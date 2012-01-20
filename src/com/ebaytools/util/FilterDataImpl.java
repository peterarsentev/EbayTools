package com.ebaytools.util;

import java.util.*;

import com.ebaytools.kernel.entity.Filter;
import com.ebaytools.kernel.entity.FilterConditions;
import com.ebaytools.kernel.entity.FilterValue;

public class FilterDataImpl implements TableModelCheckBox.IData {
    private List<Filter> filters;
    private static final List<String> nameColumn = Arrays.asList("ID", "Name", "Conditions", "Golden", "Time of day");

    public FilterDataImpl(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public List<Object[]> getData() {
        List<Object[]> filterFields = new ArrayList<Object[]>();
        for (Filter filter : filters) {
            Object[] objects = new Object[6];
            objects[0] = Boolean.FALSE;
            objects[1] = filter.getId();
            objects[2] = filter.getName();
            Map<Fields, String> conditionsMap = buildConditions(filter.getConditions());
            objects[3]  = conditionsMap.get(Fields.CONDITIONS);
            objects[4]  = conditionsMap.get(Fields.IS_GOLDEN);
            objects[5]  = conditionsMap.get(Fields.TIME_OF_DAY);
            filterFields.add(objects);
        }
        return filterFields;
    }
    

    public static Map<Fields, String> buildConditions(Set<FilterConditions> conditionses) {
        Map<Fields, String> conditionMap = new LinkedHashMap<Fields, String>();
        if (conditionses != null) {
            for (FilterConditions conditions : conditionses) {
                fullingData(conditions, Fields.CONDITIONS, conditionMap);
                fullingData(conditions, Fields.IS_GOLDEN, conditionMap);
                fullingData(conditions, Fields.TIME_OF_DAY, conditionMap);
            }
        }
        return conditionMap;
    }
    
    private static void fullingData(FilterConditions conditions, Fields field, final Map<Fields, String> data) {
        if (conditions.getName().equals(field.getKey())) {
            StringBuilder sb = new StringBuilder();
            for (FilterValue value : conditions.getValues()) {
                sb.append(value.getValue()).append(";");
            }
            data.put(field, sb.toString());
        }
    }

    @Override
    public List<String> getNameColumn() {
        return nameColumn;
    }
}
