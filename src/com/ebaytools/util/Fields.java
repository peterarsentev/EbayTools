package com.ebaytools.util;

public enum Fields {
    AUCTION_CLOSE_TIME("auction_closing_time", "auction closing time"),
    AUCTION_PRICE("auction_price", "auction price"),
    IS_GOLDEN("is_golden", "is golden"),
    SHIPPING_COST("shipping_cost", "shipping cost"),
    TOTAL_COST("total_cost", "total cost");

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
