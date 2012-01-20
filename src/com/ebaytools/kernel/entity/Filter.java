package com.ebaytools.kernel.entity;

import java.io.Serializable;
import java.util.Set;

public class Filter implements Serializable {
    private Long id;
    private String name;
    private Set<FilterConditions> conditions;

    public Set<FilterConditions> getConditions() {
        return conditions;
    }

    public void setConditions(Set<FilterConditions> conditions) {
        this.conditions = conditions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Filter filter = (Filter) o;

        if (id != null ? !id.equals(filter.id) : filter.id != null) return false;
        if (name != null ? !name.equals(filter.name) : filter.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
