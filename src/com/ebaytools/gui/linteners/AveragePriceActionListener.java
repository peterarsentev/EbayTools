package com.ebaytools.gui.linteners;

import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.dao.ProductDAOImpl;
import com.ebaytools.kernel.entity.Item;
import com.ebaytools.kernel.entity.ItemProperties;
import com.ebaytools.util.Fields;
import com.ebaytools.util.FormatterText;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.*;

public class AveragePriceActionListener implements ActionListener {
    private JFrame main;
    private Data data;

    public AveragePriceActionListener(JFrame main, Data data) {
        this.main = main;
        this.data = data;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuilder sb = new StringBuilder();
        List<Item> items = ManagerDAO.getInstance().getItemDAO().getAllItems();
        Map<Rang, List<Item>> rangByHour = new LinkedHashMap<Rang, List<Item>>();
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return o1.getCreateDate().compareTo(o2.getCreateDate());
            }
        });
        for (Item item : items) {
            Rang rang = Rang.createRang(item.getCloseDate());
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
                Map<Fields, ItemProperties> prs = ProductDAOImpl.buildProperties(item.getProperties());
                averagePrice += Float.valueOf(prs.get(Fields.AUCTION_PRICE).getValue());
            }
//            sb.append((entry.getKey().month+1) +":"+entry.getKey().day + ":" + entry.getKey().year + "\t");
            sb.append(entry.getKey().hour + "-" + (entry.getKey().hour+1) + ":\t");
            DecimalFormat twoDForm = new DecimalFormat("#.##");
            sb.append(twoDForm.format(averagePrice/entry.getValue().size()) + "$\t(" + entry.getValue().size() + ")");
            sb.append("\n");
        }
        sb.append("Total items : ").append(items.size()).append("\n");
        data.getText().setText(data.getText().getText() + sb.toString());
    }
    
    public Map<Rang, List<Item>> sortMap(Map<Rang, List<Item>> map) {
        List<Rang> keys = new ArrayList<Rang>(map.keySet());
        Collections.sort(keys, new Comparator<Rang>() {
            @Override
            public int compare(Rang o1, Rang o2) {
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
        
        private static Rang createRang(Calendar calendar) {
            Rang rang = new Rang();
            rang.year = 0;//calendar.get(Calendar.YEAR);
            rang.month = 0;//calendar.get(Calendar.MONTH);
            rang.day = 0;//calendar.get(Calendar.DAY_OF_MONTH);
            rang.hour = calendar.get(Calendar.HOUR_OF_DAY);
            return rang;
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

            if (day != rang.day) return false;
            if (hour != rang.hour) return false;
            if (month != rang.month) return false;
            if (year != rang.year) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = year;
            result = 31 * result + month;
            result = 31 * result + day;
            result = 31 * result + hour;
            return result;
        }
    }
}
