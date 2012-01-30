package com.ebaytools.util;

public enum Fields {
    //filters fields
    IS_GOLDEN_FILTER_FIELD("is_golden", "is golden"),
    APPLY_FILTER("apply_filter", "apply_filter"),
    CONDITIONS("conditions", "conditions"),
    TIME_OF_DAY("time_of_day", "time_of_day"),
    DATE_CLOSE_FROM("close_auction_date_from", "close_auction_date_from"),
    DATE_CLOSE_TO("close_auction_date_to", "close_auction_date_from"),

    //items fields
    AUCTION_PRICE("auction_price", "auction price"),
    SHIPPING_COST("shipping_cost", "shipping cost"),
    TOTAL_COST("total_cost", "total cost"),
    AUCTION_STATUS("auction_status", "auction status"),

    //systems fields
    CHOSE_OPTIONS("chose_options", "chose_optios");


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
}