package fi.helsinki.cs.thois.lapreader.ui.gui;

import fi.helsinki.cs.thois.lapreader.Controller;
import fi.helsinki.cs.thois.lapreader.model.*;
import fi.helsinki.cs.thois.lapreader.parser.OrionParser;
import fi.helsinki.cs.thois.lapreader.ui.gui.tableModel.*;
import java.awt.event.ActionEvent;
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

/**
 * Form displays all heats from a day in list
 */
public class HeatListView extends ListView {
    
    /**
     * Day that is displayed and connected to the form
     */
    private TestDay day;
    
    /**
     * Heats in the day
     */
    private Heat[] heats;
    
    /**
     * File chooser dialog for selecting file to be imported
     */
    JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
    
    /**
     * Initializes and customizes ListView for heats
     * @param controller links form to the program logic
     * @param day to be displayed
     * @throws SQLException if database error occurs
     */
    public HeatListView(Controller controller, TestDay day) throws SQLException {
        super(controller);
        DefaultTableModel model = new HeatTableModel();
        model.addTableModelListener(new ListViewTableModelListener(this));
        jTable1.setModel(model);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.day = day;
        ActionListener listener = new ListViewActionListener(this);
        showButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
        Object[] columnNames = {"Time", "Result"};
        super.columnNames = columnNames;
        listTitle.setText("Heats in " + this.day + " :");
        refreshData();
    }
    
    /**
     * Shows a heat in a HeatView
     * @param heat to be displayed
     */
    private void showHeat(Heat heat) {
        try {
            HeatView lapListView = new HeatView(controller, heat);
            lapListView.setVisible(true);
        } catch (SQLException ex) {
            displaySqlError();
        }
    }
    
    @Override
    public void showButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int id = jTable1.getSelectedRow();
        if (id >= 0 && id < heats.length) {
            showHeat(heats[id]);
        } else {
            JOptionPane.showMessageDialog(this, "Select first existing heat!");
        }
    }
    
    @Override
    public void deleteButtonActionPerformed(ActionEvent evt) {
        int id = jTable1.getSelectedRow();
        if (id >= 0 && id < heats.length) {
            int selection = JOptionPane.showConfirmDialog(this,
                "Are you sure to delete " + heats[id] + " ?", "Are you sure?",
                JOptionPane.YES_NO_OPTION);
            if (selection != 0)
                return;
            try {
                controller.deleteHeat(heats[id]);
                refreshData();
            } catch (SQLException ex) {
                displaySqlError();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select first existing heat!");
        }
    }
    
    /**
     * Refreshes data in the form
     * @throws SQLException if database error occurs 
     */
    private void refreshData() throws SQLException {
        heats = controller.getHeats(day).toArray(new Heat[0]);
        super.refreshData(new ArrayList<Model>(Arrays.asList(heats)));
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        addBestLaps(model);
        addAvgs(model);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(100);
    }
    
    /**
     * Adds column including best laps in the table
     * @param model where column will be added
     * @throws SQLException if database error occurs 
     */
    private void addBestLaps(DefaultTableModel model) throws SQLException {
        Object[] data = new Object[heats.length];
        for (int i = 0; i < heats.length; i++) {
            Lap best = controller.getBestLap(heats[i]);
           data[i] = best + " (" + best.getLapNumber() + ")";
       }
       model.addColumn("Best lap", data);
    }
    
    /**
     * Adds column including average laptimes in the table
     * @param model where column will be added
     * @throws SQLException if database error occurs
     */
    private void addAvgs(DefaultTableModel model) throws SQLException {
        Object[] data = new Object[heats.length];
        for (int i = 0; i < heats.length; i++) {
           data[i] = heats[i].getResult().avgLapTime().toString();
       }
       model.addColumn("Average", data);
    }
    
    /**
     * Displays dialog to select file including laptimes to be imported
     * @return file that user selected
     */
    private File selectLapsFile() {
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
            return fc.getSelectedFile();
        return null;       
    }
    
    /**
     * Adds heat to the database
     * @param time time of the day when heat was run
     * @throws SQLException if database error occurs
     */
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
            JOptionPane.showMessageDialog(this, "Fill time in format HH:mm");
        }
    }
    
    /**
     * Modifies time of the heat
     * @param id index to heat row to be modified
     * @throws SQLException if database error occurs
     */
    private void modifyHeat(int id) throws SQLException {
        Heat heat = heats[id];
        try {
            controller.updateHeat(heat, (String)jTable1.getValueAt(id, 0));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this,
                "Time must be in format HH:mm");
        }
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
