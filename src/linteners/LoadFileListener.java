package linteners;

import com.ebay.services.finding.SearchItem;
import com.ebay.services.finding.SortOrderType;
import model.Data;
import panel.SearchPanel;
import util.FormatterText;
import util.Pair;
import util.SearchUtil;
import util.TextUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class LoadFileListener implements ActionListener {
    private JFileChooser fc;
    private JFrame main;
    private SearchUtil util;
    private JTextArea text;
    private JTextField condition;
    private JTextField listingType;
    private JComboBox<Pair<SortOrderType>> sortedTypeField;
    private JCheckBox golderSearch;
    private Data dataModel;
    private JTextField daysLeft;

    public LoadFileListener(SearchPanel panel) {
        this.fc = new JFileChooser();
        this.condition = panel.getConditionsField();
        this.listingType = panel.getListTypeField();
        this.sortedTypeField = panel.getSortedTypeField();
        this.main = panel.getMain();
        this.util = panel.getUtil();
        this.text = panel.getText();
        this.golderSearch = panel.getGoldenSearch();
        this.dataModel = panel.getData();
        this.daysLeft = panel.getDaysLeft();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnVal = fc.showOpenDialog(main);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String msg;
            if (file.exists()) {
                List<String> data = readFile(file);
                StringBuilder sb = new StringBuilder();
                sb.append("The file is loaded and to be made search.\n");
                Pair<SortOrderType> pairSorted = sortedTypeField.getItemAt(sortedTypeField.getSelectedIndex());
                String typeSearch;
                if (golderSearch.isSelected()) {
                    typeSearch = "GoldenItems : \n";
                } else {
                    typeSearch = "All items : \n";
                }
                sb.append(typeSearch);
                Map<Pair, List<SearchItem>> map = new LinkedHashMap<Pair, List<SearchItem>>();
                for (String id : data) {
                    if (TextUtil.isNotNull(id)) {
                        List<SearchItem> items = getResult(sb, pairSorted, id, "UPC", dataModel);
                        if (items.isEmpty()) {
                            items = getResult(sb, pairSorted, id, "ReferenceID", dataModel);
                            if (items.isEmpty()) {
                                sb.append("\nid : ").append(id).append(" doesn't have any match by peference and upc id!\n\n");
                                map.put(new Pair<String>(id, "doesn't have any match by reference and upc id!"), null);
                            } else {
                                FormatterText.formatForConsole(items, dataModel.getShowOpts(), id, "ReferenceID");
                                map.put(new Pair<String>(id, "ReferenceID"), items);
                            }
                        } else {
                            FormatterText.formatForConsole(items, dataModel.getShowOpts(), id, "UPC");
                            map.put(new Pair<String>(id, "UPC"), items);
                        }
                    }
                    dataModel.setSaveData(map);
                }
                msg = sb.toString();
            } else {
                msg = "Error this file does not exist file : " + file.getAbsolutePath() + "\n";
            }
            text.setText(text.getText() + msg);
        }
    }

    private List<SearchItem> getResult(final StringBuilder sb, Pair<SortOrderType> pairSorted, String id, String type, Data data) {
        List<SearchItem> items = new ArrayList<SearchItem>();
        List<SearchItem> searchItems = util.getItemsBySortedType(id, condition.getText(), listingType.getText(), pairSorted.getValue(), type, TextUtil.getIntegerOrNull(daysLeft.getText()));
        if (searchItems != null) {
            if (golderSearch.isSelected()) {
                List<SearchItem> goldenItems = SearchUtil.getGoldenItems(searchItems);
                items.addAll(goldenItems);
            } else {
                items.addAll(searchItems);
            }
        }
        if (!items.isEmpty()) {
            sb.append(FormatterText.formatForConsole(items, data.getShowOpts(), id, type));
            sb.append("Total items : ").append(items.size()).append("\n\n");
        }
        return items;
    }

    /**
     * This method reads text from a file.
     *
     * @param file input file
     * @return List output data
     */
    public static List<String> readFile(File file) {
        List<String> list = new ArrayList<String>();
        try {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                list.add(strLine);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return list;
    }
}
