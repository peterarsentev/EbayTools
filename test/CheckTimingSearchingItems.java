import com.ebay.services.finding.SearchItem;
import com.ebay.services.finding.SortOrderType;
import com.ebaytools.util.SearchUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class CheckTimingSearchingItems {
    private static final Log log = LogFactory.getLog(CheckTimingSearchingItems.class);
    public static void main(String[] arg) {
        SearchUtil util = SearchUtil.getInstance();
        long start = System.currentTimeMillis();
        log.info("Start : " + start);
        new InnerSearcher(util, "First", "77826847").start();
        new InnerSearcher(util, "Second", "108837307").start();
        new InnerSearcher(util, "Three", "92352708").start();
        log.info(" Time : " + (System.currentTimeMillis() - start) + " ms");
//        util.getItemsBySortedType("77826847", "3000;7000", "Auction", SortOrderType.BEST_MATCH, "ReferenceID", null);
//        util.getItemsBySortedType("108837307", "3000;7000", "Auction", SortOrderType.BEST_MATCH, "ReferenceID", null);
//        util.getItemsBySortedType("92352708", "3000;7000", "Auction", SortOrderType.BEST_MATCH, "ReferenceID", null);
//        log.info(" Time : " + (System.currentTimeMillis() - start) + " ms");
    }
    
    private static class InnerSearcher extends Thread {
        private SearchUtil util;
        private String name;
        private String referenceId;

        private InnerSearcher(SearchUtil util, String name, String referenceId) {
            this.util = util;
            this.name = name;
            this.referenceId = referenceId;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();    //77826847;108837307;92352708
            List<SearchItem> items = util.getItemsBySortedType(referenceId, "3000;7000", "Auction", SortOrderType.BEST_MATCH, "ReferenceID", null);
            log.info(name + " : total : " + items.size() + " time : " + (System.currentTimeMillis() - start) + " ms");
        }
    }
    
}

