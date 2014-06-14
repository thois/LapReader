/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.ui.gui;

import fi.helsinki.cs.thois.lapreader.Controller;
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author niko
 */
public class DayListView extends ListView {
    
    public DayListView(Controller controller) {
        super(controller);
    }
    
    public void refreshDays() throws SQLException {
        Object[] columnNames = {"Date", "Heats"};
        List<TestDay> days = controller.getDays();
        Object[][] data;
        if (days == null || days.isEmpty()) {
            data = new Object[0][columnNames.length];
        } else {
            data = new Object[days.size()][columnNames.length];
            for (int i = 0; i < days.size(); i++) {
                data[i] = days.get(i).getRowData();
                System.out.println((String)data[i][0] + (String)data[i][1]);
            }
        }
        setjTable1(new JTable(data, columnNames));
    }
    
}
