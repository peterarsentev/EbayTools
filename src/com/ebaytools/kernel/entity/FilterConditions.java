package com.ebaytools.kernel.entity;

import java.io.Serializable;
import java.util.Set;

public class FilterConditions implements Serializable, Comparable<FilterConditions>  {
    private Long id;
    private Long filterId;
    private String name;
    private String type;
    private Set<FilterValue> values;

    public Set<FilterValue> getValues() {
        return values;
    }

    public void setValues(Set<FilterValue> values) {
        this.values = values;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFilterId() {
        return filterId;
    }

    public void setFilterId(Long filterId) {
        this.filterId = filterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterConditions that = (FilterConditions) o;

        if (filterId != null ? !filterId.equals(that.filterId) : that.filterId != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (filterId != null ? filterId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(FilterConditions o) {
        return this.name.compareTo(o.name);
    }
}
