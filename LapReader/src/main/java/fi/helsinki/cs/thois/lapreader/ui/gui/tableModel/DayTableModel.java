package fi.helsinki.cs.thois.lapreader.ui.gui.tableModel;

import javax.swing.table.DefaultTableModel;

public class DayTableModel extends DefaultTableModel {
    
    @Override
    public boolean isCellEditable(int row, int column) {
        if (column == 1)
            return false;
        return true;
    }
}
