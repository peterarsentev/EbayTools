package panel;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;
import linteners.*;
import model.Data;
import util.*;
import java.util.*;

import com.ebay.services.finding.*;

public class SearchPanel extends JPanel {
    private JFrame main;
    private Data data;
    private SearchUtil util;
    private JButton searchReference;
    private JButton showResultFilter;
    private JButton clear;
    private JButton save;
    private JButton optsButton;
    private JButton loadReferenceIDList;
    private JTextField numbersItem;
    private JTextField referenceId;
    private JTextField conditionsField;
    private JTextField listTypeField;
    private JTextArea text;
    private JComboBox<Pair<SortOrderType>> sortedTypeField;
    private JCheckBox goldenSearch;
    private JDateChooser startData;
    private JDateChooser endData;
    private JTextField daysLeft;
    private SearchPanel panel;

    public SearchPanel(JFrame main, Data data) {
        // This section consists from initial objects
        this.main = main;
        this.data = data;

        this.panel = this;
        this.util = new SearchUtil();
        this.searchReference = new JButton("Reference");
        this.showResultFilter = new JButton("Result");
        this.clear = new JButton("Clear");
        this.save = new JButton("Save");
        this.optsButton = new JButton("Choose opts.");
        this.loadReferenceIDList = new JButton("Load");
        this.numbersItem = new JTextField();
        this.referenceId = new JTextField();
        referenceId.setText("77826847");
        this.conditionsField = new JTextField();
        conditionsField.setText("Used");
        this.listTypeField = new JTextField();
        listTypeField.setText("Auction");
        this.text = new JTextArea();
        this.sortedTypeField = new JComboBox<Pair<SortOrderType>>();
        for (SortOrderType sortedType : SortOrderType.values()) {
            sortedTypeField.addItem(new Pair<SortOrderType>(sortedType.value(), sortedType));
        }
        this.goldenSearch = new JCheckBox("Golden Search");
        this.startData = new JDateChooser();
        this.endData = new JDateChooser();
        this.daysLeft = new JTextField();

        //  In this section we paint own components, We use special layer manager GraphPaperLayout 
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GraphPaperLayout(new Dimension(27, 27), 1, 1));
        panel.add(new JLabel("Search reference id"), new Rectangle(0, 0, 5, 1));
        panel.add(new JLabel("Reference ID"), new Rectangle(0, 1, 5, 1));
        panel.add(numbersItem, new Rectangle(6, 0, 4, 1));
        panel.add(referenceId, new Rectangle(4, 1, 6, 1));
        panel.add(loadReferenceIDList, new Rectangle(24, 1, 3, 1));
        panel.add(goldenSearch, new Rectangle(11, 1, 5, 1));
        panel.add(searchReference, new Rectangle(11, 0, 5, 1));
        panel.add(new JLabel("Condition"), new Rectangle(0, 2, 5, 1));
        panel.add(conditionsField, new Rectangle(4, 2, 6, 1));
        panel.add(new JLabel("List Type"), new Rectangle(0, 3, 5, 1));
        panel.add(listTypeField, new Rectangle(4, 3, 6, 1));
        panel.add(showResultFilter, new Rectangle(24, 4, 3, 1));
        panel.add(new JScrollPane(text), new Rectangle(0, 5, 27, 21));
        panel.add(clear, new Rectangle(0, 26, 3, 1));
        panel.add(save, new Rectangle(24, 0, 3, 1));
        panel.add(new JLabel("Sorted type"), new Rectangle(0, 4, 5, 1));
        panel.add(sortedTypeField, new Rectangle(4, 4, 6, 1));
        panel.add(optsButton, new Rectangle(4, 26, 4, 1));
        panel.add(new JLabel("Start date"), new Rectangle(11, 2, 3, 1));
        panel.add(new JLabel("End date"), new Rectangle(11, 3, 3, 1));
        panel.add(startData, new Rectangle(14, 2, 4, 1));
        panel.add(endData, new Rectangle(14, 3, 4, 1));
        panel.add(new JLabel("Days Left"), new Rectangle(11, 4, 4, 1));
        panel.add(daysLeft, new Rectangle(14, 4, 4, 1));

        // in this section we add listeners in components, We use listeners for handle some action like press on button or change some items in combobox  
        searchReference.addActionListener(new ReferenceIDLinteren(panel));
        showResultFilter.addActionListener(new ItemsSearchListener(panel));
        clear.addActionListener(new ClearListener(panel));
        save.addActionListener(new SaveListener(panel));
        loadReferenceIDList.addActionListener(new LoadFileListener(panel));
        optsButton.addActionListener(new ChooseOptsListener(panel));
    }

    public JFrame getMain() {
        return main;
    }

    public Data getData() {
        return data;
    }

    public SearchUtil getUtil() {
        return util;
    }

    public JButton getSearchReference() {
        return searchReference;
    }

    public JButton getShowResultFilter() {
        return showResultFilter;
    }

    public JButton getClear() {
        return clear;
    }

    public JButton getSave() {
        return save;
    }

    public JButton getOptsButton() {
        return optsButton;
    }

    public JButton getLoadReferenceIDList() {
        return loadReferenceIDList;
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

    public JDateChooser getStartData() {
        return startData;
    }

    public JDateChooser getEndData() {
        return endData;
    }

    public JTextField getDaysLeft() {
        return daysLeft;
    }
}
