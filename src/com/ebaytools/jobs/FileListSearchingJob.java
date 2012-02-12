package com.ebaytools.jobs;

import com.ebay.services.finding.SearchItem;
import com.ebay.services.finding.SortOrderType;
import com.ebaytools.gui.linteners.LoadFileSearchItemActionListener;
import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.FileSearching;
import com.ebaytools.util.FormatterText;
import com.ebaytools.util.Pair;
import com.ebaytools.util.SearchUtil;
import com.ebaytools.util.TextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;

import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileListSearchingJob  {
    private static final Logger log = Logger.getLogger(FileListSearchingJob.class);

    public static void jobs() {
        new Thread() {
            @Override
            public void run() {
                WorkerPool pool = new WorkerPool(1, 0, 4, 30);
                while (true) {
                    log.debug("start");
                    List<FileSearching> files = ManagerDAO.getInstance().getFileSearchingDAO().getFileSearchingCurrentTime();
                    for (FileSearching fileSearch : files) {
                        int result = pool.process(new FileSearchingWorker(fileSearch));
                        if (result == 1) {
                            ManagerDAO.getInstance().getFileSearchingDAO().updateRunTime(fileSearch);
                        }
                    }
                    try {
                        Thread.sleep(60000); //wait 1 min
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.debug("finish");
                }
            }
        }.start();
    }

    private static class FileSearchingWorker extends WorkerPool.Worker {
        private FileSearching fileSearching;
        private Long id;

        private FileSearchingWorker(FileSearching fileSearching) {
            super(fileSearching.getId());
            this.fileSearching = fileSearching;
            id = fileSearching.getId();
        }

        private FileSearchingWorker(Long id) {
            super(id);
            this.id = id;
        }

        @Override
        public void run() {
            try {
                execute(fileSearching);
            } catch (Exception e) {
                log.debug("Error", e);
            }
        }
    }

    /**
     * This method is execured through Quartz schecule
     * @param fileSearching FileSearching
     */
    private static void execute(FileSearching fileSearching) {
        log.debug("start");
        long start = System.currentTimeMillis();
        List<String> loadId = new ArrayList<String>();
        File file = new File(fileSearching.getPath());
        if (file.exists()) {
            List<String> data = LoadFileSearchItemActionListener.readFile(file);
            loadId.addAll(data);
        } else {
            log.error("File " + file.getPath() + " is not exist! ");
        }
        Map<Pair, Map<SearchItem, Boolean>> map = new LinkedHashMap<Pair, Map<SearchItem, Boolean>>();
        for (String id : loadId) {
            if (TextUtil.isNotNull(id)) {
                Map<SearchItem, Boolean> items = getResult(id, "UPC", fileSearching.getCondition(), fileSearching.getListType(), fileSearching.getDayLeft());
                if (items.isEmpty()) {
                    items = getResult(id, "ReferenceID", fileSearching.getCondition(), fileSearching.getListType(), fileSearching.getDayLeft());
                    if (items.isEmpty()) {
                        log.debug(new StringBuffer().append("\nid : ").append(id).append(" doesn't have any match by peference and upc id!\n\n").toString());
                        map.put(new Pair<String>(id, "doesn't have any match by reference and upc id!"), null);
                    } else {
                        FormatterText.formatForConsole(items, id, "ReferenceID");
                        map.put(new Pair<String>(id, "ReferenceID"), items);
                    }
                } else {
                    FormatterText.formatForConsole(items, id, "UPC");
                    map.put(new Pair<String>(id, "UPC"), items);
                }
            }
        }
        ManagerDAO.getInstance().getProductDAO().create(map);
        log.debug("total : " + map.size() + " time : " + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * This method help to check reference or upc id
     * @param id product id
     * @param type type product id reference or upc
     * @param conditions conditions
     * @param listingType listing type usually auction
     * @param leftTime left time
     * @return map result with item and golden property
     */
    private static Map<SearchItem, Boolean> getResult(String id, String type, String conditions, String listingType, Integer leftTime) {
        Map<SearchItem, Boolean> items = new LinkedHashMap<SearchItem, Boolean>();
        log.debug(id + " " + conditions + " " + listingType + " " + SortOrderType.BEST_MATCH + " " + type + " " + leftTime);
        List<SearchItem> searchItems = SearchUtil.getInstance().getItemsBySortedType(id, conditions, listingType, SortOrderType.BEST_MATCH, type, leftTime);
        Map<SearchItem, Boolean> fullingItem = SearchUtil.fullingGoldenItems(searchItems);
        items.putAll(fullingItem);
        log.debug(" Total : " + searchItems.size());
        return items;
    }
}
