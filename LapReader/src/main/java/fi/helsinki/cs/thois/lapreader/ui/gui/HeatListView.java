package fi.helsinki.cs.thois.lapreader.ui.gui;

import fi.helsinki.cs.thois.lapreader.Controller;
import fi.helsinki.cs.thois.lapreader.model.Heat;
import fi.helsinki.cs.thois.lapreader.model.Model;
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import fi.helsinki.cs.thois.lapreader.parser.OrionParser;
import fi.helsinki.cs.thois.lapreader.ui.gui.tableModel.HeatTableModel;
import fi.helsinki.cs.thois.lapreader.ui.gui.tableModel.ListViewTableModelListener;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class HeatListView extends ListView {
    
    private TestDay day;
    private Heat[] heats;
    JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
    
    public HeatListView(Controller controller, TestDay day) throws SQLException {
        super(controller);
        DefaultTableModel model = new HeatTableModel();
        model.addTableModelListener(new ListViewTableModelListener(this));
        jTable1.setModel(model);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.day = day;
        ActionListener listener = new ListViewActionListener(this);
        showButton.addActionListener(listener);
        Object[] columnNames = {"Time", "Result"};
        super.columnNames = columnNames;
        getListTitle().setText("Heats in " + this.day + " :");
        refreshData();
    }
    
    private void showHeat(Heat heat) {
        try {
            LapListView lapListView = new LapListView(controller, heat);
            lapListView.setVisible(true);
        } catch (SQLException ex) {
            displaySqlError();
        }
    }
    
    @Override
    public void showButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int id = getjTable1().getSelectedRow();
        if (id >= 0 && id < heats.length) {
            showHeat(heats[id]);
        } else {
            JOptionPane.showMessageDialog(this, "Select first existing heat!");
        }
    }
    
    private void refreshData() throws SQLException {
        heats = controller.getHeats(day).toArray(new Heat[0]);
        super.refreshData(new ArrayList<Model>(Arrays.asList(heats)));
    }
    
    private File selectLapsFile() {
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
            return fc.getSelectedFile();
        return null;       
    }
    
    private void addHeat(String time) throws SQLException {
        File file = selectLapsFile();
        if (file == null)
            return;
        try {
            try {
                Heat createdHeat = controller.addHeatFromFile(day,
                        file.getPath(), time, new OrionParser());
                showHeat(createdHeat);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error in reading file!");
            }
        } catch (ParseException ex) {
            //TODO start editing cell without firing cellChanged event
            //jTable1.editCellAt(row, 0);
            JOptionPane.showMessageDialog(this, "Fill time in format HH.mm");
        }
    }
    
    private void modifyHeat(int id) {
        //TODO modify heat
        JOptionPane.showMessageDialog(this,
                "Modifying existing heat not yet implemented");
    }
    
    @Override
    public void rowChangedAction(int row) {
        if (row < 0)
            return;
        try {
            if (row < heats.length) {
                modifyHeat(row);
            } else {
                addHeat((String)jTable1.getValueAt(row, 0));
            }
            refreshData();
        } catch (SQLException ex) {
            displaySqlError();
        }
    }
}
