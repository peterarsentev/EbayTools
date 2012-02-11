package com.ebaytools.util;

import java.util.*;

import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.Filter;
import com.ebaytools.kernel.entity.FilterConditions;
import com.ebaytools.kernel.entity.FilterValue;
import com.ebaytools.kernel.entity.SystemSetting;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

public class FilterDataImpl implements TableModelCheckBox.IData {
    private List<Filter> filters;
    private static final List<String> nameColumn = Arrays.asList("ID", "Name", "Conditions", "Golden", "Time of day", "Sold");

    public FilterDataImpl(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public List<Object[]> getData() {
        List<Object[]> filterFields = new ArrayList<Object[]>();
        List<String> settings = ManagerDAO.getInstance().getSystemSettingDAO().getSystemsValue(Fields.APPLY_FILTER.getKey());
        for (Filter filter : filters) {
            Object[] objects = new Object[7];
            objects[0] = settings.contains(String.valueOf(filter.getId()));
            objects[1] = filter.getId();
            objects[2] = filter.getName();
            Map<Fields, String> conditionsMap = buildConditions(filter.getConditions());
            objects[3]  = conditionsMap.get(Fields.CONDITIONS);
            objects[4]  = conditionsMap.get(Fields.IS_GOLDEN_FILTER_FIELD);
            objects[5]  = conditionsMap.get(Fields.TIME_OF_DAY);
            objects[6]  = conditionsMap.get(Fields.SOLD);
            filterFields.add(objects);
        }
        return filterFields;
    }
    

    public static Map<Fields, String> buildConditions(Set<FilterConditions> conditionses) {
        Map<Fields, String> conditionMap = new LinkedHashMap<Fields, String>();
        if (conditionses != null) {
            for (FilterConditions conditions : conditionses) {
                fullingData(conditions, Fields.CONDITIONS, conditionMap);
                fullingData(conditions, Fields.IS_GOLDEN_FILTER_FIELD, conditionMap);
                fullingData(conditions, Fields.TIME_OF_DAY, conditionMap);
                fullingData(conditions, Fields.SOLD, conditionMap);
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

    public void resizeColumnsByName(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(20);
        tcm.getColumn(1).setPreferredWidth(20);
        tcm.getColumn(2).setPreferredWidth(100);
        tcm.getColumn(3).setPreferredWidth(100);
        tcm.getColumn(4).setPreferredWidth(50);
        tcm.getColumn(5).setPreferredWidth(100);
        tcm.getColumn(6).setPreferredWidth(50);
    }
}
