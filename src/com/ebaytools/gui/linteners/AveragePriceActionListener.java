package com.ebaytools.gui.linteners;

import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.dao.ProductDAOImpl;
import com.ebaytools.kernel.entity.Filter;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.kernel.entity.SystemSetting;
import com.ebaytools.util.Fields;
import com.ebaytools.util.FilterDataImpl;
import com.ebaytools.util.FormatterText;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.*;

public class AveragePriceActionListener implements ActionListener {
    private static final Logger log = Logger.getLogger(AveragePriceActionListener.class);

    private JFrame main;
    private Data data;

    public AveragePriceActionListener(JFrame main, Data data) {
        this.main = main;
        this.data = data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Object[]> selectData = data.getProductTable().getDataSelect();
        List<Long> prsids = new ArrayList<Long>();
        if (!selectData.isEmpty()) {
            for (Object[] object : selectData) {
                prsids.add((Long) object[1]);
            }
        }
        StringBuilder sb = new StringBuilder();
        List<Filter> filters = new ArrayList<Filter>();
        List<SystemSetting> settings = ManagerDAO.getInstance().getSystemSettingDAO().getSystemSettingByName(Fields.APPLY_FILTER.getKey());
        for (SystemSetting setting : settings) {
            filters.add(ManagerDAO.getInstance().getFilterDAO().find(Long.valueOf(setting.getValue())));
        }
        if (filters.isEmpty()) {
            filters.add(new Filter());
        }
        for (Filter filter : filters) {
            sb.append("Filter : ").append(filter.getName()).append("\n");
            sb.append("-----------------------------------------------------------------------------\n");
            List<Item> items = ManagerDAO.getInstance().getItemDAO().getProductByFilter(filter, prsids);
            Map<Rang, List<Item>> rangByHour = new LinkedHashMap<Rang, List<Item>>();
            Collections.sort(items, new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    return o1.getCreateDate().compareTo(o2.getCreateDate());
                }
            });
            if (!selectData.isEmpty()) {
                sb.append("For reference ids : ");
                for (Object[] object : selectData) {
                    sb.append(object[2]).append(";");
                }
                sb.append("\n");
            }
            Map<Fields, String> conditions = FilterDataImpl.buildConditions(filter.getConditions());
            String type = conditions.get(Fields.PERIOD);
            for (Item item : items) {
                Rang rang = Rang.createRang(item.getCloseDate(), type, item.getProduct().getReferenceId());
                List<Item> rangItems = rangByHour.get(rang);
                if (rangItems == null) {
                    rangByHour.put(rang, new ArrayList<Item>(Arrays.asList(item)));
                } else {
                    rangItems.add(item);
                }
            }
            for (Map.Entry<Rang, List<Item>> entry : sortMap(rangByHour).entrySet()) {
                float averagePrice = 0F;
                for (Item item : entry.getValue()) {
                    Map<Fields, ItemProperties> prs = Fields.buildProperties(item.getProperties());
                    averagePrice += Float.valueOf(prs.get(Fields.TOTAL_COST).getValue());
                }
                sb.append(entry.getKey().getLabel()).append(":\t");
                DecimalFormat twoDForm = new DecimalFormat("#.##");
                sb.append(twoDForm.format(averagePrice/entry.getValue().size()) + "$\t(" + entry.getValue().size() + ")");
                sb.append("\n");
            }
            sb.append("-----------------------------------------------------------------------------\n");
            sb.append("Total items : ").append(items.size()).append("\n\n");
        }
        data.getText().setText(data.getText().getText() + sb.toString());
    }

    public Map<Rang, List<Item>> sortMap(Map<Rang, List<Item>> map) {
        List<Rang> keys = new ArrayList<Rang>(map.keySet());
        Collections.sort(keys, new Comparator<Rang>() {
            @Override
            public int compare(Rang o1, Rang o2) {
                if ("ref;".equals(o1.type)) {
                    return o1.ref.compareTo(o2.ref);
                } else {
                    if (o1.year == o2.year) {
                        if (o1.month == o2.month) {
                            if (o1.day == o2.day) {
                                if (o1.hour == o2.hour) {
                                    return 0;
                                } else {
                                    return o1.hour > o2.hour ? 1 : -1;
                                }
                            } else {
                                return o1.day > o2.day ? 1 : -1;
                            }
                        } else {
                            return o1.month > o2.month ? 1 : -1;
                        }
                    } else {
                        return o1.year > o2.year ? 1 : -1;
                    }
                }
            }
        });
        Map<Rang, List<Item>> sortedMap = new LinkedHashMap<Rang, List<Item>>();
        for (Rang rang : keys) {
            sortedMap.put(rang, map.get(rang));
        }
        return sortedMap;
    }

    private static class Rang {
        private int year;
        private int month;
        private int day;
        private int hour;
        private String ref;
        private Calendar cal;

        private String type;

        private static Rang createRang(Calendar calendar, String type, String ref) {
            Rang rang = new Rang();
            rang.cal = calendar;
            rang.type = type;
            if ("ref;".equals(type)) {
                rang.ref = ref;
            }
            if ("day;".equals(type)) {
                rang.year = calendar.get(Calendar.YEAR);
                rang.month = calendar.get(Calendar.MONTH);
                rang.day = calendar.get(Calendar.DAY_OF_MONTH);
            }
            if (type == null || "hour;".equals(type)) {
                rang.hour = calendar.get(Calendar.HOUR_OF_DAY);
            }
            return rang;
        }

        public String getLabel() {
            if ("ref;".equals(type)) {
                return "Reference ("+ref+")";
            }
            if ("day;".equals(type)) {
                return FormatterText.dateForAverage.format(cal.getTime());
            }
            if (type == null || "hour;".equals(type)) {
                return hour + "-" + (hour+1);
            }
            throw new IllegalStateException();
        }

        @Override
        public String toString() {
            return "Rang{" +
                    "year=" + year +
                    ", month=" + month +
                    ", day=" + day +
                    ", hour=" + hour +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Rang rang = (Rang) o;
            if ("ref;".equals(type)) {
                return ref.equals(rang.ref);
            } else {
                if (day != rang.day) return false;
                if (hour != rang.hour) return false;
                if (month != rang.month) return false;
                if (year != rang.year) return false;
                if (type != null ? !type.equals(rang.type) : rang.type != null) return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            if ("ref;".equals(type)) {
                return ref.hashCode();
            }
            int result = year;
            result = 31 * result + month;
            result = 31 * result + day;
            result = 31 * result + hour;
            return result;
        }
    }
}
