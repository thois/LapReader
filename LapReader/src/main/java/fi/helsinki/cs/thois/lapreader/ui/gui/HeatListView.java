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

/**
 *
 * @author niko
 */
public class HeatListView extends ListView {
    
    private TestDay selectedDay;
    
    public HeatListView(Controller controller, TestDay day) {
        super(controller);
        selectedDay = day;
        Object[] columnNames = {"Date", "Heats"};
        super.columnNames = columnNames;
    }
    
    public void refreshData() throws SQLException {
        List<Model> heats = new ArrayList<Model>(controller.getHeats(selectedDay));
        super.refreshData(heats);
    }
    
}
