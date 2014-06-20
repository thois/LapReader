/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.ui.gui;

import fi.helsinki.cs.thois.lapreader.Controller;
import fi.helsinki.cs.thois.lapreader.model.Heat;
import fi.helsinki.cs.thois.lapreader.model.Model;
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author niko
 */
public class HeatListView extends ListView {
    
    private TestDay day;
    private Heat[] heats;
    
    public HeatListView(Controller controller, TestDay day) throws SQLException {
        super(controller);
        this.day = day;
        //TODO make clean actionListener
        super.showButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        showButtonActionPerformed(evt);
    }
});
        Object[] columnNames = {"Date", "Heats"};
        super.columnNames = columnNames;
        getListTitle().setText("Heats in " + this.day + " :");
        refreshData();
    }
    
        protected void showButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int id = getjTable1().getSelectedRow();
        if (id >= 0 && id < heats.length) {
            try {
                LapListView dayListView = new LapListView(controller,
                        heats[id]);            
                dayListView.setVisible(true);
            } catch (SQLException ex) {
                displaySqlError();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select first existing heat!");
        }
    }
    
    public void refreshData() throws SQLException {
        heats = controller.getHeats(day).toArray(new Heat[0]);
        super.refreshData(new ArrayList<Model>(Arrays.asList(heats)));
    }
    
}
