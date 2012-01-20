package com.ebaytools.kernel.entity;

import java.io.Serializable;

public class FilterValue implements Serializable, Comparable<FilterValue> {
    private Long id;
    private Long filterConditionsId;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFilterConditionsId() {
        return filterConditionsId;
    }

    public void setFilterConditionsId(Long filterConditionsId) {
        this.filterConditionsId = filterConditionsId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterValue that = (FilterValue) o;

        if (filterConditionsId != null ? !filterConditionsId.equals(that.filterConditionsId) : that.filterConditionsId != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (filterConditionsId != null ? filterConditionsId.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(FilterValue o) {
        return this.value.compareTo(o.value);
    }
}
