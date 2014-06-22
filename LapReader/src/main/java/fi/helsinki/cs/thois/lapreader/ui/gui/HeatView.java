package fi.helsinki.cs.thois.lapreader.ui.gui;

import com.j256.ormlite.dao.ForeignCollection;
import fi.helsinki.cs.thois.lapreader.Controller;
import fi.helsinki.cs.thois.lapreader.model.Heat;
import fi.helsinki.cs.thois.lapreader.model.Lap;
import fi.helsinki.cs.thois.lapreader.model.Model;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class HeatView extends javax.swing.JFrame {

    /**
     * Creates new form ListView
     */
    public HeatView(Controller controller, Heat heat) throws SQLException {
        initComponents();
        this.controller = controller;
        this.heat = heat;
        this.heat = heat;
        Object[] columnNames = {"Lapnumber", "Laptime"};
        this.columnNames = columnNames;
        listTitle.setText("Day " + heat.getTestDay() + " heat at " + heat + " :");
        
        refreshData();
        chartPanel = new ChartPanel(chart);
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(chartPanel, BorderLayout.NORTH);
        jLabelTrackRecord.setVisible(false);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        listTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabelSetupChanges = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaSetupChanges = new javax.swing.JTextArea();
        saveButton = new javax.swing.JButton();
        jLabelBestLap = new javax.swing.JLabel();
        jLabelAvg = new javax.swing.JLabel();
        jLabelTrackRecord = new javax.swing.JLabel();
        jLabelTrack = new javax.swing.JLabel();
        jLabelCar = new javax.swing.JLabel();
        jLabelTrackTemp = new javax.swing.JLabel();
        jLabelAirTemp = new javax.swing.JLabel();
        jTextFieldTrackTemp = new javax.swing.JTextField();
        jTextFieldAirTemp = new javax.swing.JTextField();
        jLabelClass = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        listTitle.setText("Title");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 416, Short.MAX_VALUE)
        );

        jLabelSetupChanges.setText("Setup changes:");

        jTextAreaSetupChanges.setColumns(20);
        jTextAreaSetupChanges.setRows(5);
        jScrollPane2.setViewportView(jTextAreaSetupChanges);

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        jLabelBestLap.setText("Best Lap:  15.560 in lap 5");

        jLabelAvg.setText("Average: 16.580 ");

        jLabelTrackRecord.setText("Track record for result! Track record for best lap!");

        jLabelTrack.setText("Track: Tattarisuo, small");

        jLabelCar.setText("Car: Xray T3");

        jLabelTrackTemp.setText("Track temp:");

        jLabelAirTemp.setText("Air temp:");

        jLabelClass.setText("Class: TSP-10");

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(listTitle)
                            .addComponent(saveButton))
                        .addGap(0, 1014, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelSetupChanges)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelAirTemp)
                                    .addComponent(jTextFieldAirTemp)
                                    .addComponent(jLabelTrackTemp)
                                    .addComponent(jTextFieldTrackTemp))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelBestLap)
                                    .addComponent(jLabelAvg)
                                    .addComponent(jLabelTrack)
                                    .addComponent(jLabelCar)
                                    .addComponent(jLabelClass)
                                    .addComponent(jLabelTrackRecord))
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(listTitle)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelSetupChanges)
                                .addGap(8, 8, 8))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelTrackTemp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextFieldTrackTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelAirTemp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldAirTemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelBestLap)
                        .addGap(4, 4, 4)
                        .addComponent(jLabelAvg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTrack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelCar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelClass)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTrackRecord)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        heat.setSetupChanges(jTextAreaSetupChanges.getText());
        //TODO refactor dublicate code to own method
        if (!jTextFieldTrackTemp.getText().isEmpty())
            try {
                heat.setTrackTemp(Integer.parseInt(jTextFieldTrackTemp.getText()));
            } catch (NumberFormatException exp) {
                jTextFieldTrackTemp.requestFocus();
                displayNumberFormatError();
                return;
            }
        else
            heat.setTrackTemp(null);
        if (!jTextFieldAirTemp.getText().isEmpty())
            try {
                heat.setAirTemp(Integer.parseInt(jTextFieldAirTemp.getText()));
            } catch (NumberFormatException exp) {
                jTextFieldAirTemp.requestFocus();
                displayNumberFormatError();
                return;
            }
        else
            heat.setAirTemp(null);
        try {
            controller.updateHeat(heat);
        } catch (SQLException ex) {
            displaySqlError();
            return;
        }
        JOptionPane.showMessageDialog(this, "Additional data saved!");
    }//GEN-LAST:event_saveButtonActionPerformed
    
    private void displayNumberFormatError() {
        JOptionPane.showMessageDialog(this,
                "Please write only numbers to temp. Data not saved!");
    }
    
    private Object[][] constructTable(List<Model> models, int columns) {
        Object[][] data;
        if (models == null) {
            data = new Object[0][columns];
        } else {
            data = new Object[models.size()][columns];
            for (int i = 0; i < models.size(); i++)
                data[i] = models.get(i).getRowData();
        }
        return data;
    }
    
    protected void refreshData() throws SQLException {
        laps = controller.getLaps(heat);
        ArrayList<Model> models = new ArrayList<Model>(laps);
        Object[][] data = constructTable(models, columnNames.length);
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        model.setDataVector(data, columnNames);
        refreshAdditionalData();
        refreshPlot();
    }
    
    protected void refreshAdditionalData() throws SQLException {
        jTextAreaSetupChanges.setText(heat.getSetupChanges());
        if (heat.getTrackTemp() != null)
            jTextFieldAirTemp.setText(""+heat.getAirTemp());
        if (heat.getAirTemp() != null)
            jTextFieldTrackTemp.setText(""+heat.getTrackTemp());
        jLabelAvg.setText("Average: " + heat.getResult().avgLapTime());
        Lap best = controller.getBestLap(heat);
        jLabelBestLap.setText("Best Lap: " + best + " in lap " + best.getLapNumber());
    }
    
    protected void refreshPlot() {
        XYSeries series = new XYSeries(heat.toString());
        for (Lap l : laps)
            series.add(l.getLapNumber(), ((double)l.getTime())/1000);
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        chart = ChartFactory.createXYLineChart(null, "Lap", "Laptime", dataset,
                PlotOrientation.VERTICAL, false, true, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)plot.getRenderer();
        renderer.setShapesVisible(true);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);
    }
    
    protected void displaySqlError() {
        JOptionPane.showMessageDialog(this,
                "Database error! Restart app and try again.");
    }
    
    public void rowChangedAction(int row) {
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabelAirTemp;
    private javax.swing.JLabel jLabelAvg;
    private javax.swing.JLabel jLabelBestLap;
    private javax.swing.JLabel jLabelCar;
    private javax.swing.JLabel jLabelClass;
    private javax.swing.JLabel jLabelSetupChanges;
    private javax.swing.JLabel jLabelTrack;
    private javax.swing.JLabel jLabelTrackRecord;
    private javax.swing.JLabel jLabelTrackTemp;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    protected javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextAreaSetupChanges;
    private javax.swing.JTextField jTextFieldAirTemp;
    private javax.swing.JTextField jTextFieldTrackTemp;
    private javax.swing.JLabel listTitle;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables

    protected Controller controller;
    protected Object[] columnNames;
    private Heat heat;
    private ForeignCollection<Lap> laps;
    private ChartPanel chartPanel;
    private JFreeChart chart;
}