package com.ebaytools.util;

public enum Fields {
    AUCTION_CLOSE_TIME("auction_closing_time", "auction closing time"),
    AUCTION_PRICE("auction_price", "auction price"),
    IS_GOLDEN("is_golden", "is golden"),
    SHIPPING_COST("shipping_cost", "shipping cost"),
    TOTAL_COST("total_cost", "total cost"),
    AUCTION_STATUS("auction_status", "auction status"),
    CONDITIONS("conditions", "conditions"),
    TIME_OF_DAY("time_of_day", "time_of_day"),
    APPLY_FILTER("apply_filter", "apply_filter"),
    CHOSE_OPTIONS("chose_options", "chose_optios"),
    TOTAL_BIDS("total_bids", "total_bids");


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