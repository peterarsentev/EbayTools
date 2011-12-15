package linteners;

import com.ebay.services.finding.SearchItem;
import model.Data;
import panel.SearchPanel;
import util.Pair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

public class SaveListener implements ActionListener {
    private JFileChooser fc;
    private JFrame main;
    private JTextArea text;
    private Data data;

    public SaveListener(SearchPanel panel) {
        this.fc = new JFileChooser();
        this.main = panel.getMain();
        this.text = panel.getText();
        this.data = panel.getData();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int returnVal = fc.showOpenDialog(main);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            Map<Pair, List<SearchItem>> map = data.getSaveData();
            if (map != null) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<Pair, List<SearchItem>> entry : map.entrySet()) {
                    sb.append(entry.getKey().getKey()).append(" (").append(entry.getKey().getValue()).append(")").append("\n\n");
                    if (entry.getValue() != null) {
                        for (SearchItem item : entry.getValue()) {
                            sb.append(item.getItemId()).append("\n");
                        }
                    }
                }
                save(file, sb.toString());
                text.setText(text.getText() + "Save was done. file : " + file.getAbsolutePath() + "\n") ;

            } else {
            }
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
