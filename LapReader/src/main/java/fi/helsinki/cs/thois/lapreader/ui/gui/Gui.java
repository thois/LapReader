/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.ui.gui;

import fi.helsinki.cs.thois.lapreader.Controller;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author niko
 */
public class Gui implements Runnable {
    
    private final Controller controller;
    
    public Gui(Controller controller) {
        this.controller = controller;
    }
    
    @Override
    public void run() {
        try {
            DayListView dayListView = new DayListView(controller);
            dayListView.setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Tietokantavirhe!");
        }
        
    }
    
}
