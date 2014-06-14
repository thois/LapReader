/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.ui.gui;

import com.j256.ormlite.dao.ForeignCollection;
import fi.helsinki.cs.thois.lapreader.Controller;
import fi.helsinki.cs.thois.lapreader.model.Heat;
import fi.helsinki.cs.thois.lapreader.model.Lap;
import fi.helsinki.cs.thois.lapreader.model.Model;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author niko
 */
public class LapListView extends ListView {
    
    private Heat heat;
    private ForeignCollection<Lap> laps;
    
    public LapListView(Controller controller, Heat heat) throws SQLException {
        super(controller);
        this.heat = heat;
        Object[] columnNames = {"Lapnumber", "Laptime"};
        super.columnNames = columnNames;
        getListTitle().setText("Days " + heat.getTestDay() + " heat " + heat + " :");
        refreshData();
    }
    
    public void refreshData() throws SQLException {
        laps = controller.getLaps(heat);
        super.refreshData(new ArrayList<Model>(laps));
    }
    
}
