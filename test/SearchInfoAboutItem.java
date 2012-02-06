import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebaytools.util.SearchUtil;

public class SearchInfoAboutItem {
    public static void main(String[] arg) {
        SearchUtil util = SearchUtil.getInstance();
        ItemType item = util.getProductByItemNumber("220929978600");
        System.out.println(item.getProductListingDetails().getDetailsURL());
        System.out.println(item.getSellingStatus().getCurrentPrice().getValue() +  " " + item.getSellingStatus().getCurrentPrice().getCurrencyID());
    }
}
