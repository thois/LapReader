/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.ui.gui;

import fi.helsinki.cs.thois.lapreader.Controller;
import fi.helsinki.cs.thois.lapreader.model.Model;
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author niko
 */
public class DayListView extends ListView {
    
    List<TestDay> days;
    
    public DayListView(Controller controller) throws SQLException {
        super(controller);
        Object[] columnNames = {"Date", "Heats"};
        super.columnNames = columnNames;
        getListTitle().setText("Päivät");
        refreshData();
    }
    
    public void refreshData() throws SQLException {
        days = controller.getDays();
        super.refreshData(new ArrayList<Model>(days));
    }
    
    protected void showButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int id = getjTable1().getSelectedRow();
        if (id >= 0 && id < days.size()) {
            HeatListView dayListView = new HeatListView(controller,
                days.get(id));
            dayListView.setVisible(true);            
        } else {
            JOptionPane.showMessageDialog(this, "Select first existing day!");
        }
    }
    
    protected void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
        
    }
    
}
