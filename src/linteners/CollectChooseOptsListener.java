package linteners;

import model.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CollectChooseOptsListener implements ActionListener {
    private Data data;
    private List<JCheckBox> checkBoxes;
    private JDialog dialog;

    public CollectChooseOptsListener(JDialog dialog, Data data, List<JCheckBox> checkBoxes) {
        this.data = data;
        this.checkBoxes = checkBoxes;
        this.dialog = dialog;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Map<String, Boolean> showOpts = new LinkedHashMap<String, Boolean>();
        for (JCheckBox box : checkBoxes) {
            if (box.isSelected()) {
                showOpts.put(box.getText(), true);
            }
        }
        data.setShowOpts(showOpts);
        dialog.setVisible(false);
        dialog.dispose();
    }
}
