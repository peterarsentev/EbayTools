package com.ebaytools.util;

import com.ebay.sdk.*;
import com.ebay.sdk.call.GetItemCall;
import com.ebay.soap.eBLBaseComponents.DetailLevelCodeType;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebay.services.finding.SearchItem;
import java.util.*;
import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.*;
import com.ebay.services.finding.FindingServicePortType;

import javax.xml.datatype.Duration;

/**
 * This class is used for search items in ebay.
 * @author Admin
 *
 */
public class SearchUtil {
    private GetItemCall gc;
    private FindingServicePortType serviceClient;

    /**
     * is used singleton pattern, because this instance is needed in difference place in this project
     */
    private static SearchUtil instance;

    public static synchronized SearchUtil getInstance() {
        if (instance == null) {
            instance = new SearchUtil();
        }
        return instance;
    }

    /**
     * This constructor initial special configurations for sdk ebay API
     */
    private SearchUtil() {
        ClientConfig config = new ClientConfig();
        config.setApplicationId("TrackStu-c3e1-4e31-90e0-7ae139cd3650");
        this.serviceClient = FindingServiceClientFactory.getServiceClient(config);
        ApiContext apiContext = new ApiContext();
        ApiCredential cred = apiContext.getApiCredential();

        cred.seteBayToken("AgAAAA**AQAAAA**aAAAAA**l/nATg**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AEl4SnCJaKqAidj6x9nY+seQ**s00BAA**AAMAAA**oEMj7FN4qgzFdhM9ppIZUAndL2MDJxd3Q1HBBPHJPk4IH0ajpiEYrbXoAzpYZcUL0XyvU9f84EEK0EfqlXuOZu8g0o+ENxBr/UjaPFNcUpiRmwXA4ESVpKY0aB3V445a24yp9as+ljhDszsR1b6kmieYedFfZroyZ3FzlW1H4pfSOU0OCi5FxUQE52Qwv9bW91hFPJG5CzXskU3Cv4eYspjjSQSij5Jm2jaoiPOp/M4wLHw8Fi4p3n2X9znbpOco2qXgeisluHVbEIG+Qx7ODD6BZmi0aRt2wVW+00B72HpfWiFwsD9apico5TeYt5XUMtyMyloTRFTj0bp0A866yJNmWOQx8ny1DsDSHIpCr80E5aUZmHh2qc1JkSX/CfYBa6mO4qLndgSIP7fn3CxLQxGT4kXEGeJ3KuRPoD97wEhNGkoEY0OxvfVg++Bk7CUQnOkcSLVFEPIlPaZ/woUGpLD38MslXiEW4aoPfz6IBMxlka+jRqqr/HreC7RK47oeezdfMP5Z7dTZSHdmxXt2V7dN+IruYTIDe5bvFOtexiZUZ8QmAp2MkxlwLqaRb51AHdhp+jKr2Oc1OWdQf20H/WFdTqWfHPWVWwbnpsay8ais6FnMMbMS4xf5W2Yn4N3rhz6YH/Sxhvv2FHJ/zSJ7uwuEDCJ3yuj2kVzmdw3+Cmukml2RhGAAY4xZT3bk09pzO7X55fGUd+q2T0CfUBBPVoQH4Yxj6eoHmLa+NmBKsbaSr1NgcGl1BrPBHYWmrQk7");
        apiContext.setApiServerUrl("https://api.ebay.com/wsapi");
        GetItemCall gc = new GetItemCall(apiContext);
        DetailLevelCodeType[] detailLevels = new DetailLevelCodeType[] {
                DetailLevelCodeType.RETURN_ALL,
                DetailLevelCodeType.ITEM_RETURN_ATTRIBUTES,
                DetailLevelCodeType.ITEM_RETURN_DESCRIPTION
        };
        gc.setDetailLevel(detailLevels);
        this.gc = gc;
    }

