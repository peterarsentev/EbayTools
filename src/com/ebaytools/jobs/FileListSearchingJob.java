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

public class FileListSearchingJob extends QuartzJobBean {
    private static final Logger log = Logger.getLogger(FileListSearchingJob.class);

    /*
    Should to change it because it heavy to set up in the code
     */
    private String conditions = "3000;7000"; //set up conditions through DI spring
    private String listingType = "Auction"; //set up aucton through DI spring

    /**
     * This method is execured through Quartz schecule
     * @param context JobExecutionContext
     * @throws JobExecutionException for necessery
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.debug("start");
        long start = System.currentTimeMillis();
        List<FileSearching> files = ManagerDAO.getInstance().getFileSearchingDAO().getAllFileSearching();
        List<String> loadId = new ArrayList<String>();
        for (FileSearching fileSearching: files) {
            File file = new File(fileSearching.getPath());
            if (file.exists()) {
                List<String> data = LoadFileSearchItemActionListener.readFile(file);
                loadId.addAll(data);
            } else {
                log.error("File " + file.getPath() + " is not exist! ");
            }
        }
        Map<Pair, Map<SearchItem, Boolean>> map = new LinkedHashMap<Pair, Map<SearchItem, Boolean>>();
        for (String id : loadId) {
            if (TextUtil.isNotNull(id)) {
                Map<SearchItem, Boolean> items = getResult(id, "UPC", conditions, listingType);
                if (items.isEmpty()) {
                    items = getResult(id, "ReferenceID", conditions, listingType);
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
     * @return map result with item and golden property
     */
    private static Map<SearchItem, Boolean> getResult(String id, String type, String conditions, String listingType) {
        Map<SearchItem, Boolean> items = new LinkedHashMap<SearchItem, Boolean>();
        List<SearchItem> searchItems = SearchUtil.getInstance().getItemsBySortedType(id, conditions, listingType, SortOrderType.BEST_MATCH, type, 1);
        if (searchItems != null) {
            Map<SearchItem, Boolean> fullingItem = SearchUtil.fullingGoldenItems(searchItems);
            items.putAll(fullingItem);
        }
        return items;
    }
}
