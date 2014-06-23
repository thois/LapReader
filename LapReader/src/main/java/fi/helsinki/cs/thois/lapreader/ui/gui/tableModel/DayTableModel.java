package fi.helsinki.cs.thois.lapreader.ui.gui.tableModel;

import javax.swing.table.DefaultTableModel;

/**
 * Table model for days in a dayListView
 */
public class DayTableModel extends DefaultTableModel {
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 0;
    }
}
