package com.ebaytools.util;

import com.ebaytools.kernel.entity.ItemProperties;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public enum Fields {
    //filters fields
    IS_GOLDEN_FILTER_FIELD("is_golden", "is golden"),
    APPLY_FILTER("apply_filter", "apply_filter"),
    CONDITIONS("conditions", "conditions"),
    SOLD("sold", "sold"),
    TIME_OF_DAY("time_of_day", "time_of_day"),
    DATE_CLOSE_FROM("close_auction_date_from", "close_auction_date_from"),
    DATE_CLOSE_TO("close_auction_date_to", "close_auction_date_from"),

    //items fields
    AUCTION_PRICE("auction_price", "auction price"),
    SHIPPING_COST("shipping_cost", "shipping cost"),
    TOTAL_COST("total_cost", "total cost"),
    AUCTION_STATUS("auction_status", "auction status"),
    EBAY_ITEM_ID("ebay_item_id", "ebay_item_id"),
    CLOSE_DATE("close_date", "close_date"),
    TOTAL_BID("total_bid", "total_bid"),
    GOLDEN("golden", "golden"),
    POSITIVE_FEEDBACK_PERCENT("positive_feedback_percent", "positive_feedback_percent"),
    FEEDBACK_SCORE("feedback_score", "feedback_score"),
    TOP_RATED_SELLER("top_rated_seller", "top_rated_seller"),
    SHIP_TO_LOCATION("ship_to_location", "ship_to_location"),
    HANDLING_TIME("handling_time", "handling_time"),
    LISTING_TYPE("listing_type", "listing_type"),

    //systems fields
    CHOSE_OPTIONS("chose_options", "chose_optios"),
    SHOW_FIELDS("show_fields", "show_fields");

    private String key;
    private String name;

    private Fields(String key, String name) {
        this.key = key;
        this.name = name;
    }


    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public static Map<Fields, ItemProperties> buildProperties(Set<ItemProperties> propertiesSet) {
        Map<Fields, ItemProperties> map = new LinkedHashMap<Fields, ItemProperties>();
        for (ItemProperties properties : propertiesSet) {
            fullingValue(Fields.AUCTION_STATUS, properties, map);
            fullingValue(Fields.AUCTION_PRICE, properties, map);
            fullingValue(Fields.SHIPPING_COST, properties, map);
            fullingValue(Fields.TOTAL_COST, properties, map);
            fullingValue(Fields.CONDITIONS, properties, map);

            fullingValue(Fields.POSITIVE_FEEDBACK_PERCENT, properties, map);
            fullingValue(Fields.FEEDBACK_SCORE, properties, map);
            fullingValue(Fields.TOP_RATED_SELLER, properties, map);
            fullingValue(Fields.SHIP_TO_LOCATION, properties, map);
            fullingValue(Fields.HANDLING_TIME, properties, map);
            fullingValue(Fields.LISTING_TYPE, properties, map);
        }
        return map;
    }

    private static void fullingValue(Fields fields, ItemProperties properties, Map<Fields, ItemProperties> map) {
        if (fields.getKey().equals(properties.getName())) {
            map.put(fields, properties);
        }
    }
}