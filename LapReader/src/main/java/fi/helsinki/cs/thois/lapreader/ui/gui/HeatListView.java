/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.ui.gui;

import com.j256.ormlite.dao.ForeignCollection;
import fi.helsinki.cs.thois.lapreader.Controller;
import fi.helsinki.cs.thois.lapreader.model.Heat;
import fi.helsinki.cs.thois.lapreader.model.Model;
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author niko
 */
public class HeatListView extends ListView {
    
    private TestDay day;
    private ForeignCollection<Heat> heats;
    
    public HeatListView(Controller controller, TestDay day) throws SQLException {
        super(controller);
        this.day = day;
        Object[] columnNames = {"Date", "Heats"};
        super.columnNames = columnNames;
        getListTitle().setText("Heats in " + this.day + " :");
        refreshData();
    }
    
    public void refreshData() throws SQLException {
        heats = controller.getHeats(day);
        super.refreshData(new ArrayList<Model>(heats));
    }
    
}
