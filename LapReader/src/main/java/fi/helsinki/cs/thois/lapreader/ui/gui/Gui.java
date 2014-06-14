/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.ui.gui;

import fi.helsinki.cs.thois.lapreader.Controller;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        DayListView dayListView = new DayListView(controller);
        try {
            dayListView.refreshData();
            dayListView.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
