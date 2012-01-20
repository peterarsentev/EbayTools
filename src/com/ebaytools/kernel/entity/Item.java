package com.ebaytools.kernel.entity;

import com.ebaytools.util.FormatterText;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class Item implements Comparable<Item>, Serializable {
    private Long id;
    private String ebayItemId;
    private Long productId;
    private Calendar createDate;
    private Set<ItemProperties> properties;
    private Calendar closeDate;
    private Integer totalBid;
    private Boolean golden;
    private Boolean closeAuction;

    public Boolean getCloseAuction() {
        return closeAuction;
    }

    public void setCloseAuction(Boolean closeAuction) {
        this.closeAuction = closeAuction;
    }

    public Boolean getGolden() {
        return golden;
    }

    public void setGolden(Boolean golden) {
        this.golden = golden;
    }

    public Integer getTotalBid() {
        return totalBid;
    }

    public void setTotalBid(Integer totalBid) {
        this.totalBid = totalBid;
    }

    public Calendar getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Calendar closeDate) {
        this.closeDate = closeDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEbayItemId() {
        return ebayItemId;
    }

    public void setEbayItemId(String ebayItemId) {
        this.ebayItemId = ebayItemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public Set<ItemProperties> getProperties() {
        return properties;
    }

    public void setProperties(Set<ItemProperties> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", ebayItemId='" + ebayItemId + '\'' +
                ", productId=" + productId +
                ", createDate=" + FormatterText.dateformatter.format(createDate.getTime()) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (createDate != null ? !createDate.equals(item.createDate) : item.createDate != null) return false;
        if (ebayItemId != null ? !ebayItemId.equals(item.ebayItemId) : item.ebayItemId != null) return false;
        if (id != null ? !id.equals(item.id) : item.id != null) return false;
        if (productId != null ? !productId.equals(item.productId) : item.productId != null) return false;
        if (properties != null ? !properties.equals(item.properties) : item.properties != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ebayItemId != null ? ebayItemId.hashCode() : 0);
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (properties != null ? properties.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Item o) {
        return this.ebayItemId.compareTo(o.ebayItemId);
    }
}
