package fi.helsinki.cs.thois.lapreader.ui.gui;

import fi.helsinki.cs.thois.lapreader.Controller;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Gui implements Runnable {
    
    private final Controller controller;
    
    public Gui(Controller controller) {
        this.controller = controller;
    }
    
    public void setLook() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        //setLook();
        try {
            DayListView dayListView = new DayListView(controller);
            dayListView.setVisible(true);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Tietokantavirhe!");
        }
        
    }
    
}
