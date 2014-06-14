/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.ui.gui;

import fi.helsinki.cs.thois.lapreader.Controller;
import fi.helsinki.cs.thois.lapreader.model.Model;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author niko
 */
public class DayListView extends ListView {
    
    public DayListView(Controller controller) {
        super(controller);
        Object[] columnNames = {"Date", "Heats"};
        super.columnNames = columnNames;
    }
    
    public void refreshData() throws SQLException {
        List<Model> days = new ArrayList<Model>(controller.getDays());
        ListView.this.refreshData(days);
    }
    
}
