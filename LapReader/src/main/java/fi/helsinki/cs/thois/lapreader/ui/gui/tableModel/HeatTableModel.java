package fi.helsinki.cs.thois.lapreader.ui.gui.tableModel;

import javax.swing.table.DefaultTableModel;

public class HeatTableModel extends DefaultTableModel {
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return column != 1;
    }
}
