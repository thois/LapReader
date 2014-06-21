package fi.helsinki.cs.thois.lapreader.ui.gui;

import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ListViewActionListener implements ActionListener {
    ListView view;
    
    ListViewActionListener(ListView view) {
        this.view = view;
    }
    
    @Override
     public void actionPerformed(java.awt.event.ActionEvent evt) {
         JButton button = (JButton)evt.getSource();
         if (button.getText().equals("Show"))
            view.showButtonActionPerformed(evt);
    }
}
