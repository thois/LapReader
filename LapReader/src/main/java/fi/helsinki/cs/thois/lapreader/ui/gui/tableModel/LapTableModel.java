package fi.helsinki.cs.thois.lapreader.ui.gui.tableModel;

import javax.swing.table.DefaultTableModel;

/**
 * Table model for laps in a heatView
 */
public class LapTableModel extends DefaultTableModel {
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}