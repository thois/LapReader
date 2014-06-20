package fi.helsinki.cs.thois.lapreader.ui.gui;

import fi.helsinki.cs.thois.lapreader.Controller;
import fi.helsinki.cs.thois.lapreader.model.Model;
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DayListView extends ListView {
    
    List<TestDay> days;
    
    public DayListView(Controller controller) throws SQLException {
        super(controller);
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
    
    public void refreshData() throws SQLException {
        days = controller.getDays();
        super.refreshData(new ArrayList<Model>(days));
    }
    
    protected void showButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int id = getjTable1().getSelectedRow();
        if (id >= 0 && id < days.size()) {
            try {
                HeatListView dayListView = new HeatListView(controller,
                        days.get(id));            
                dayListView.setVisible(true);
            } catch (SQLException ex) {
                displaySqlError();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select first existing day!");
        }
    }
    
    protected void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
        
    }
    
    
}