    /**
     * This method gets reference id by numbers item
     * @param itemNubmer item id
     * @return ItemType product
     */
    public ItemType getProductByItemNumber(String itemNubmer) {
        try {
            return gc.getItem(itemNubmer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  This method gets upc id by reference id
     * @param referenceID Reference ID
     * @return UPC ID
     * @throws Exception for necessery
     */
    public String getUpcID(String referenceID) throws Exception {
        FindByProduct productRequest = new FindByProduct();
        PaginationInput pi = new PaginationInput();
        pi.setEntriesPerPage(1);
        pi.setPageNumber(1);
        productRequest.setPaginationInput(pi);
        ProductId product = new ProductId();
        product.setType("ReferenceID");
        product.setValue(referenceID);
        productRequest.setProductId(product);
        FindItemsByProductResponse response = serviceClient.findItemsByProduct(productRequest);
        SearchResult result = response.getSearchResult();
        if (result != null) {
            ItemType item = gc.getItem(result.getItem().get(0).getItemId());
            return item.getProductListingDetails().getUPC();
        } else {
            return null;
        }
    }

    private static ItemFilter buildFulter(ItemFilterType type, String value) {
        Filter filter = new Filter();
        filter.setName(type);
        filter.addValue(value);
        return filter;
    }

    public List<SearchItem> getItemsBySortedType(String productId, String condition, String listingType, SortOrderType order, String type, Integer daysLeft) {
        FindByProduct productRequest = new FindByProduct();
        PaginationInput pi = new PaginationInput();
        pi.setPageNumber(1);
        productRequest.setPaginationInput(pi);
        productRequest.setSortOrder(order);
        if (TextUtil.isNotNull(condition)) {
            productRequest.add(buildFulter(ItemFilterType.CONDITION, condition));
        }
        if (TextUtil.isNotNull(listingType)) {
            productRequest.add(buildFulter(ItemFilterType.LISTING_TYPE, listingType));
        }
        if (TextUtil.isNotNull(daysLeft)) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, daysLeft);
            productRequest.add(buildFulter(ItemFilterType.END_TIME_TO, FormatterText.buildDate(cal)));
        }

        ProductId product = new ProductId();
        product.setType(type);
        product.setValue(productId);
        productRequest.setProductId(product);
        FindItemsByProductResponse response = serviceClient.findItemsByProduct(productRequest);
        SearchResult result = response.getSearchResult();
        if (result != null) {
            return getAllItems(productRequest, response, result.getItem());
        } else {
            return new ArrayList<SearchItem>();
        }
    }

    /**
     * This method gets all items, because find ebay api doesn't allow to get more then 100 items by one quire,
     * We get all items from all pages.
     * @param productRequest find request
     * @param response find response
     * @param items first list
     * @return all items
     */
    private List<SearchItem> getAllItems(FindByProduct productRequest, FindItemsByProductResponse response, List<SearchItem> items) {
        List<SearchItem> totalItems = new ArrayList<SearchItem>();
        PaginationOutput outPage = response.getPaginationOutput();
        totalItems.addAll(items);
        if (items.size() < outPage.getTotalEntries()) {
            for (int i=2;i!=outPage.getTotalPages()+1;++i) {
                PaginationInput pi = new PaginationInput();
                pi.setPageNumber(i);
                productRequest.setPaginationInput(pi);
                response = serviceClient.findItemsByProduct(productRequest);
                SearchResult result = response.getSearchResult();
                totalItems.addAll(result.getItem());
            }
        }
        return totalItems;
    }

    public static List<SearchItem> getGoldenItems(List<SearchItem> sortedItems) {       // after fixing the bug
        List<SearchItem> incorrectDuration = new ArrayList<SearchItem>();
        boolean check = false;   //checking for the moment the time has switched.
        if (!sortedItems.isEmpty()) {
            Duration previuosDuration = sortedItems.get(0).getSellingStatus().getTimeLeft();
            for (SearchItem item : sortedItems) {
                Duration currectDuration = item.getSellingStatus().getTimeLeft();
                if (currectDuration.isShorterThan(previuosDuration)) {
                    check = true;
                }
                if (check) {incorrectDuration.add(item); }
                previuosDuration = currectDuration;

            }
        }
        return incorrectDuration;
    }
}
