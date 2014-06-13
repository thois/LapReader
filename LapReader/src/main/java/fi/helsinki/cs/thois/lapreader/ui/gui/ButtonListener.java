/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.ui.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author niko
 */
public class ButtonListener implements ActionListener {
    private final ListView view;
    
    public ButtonListener(ListView view) {
        this.view = view;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String txt = ((JButton)e.getSource()).getText();
        switch (txt) {
            case "Display":
                view.display();
                break;
            case "Delete":
                view.delete();
                break;
            case "Add":
                view.addItem();
                break;
        }
    }
}
