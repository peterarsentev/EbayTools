package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.Filter;
import com.ebaytools.kernel.entity.FilterConditions;

import java.util.List;
import java.util.Set;

public interface FilterDAO extends CrUD<Filter> {
    public List<Filter> getAllFilters();
}
