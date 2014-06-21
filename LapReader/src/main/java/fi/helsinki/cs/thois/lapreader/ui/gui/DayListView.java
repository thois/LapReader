package fi.helsinki.cs.thois.lapreader.ui.gui;

import fi.helsinki.cs.thois.lapreader.Controller;
import fi.helsinki.cs.thois.lapreader.model.Model;
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import fi.helsinki.cs.thois.lapreader.ui.gui.tableModel.DayTableModel;
import fi.helsinki.cs.thois.lapreader.ui.gui.tableModel.ListViewTableModelListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DayListView extends ListView {
    
    List<TestDay> days;
    
    public DayListView(Controller controller) throws SQLException {
        super(controller);
        DefaultTableModel model = new DayTableModel();
        model.addTableModelListener(new ListViewTableModelListener(this));
        jTable1.setModel(model);
        //TODO make clean actionListener
        super.showButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        showButtonActionPerformed(evt);
    }
});
        Object[] columnNames = {"Date", "Heats"};
        super.columnNames = columnNames;
        getListTitle().setText("Dates");
        refreshData();
    }
    
    private void refreshData() throws SQLException {
        days = controller.getDays();
        super.refreshData(new ArrayList<Model>(days));
    }
    
    protected void showButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int id = getjTable1().getSelectedRow();
        if (id >= 0 && id < days.size()) {
            try {
                HeatListView heatListView = new HeatListView(controller,
                        days.get(id));            
                heatListView.setVisible(true);
            } catch (SQLException ex) {
                displaySqlError();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select first existing day!");
        }
    }

    @Override
    public void rowChangedAction(int row) {
        if (row < 0)
            return;
        try {
            if (row < days.size()) {
                //TODO modify day
                JOptionPane.showMessageDialog(this,
                        "Modifying existing day not yet implemented");
                return;
            } else {
                try {
                    controller.addDay((String)jTable1.getValueAt(row, 0));
                } catch (ParseException ex) {
                    //TODO start editing cell without firing cellChanged event
                    //jTable1.editCellAt(row, 0);
                    JOptionPane.showMessageDialog(this, "Fill date in form dd.MM.yyyy");
                }
            }
            refreshData();
        } catch (SQLException ex) {
            displaySqlError();
        }
    }
    
}
