package com.ebaytools.gui.panel;

import java.awt.*;

import javax.swing.*;

import com.ebaytools.gui.linteners.*;
import com.ebaytools.gui.model.Data;
import com.ebaytools.kernel.dao.ManagerDAO;
import com.ebaytools.kernel.entity.SystemSetting;
import com.ebaytools.util.*;
import com.ebay.services.finding.*;

public class SearchPanel extends JPanel {
    private JFrame main;
    private Data data;
    private JTextField numbersItem;
    private JTextField referenceId;
    private JTextField conditionsField;
    private JTextField listTypeField;
    private JTextArea text;
    private JComboBox<Pair<SortOrderType>> sortedTypeField;
    private JCheckBox goldenSearch;
    private JTextField daysLeft;

    public SearchPanel(JFrame main, Data data) {
        // This section consists from initial objects
        this.main = main;
        this.data = data;

        SearchPanel panel = this;
        JButton searchReference = new JButton("Auction ID");
        searchReference.setForeground(ColorUtil.LightSlateGray);
        JButton showResultFilter = new JButton("Result");
        showResultFilter.setForeground(ColorUtil.LightSlateGray);
        JButton clear = new JButton("Clear");
        JButton save = new JButton("Save");
        JButton optsButton = new JButton("Choose opts.");
        JButton loadReferenceIDList = new JButton("Load");
        loadReferenceIDList.setForeground(ColorUtil.LightSlateGray);
        JButton searchUPC = new JButton("RefID to UPC");
        searchUPC.setForeground(ColorUtil.LightSlateGray);
        this.numbersItem = new JTextField();
        this.referenceId = new JTextField();
        referenceId.setText("77826847");
        this.conditionsField = new JTextField();
        conditionsField.setText("3000;7000");
        this.listTypeField = new JTextField();
        listTypeField.setText("Auction");
        this.text = new JTextArea();
        this.sortedTypeField = new JComboBox<Pair<SortOrderType>>();
        for (SortOrderType sortedType : SortOrderType.values()) {
            sortedTypeField.addItem(new Pair<SortOrderType>(sortedType.value(), sortedType));
        }
        this.goldenSearch = new JCheckBox("Search single item");
        goldenSearch.setForeground(ColorUtil.LightSlateGray);
        this.daysLeft = new JTextField();
        StringBuilder sb = new StringBuilder();
        for (SystemSetting setting : ManagerDAO.getInstance().getSystemSettingDAO().getSystemSettingByName(Fields.APPLY_FILTER.getKey())) {
            com.ebaytools.kernel.entity.Filter filter = ManagerDAO.getInstance().getFilterDAO().find(Long.valueOf(setting.getValue()));
            if (filter != null) {
                sb.append(filter.getName()).append(";");
            }
        }
        JButton buttonFilter = new JButton(sb.toString());
        JButton averagePrice = new JButton("Average price");
        JButton filters = new JButton("Filters");
        JButton showFields = new JButton("Show fields");
        buttonFilter.setForeground(ColorUtil.darkGreen);
        averagePrice.setForeground(ColorUtil.darkGreen);
        filters.setForeground(ColorUtil.darkGreen);
        clear.setForeground(ColorUtil.LightSlateGray);
        optsButton.setForeground(ColorUtil.LightSlateGray);
        save.setForeground(ColorUtil.LightSlateGray);

        data.setNumbersItem(numbersItem);
        data.setReferenceId(referenceId);
        data.setConditionsField(conditionsField);
        data.setListTypeField(listTypeField);
        data.setText(text);
        data.setSortedTypeField(sortedTypeField);
        data.setGoldenSearch(goldenSearch);
        data.setDaysLeft(daysLeft);
        data.setButtonFilter(buttonFilter);

        //  In this section we paint own components, We use special layer manager GraphPaperLayout 
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GraphPaperLayout(new Dimension(27, 27), 1, 1));
        panel.add(new JLabel("Search reference id"), new Rectangle(0, 0, 5, 1));
        panel.add(new JLabel("Auction ID"), new Rectangle(0, 1, 5, 1));
        panel.add(numbersItem, new Rectangle(6, 0, 4, 1));
        panel.add(referenceId, new Rectangle(4, 1, 6, 1));
        panel.add(loadReferenceIDList, new Rectangle(24, 1, 3, 1));
        panel.add(searchUPC, new Rectangle(11, 1, 5, 1));
        panel.add(goldenSearch, new Rectangle(11, 2, 5, 1));
        panel.add(searchReference, new Rectangle(11, 0, 5, 1));
        panel.add(new JLabel("Condition"), new Rectangle(0, 2, 5, 1));
        panel.add(conditionsField, new Rectangle(4, 2, 6, 1));
        panel.add(new JLabel("List Type"), new Rectangle(0, 3, 5, 1));
        panel.add(listTypeField, new Rectangle(4, 3, 6, 1));
        panel.add(showResultFilter, new Rectangle(11, 4, 5, 1));
        panel.add(new JScrollPane(text), new Rectangle(0, 5, 13, 21));
        panel.add(new ProductPanel(main, data), new Rectangle(13, 5, 15, 26));
        panel.add(clear, new Rectangle(0, 26, 3, 1));
        panel.add(save, new Rectangle(24, 0, 3, 1));
        panel.add(new JLabel("Sorted type"), new Rectangle(0, 4, 5, 1));
        panel.add(sortedTypeField, new Rectangle(4, 4, 6, 1));
        panel.add(optsButton, new Rectangle(4, 26, 4, 1));
        JLabel lbDayLeft = new JLabel("Days Left");
        lbDayLeft.setForeground(ColorUtil.LightSlateGray);
        panel.add(lbDayLeft, new Rectangle(11, 3, 4, 1));
        panel.add(daysLeft, new Rectangle(14, 3, 4, 1));
        panel.add(buttonFilter, new Rectangle(18, 0, 4, 1));
        panel.add(filters, new Rectangle(18, 1, 4, 1));
        panel.add(averagePrice, new Rectangle(18, 2, 4, 1));
        panel.add(showFields, new Rectangle(18, 3, 4, 1));

        // in this section we addFilter listeners in components, We use listeners for handle some action like press on button or change some items in combobox
        searchReference.addActionListener(new ReferenceIDLinteren(panel));
        showResultFilter.addActionListener(new ItemsSearchListener(panel));
        clear.addActionListener(new ClearListener(panel));
        save.addActionListener(new SaveListener(panel));
        loadReferenceIDList.addActionListener(new LoadFileSearchItemActionListener(main, data));
        optsButton.addActionListener(new ChooseOptsListener(panel));
        searchUPC.addActionListener(new UPCSearchListener(panel));
        buttonFilter.addActionListener(new FilterActionListener(main, data));
        averagePrice.addActionListener(new AveragePriceActionListener(main, data));
        filters.addActionListener(new OpenDialogFilterActionListener(main, data));
        showFields.addActionListener(new OpenDialogShowFieldsActionListener(main, data));
    }

    public JFrame getMain() {
        return main;
    }

    public Data getData() {
        return data;
    }

    public JTextField getNumbersItem() {
        return numbersItem;
    }

    public JTextField getReferenceId() {
        return referenceId;
    }

    public JTextField getConditionsField() {
        return conditionsField;
    }

    public JTextField getListTypeField() {
        return listTypeField;
    }

    public JTextArea getText() {
        return text;
    }

    public JComboBox<Pair<SortOrderType>> getSortedTypeField() {
        return sortedTypeField;
    }

    public JCheckBox getGoldenSearch() {
        return goldenSearch;
    }

    public JTextField getDaysLeft() {
        return daysLeft;
    }
}
