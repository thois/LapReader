package fi.helsinki.cs.thois.lapreader.ui.gui;

import com.j256.ormlite.dao.ForeignCollection;
import fi.helsinki.cs.thois.lapreader.Controller;
import fi.helsinki.cs.thois.lapreader.model.Heat;
import fi.helsinki.cs.thois.lapreader.model.Lap;
import fi.helsinki.cs.thois.lapreader.model.Model;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;

public class LapListView extends ListView {
    
    private Heat heat;
    private ForeignCollection<Lap> laps;
    
    public LapListView(Controller controller, Heat heat) throws SQLException {
        super(controller);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showButton.setVisible(false);
        addButton.setVisible(false);
        deleteButton.setVisible(false);
        this.heat = heat;
        Object[] columnNames = {"Lapnumber", "Laptime"};
        super.columnNames = columnNames;
        getListTitle().setText("Day " + heat.getTestDay() + " heat at " + heat + " :");
        refreshData();
    }
    
    private void refreshData() throws SQLException {
        laps = controller.getLaps(heat);
        super.refreshData(new ArrayList<Model>(laps));
    }
    
}
