package fi.helsinki.cs.thois.lapreader.ui.gui;

import fi.helsinki.cs.thois.lapreader.Controller;
import fi.helsinki.cs.thois.lapreader.model.Heat;
import fi.helsinki.cs.thois.lapreader.model.Model;
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class HeatListView extends ListView {
    
    private TestDay day;
    private Heat[] heats;
    
    public HeatListView(Controller controller, TestDay day) throws SQLException {
        super(controller);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.day = day;
        //TODO make clean actionListener
        super.showButton.addActionListener(new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        showButtonActionPerformed(evt);
    }
});
        Object[] columnNames = {"Time", "Result"};
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
