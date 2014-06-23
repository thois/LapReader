package fi.helsinki.cs.thois.lapreader.ui.gui.tableModel;

import javax.swing.table.DefaultTableModel;

/**
 * Table model for heats in a heatListView
 */
public class HeatTableModel extends DefaultTableModel {
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 0;
    }
}
