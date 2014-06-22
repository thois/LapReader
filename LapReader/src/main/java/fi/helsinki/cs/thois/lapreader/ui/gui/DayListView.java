package fi.helsinki.cs.thois.lapreader.ui.gui;

import fi.helsinki.cs.thois.lapreader.Controller;
import fi.helsinki.cs.thois.lapreader.model.*;
import fi.helsinki.cs.thois.lapreader.ui.gui.tableModel.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class DayListView extends ListView {
    
    List<TestDay> days;
    
    public DayListView(Controller controller) throws SQLException {
        super(controller);
        DefaultTableModel model = new DayTableModel();
        model.addTableModelListener(new ListViewTableModelListener(this));
        jTable1.setModel(model);
        ActionListener listener = new ListViewActionListener(this);
        showButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
        Object[] columnNames = {"Date", "Heats"};
        super.columnNames = columnNames;
        getListTitle().setText("Dates");
        refreshData();
    }
    
    private void setColumnWidths() {
        TableColumnModel model = jTable1.getColumnModel();
        model.getColumn(1).setPreferredWidth(50);
        model.getColumn(2).setPreferredWidth(150);
        model.getColumn(3).setPreferredWidth(130);
    }
    
    private void refreshData() throws SQLException {
        days = controller.getDays();
        super.refreshData(new ArrayList<Model>(days));
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        addBestLaps(model);
        addBestResults(model);
        setColumnWidths();
    }
    
    private void addBestLaps(DefaultTableModel model) throws SQLException {
        Object[] data = new Object[days.size()];
        for (int i = 0; i < days.size(); i++) {
            Lap best = controller.getBestLap(days.get(i));
            if (best == null)
                data[i] = "";
            else
                data[i] = best + " (" + best.getLapNumber() + ") at "
                        + best.getHeat().timeToString();
        }
        model.addColumn("Best lap", data);
    }
    
    private void addBestResults(DefaultTableModel model) throws SQLException {
        Object[] data = new Object[days.size()];
        for (int i = 0; i < days.size(); i++) {
            Result best = controller.getBestResult(days.get(i));
            if (best == null)
                data[i] = "";
            else
                data[i] = best.toString();
        }
        model.addColumn("Best result", data);
    }
    
    private void openDay(TestDay day) {
        try {
            HeatListView heatListView = new HeatListView(controller, day);
            heatListView.setVisible(true);
        } catch (SQLException ex) {
            displaySqlError();
        }
    }
    
    @Override
    public void showButtonActionPerformed(ActionEvent evt) {
        int id = getjTable1().getSelectedRow();
        if (id >= 0 && id < days.size()) {
                openDay(days.get(id));
        } else {
            JOptionPane.showMessageDialog(this, "Select first existing day!");
        }
    }

    @Override
    public void deleteButtonActionPerformed(ActionEvent evt) {
        int id = getjTable1().getSelectedRow();
        if (id >= 0 && id < days.size()) {
            int selection = JOptionPane.showConfirmDialog(this, "Are you sure to delete " +
                    days.get(id) + " ?", "Are you sure?",
                JOptionPane.YES_NO_OPTION);
            if (selection != 0)
                return;
            try {
                controller.deleteDay(days.get(id));
                refreshData();
            } catch (SQLException ex) {
                displaySqlError();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select first existing day!");
        }
    }
    
    private void addDay(String day) throws SQLException {
        try {
            openDay(controller.addDay(day));
        } catch (ParseException ex) {
            //TODO start editing cell without firing cellChanged event
            //jTable1.editCellAt(row, 0);
            JOptionPane.showMessageDialog(this, "Fill date in form dd.MM.yyyy");
        }
    }
    
    private void modifyDay(int id) throws SQLException {
        TestDay day = days.get(id);
        try {
            controller.updateDay(day, (String)jTable1.getValueAt(id, 0));
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this,
                "Date must be in format dd.MM.yyyy");
        }
    }
    
    @Override
    public void rowChangedAction(int row) {
        if (row < 0)
            return;
        try {
            if (row < days.size()) {
                modifyDay(row);
            } else
                addDay((String)jTable1.getValueAt(row, 0));
            refreshData();
        } catch (SQLException ex) {
            displaySqlError();
        }
    }
    
}
