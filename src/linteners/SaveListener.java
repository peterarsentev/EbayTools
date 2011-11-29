package linteners;

import com.ebay.services.finding.SearchItem;
import util.SearchUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SaveListener implements ActionListener {
    private JFileChooser fc;
    private JPanel main;
    private JTextField productField;
    private SearchUtil util;
    private JTextArea text;

    public SaveListener(SearchUtil util, JPanel main, JTextField productField, JTextArea text) {
        this.fc = new JFileChooser();
        this.productField = productField;
        this.main = main;
        this.util = util;
        this.text = text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnVal = fc.showOpenDialog(main);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            List<SearchItem> items = util.getItemsByProductId(productField.getText());
            Map<String, List<SearchItem>> listByComdition = new LinkedHashMap<String, List<SearchItem>>(); //this map we use for sort our items by condition
            for (SearchItem item : items) {
                String condition = item.getCondition().getConditionDisplayName();
                List<SearchItem> conditionList = listByComdition.get(condition);
                if (conditionList == null) {
                    conditionList = new ArrayList<SearchItem>();
                }
                conditionList.add(item);
                listByComdition.put(condition, conditionList);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Product ID (").append(productField.getText()).append(")").append("\n\n");
            for (Map.Entry<String, List<SearchItem>> entry : listByComdition.entrySet()) {
                sb.append("Condition (").append(entry.getKey()).append(")").append("\n");
                for (SearchItem item : entry.getValue()) {
                    sb.append(item.getItemId()).append("\n");
                }
                sb.append("\n");
            }
            save(file, sb.toString());
            text.setText(text.getText() + "Save was done. file : " + file.getAbsolutePath() + "\n") ;
        }
    }

    public static void save(File file, String text) {
        try{
            FileWriter fstream = new FileWriter(file);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(text);
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
