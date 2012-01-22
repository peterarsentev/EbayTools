package com.ebaytools.util;

import com.ebay.services.finding.Amount;
import com.ebay.services.finding.SearchItem;
import com.ebaytools.kernel.dao.ManagerDAO;

import javax.xml.datatype.Duration;
import java.text.SimpleDateFormat;
import java.util.*;

public class FormatterText {
    public final static SimpleDateFormat dateformatter = new SimpleDateFormat("hh:mm:ss dd.MM.yyyy");
    private final static SimpleDateFormat dateForSearchDay = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat dateForSearchTime = new SimpleDateFormat("hh:mm:ss.SSS");

    public static String buildDate(Calendar cal) {
        return dateForSearchDay.format(cal.getTime()) + "T" + dateForSearchTime.format(cal.getTime()) + "Z";
    }

    /**
     * This method makes specail formats data for file
     * @param items
     * @param type
     * @param id
     * @return
     */
    public static String buildCsvFile(List<SearchItem> items, String type, String id, String typeSearch) {
        Map<String, List<SearchItem>> listByComdition = new LinkedHashMap<String, List<SearchItem>>(); //this map we use for sort our items by condition
        for (SearchItem item : items) {
            String condition = item.getCondition().getConditionDisplayName();
            List<SearchItem> conditionList = listByComdition.get(condition);
            if (conditionList == null) {
                conditionList = new ArrayList<SearchItem>();
            }
            conditionList.add(item);
            listByComdition.put(condition, conditionList);
        }
        StringBuilder sb = new StringBuilder("Type search : " + typeSearch);
        sb.append(type).append(" (").append(id).append(")").append("\n\n");
        for (Map.Entry<String, List<SearchItem>> entry : listByComdition.entrySet()) {
            sb.append("Condition (").append(entry.getKey()).append(")").append("\n");
            for (SearchItem item : entry.getValue()) {
                sb.append(item.getItemId()).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    /**
     * This method prints result searching with system setting
     * @param items Map searching result
     * @param id product id
     * @param type type product
     * @return formatted text
     */
    public static String formatForConsole(Map<SearchItem, Boolean> items, String id, String type) {
        StringBuilder sb = new StringBuilder();
        sb.append(type).append(" (").append(id).append(")").append("\n\n");
        List<String> opts = ManagerDAO.getInstance().getSystemSettingDAO().getChooseOptsValue();
        for (Map.Entry<SearchItem, Boolean> entry : items.entrySet())  {
             SearchItem item = entry.getKey();
            for (String valueOpt : opts) {
                if ("autoPay".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(String.valueOf(item.isAutoPay())).append("\n");
                }
                if ("charityId".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getCharityId()).append("\n");
                }
                if ("compatibility".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getCompatibility()).append("\n");
                }
                if ("conditionDisplayName".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getCondition().getConditionDisplayName()).append("\n");
                }
                if ("country".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getCountry()).append("\n");
                }
                if ("distance".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getDistance()).append("\n");
                }
                if ("galleryURL".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getGalleryURL()).append("\n");
                }
                if ("galleryPlusPictureURL".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getGalleryPlusPictureURL()).append("\n");
                }
                if ("globalId".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getGlobalId()).append("\n");
                }
                if ("itemId".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getItemId()).append("\n");
                }
                if ("bestOfferEnabled".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getListingInfo().isBestOfferEnabled()).append("\n");
                }
                if ("buyItNowAvailable".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getListingInfo().isBuyItNowAvailable()).append("\n");
                }
                if ("buyItNowPrice".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getListingInfo().getBuyItNowPrice()).append("\n");
                }
                if ("convertedBuyItNowPrice".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getListingInfo().getConvertedBuyItNowPrice()).append("\n");
                }
                if ("endTime".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(dateformatter.format(item.getListingInfo().getEndTime().getTime())).append("\n");
                }
                if ("gift".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getListingInfo().isGift()).append("\n");
                }
                if ("listingType".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getListingInfo().getListingType()).append("\n");
                }
                if ("startTime".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(dateformatter.format(item.getListingInfo().getStartTime().getTime())).append("\n");
                }
                if ("primaryCategoryId".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getPrimaryCategory().getCategoryId()).append("\n");
                }
                if ("primaryCategoryName".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getPrimaryCategory().getCategoryName()).append("\n");
                }
                if ("location".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getLocation()).append("\n");
                }
                if ("paymentMethod".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getPaymentMethod()).append("\n");
                }
                if ("postalCode".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getPostalCode()).append("\n");
                }
                if ("productId".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getProductId()).append("\n");
                }
                if ("returnsAccepted".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.isReturnsAccepted()).append("\n");
                }
                if ("secondaryCategoryId".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getSecondaryCategory().getCategoryId()).append("\n");
                }
                if ("secondaryCategoryName".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getSecondaryCategory().getCategoryName()).append("\n");
                }
                if ("feedbackRatingStar".equals(valueOpt)) {
                    String value = item.getSellerInfo() != null ? item.getSellerInfo().getFeedbackRatingStar() : "";
                    sb.append(valueOpt).append(" : ").append(value).append("\n");
                }
                if ("feedbackScore".equals(valueOpt)) {
                    String value = item.getSellerInfo() != null ? String.valueOf(item.getSellerInfo().getFeedbackScore()) : "";
                    sb.append(valueOpt).append(" : ").append(value).append("\n");
                }
                if ("positiveFeedbackPercent".equals(valueOpt)) {
                    String value = item.getSellerInfo() != null ? String.valueOf(item.getSellerInfo().getPositiveFeedbackPercent()) : "";
                    sb.append(valueOpt).append(" : ").append(value).append("\n");
                }
                if ("sellerUserName".equals(valueOpt)) {
                    String value = item.getSellerInfo() != null ? String.valueOf(item.getSellerInfo()) : "";
                    sb.append(valueOpt).append(" : ").append(value).append("\n");
                }
                if ("topRatedSeller".equals(valueOpt)) {
                    String value = item.getSellerInfo() != null ? String.valueOf(item.getSellerInfo().isTopRatedSeller()) : "";
                    sb.append(valueOpt).append(" : ").append(value).append("\n");
                }
                if ("bidCount".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getSellingStatus().getBidCount()).append("\n");
                }
                if ("convertedCurrentPrice".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getSellingStatus().getConvertedCurrentPrice()).append("\n");
                }
                if ("currentPrice".equals(valueOpt)) {
                    String value = String.valueOf(item.getSellingStatus().getCurrentPrice().getValue());
                    String rate = item.getSellingStatus().getCurrentPrice().getCurrencyId();
                    sb.append(valueOpt).append(" : ").append(getPrice(item.getSellingStatus().getCurrentPrice())).append("\n");
                }
                if ("sellingState".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getSellingStatus().getSellingState()).append("\n");
                }
                if ("timeLeft".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(buildTime(item.getSellingStatus().getTimeLeft())).append("\n");
                }
                if ("expeditedShipping".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getShippingInfo().isExpeditedShipping()).append("\n");
                }
                if ("handlingTime".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getShippingInfo().getHandlingTime()).append("\n");
                }
                if ("oneDayShippingAvailable".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getShippingInfo().isOneDayShippingAvailable()).append("\n");
                }
                if ("shippingServiceCost".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(getPrice(item.getShippingInfo().getShippingServiceCost())).append("\n");
                }
                if ("shippingType".equals(valueOpt)) {

                    sb.append(valueOpt).append(" : ").append(item.getShippingInfo().getShippingType()).append("\n");
                }
                if ("shipToLocations".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getShippingInfo().getShipToLocations()).append("\n");
                }
                if ("storeName".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getStoreInfo().getStoreName()).append("\n");
                }
                if ("storeURL".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getStoreInfo().getStoreURL()).append("\n");
                }
                if ("subtitle".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getSubtitle()).append("\n");
                }
                if ("title".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getTitle()).append("\n");
                }
                if ("viewItemURL".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(item.getViewItemURL()).append("\n");
                }
                if ("description".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(SearchUtil.getInstance().getProductByItemNumber(item.getItemId()).getDescription()).append("\n");
                }
                if ("golden".equals(valueOpt)) {
                    sb.append(valueOpt).append(" : ").append(String.valueOf(entry.getValue())).append("\n");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private static String buildTime(Duration duration) {
        StringBuilder sb = new StringBuilder();
        sb.append(duration.getDays()).append(" days, ").append(duration.getHours()).append(" hours, ").append(duration.getMinutes()).append(" minutes, ").append(duration.getSeconds()).append(" seconds.");
        return sb.toString();
    }
    
    public static String addAmount(Amount shipping, Amount price) {
        if (shipping != null && price != null) {
            return String.valueOf(shipping.getValue() + price.getValue());
        } else {
            return null;
        }        
    }

    public static String getPrice(Amount amount) {
        if (amount != null) {
            return String.valueOf(amount.getValue());
        } else {
            return null;
        }
    }

    public static String getCurrency(Amount amount) {
        if (amount != null) {
            return amount.getCurrencyId();
        } else {
            return null;
        }
    }
}
