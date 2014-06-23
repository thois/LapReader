package fi.helsinki.cs.thois.lapreader.ui.gui;

import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * ActionListener for ListView to get buttons work
 */
public class ListViewActionListener implements ActionListener {
    ListView view;
    
    ListViewActionListener(ListView view) {
        this.view = view;
    }
    
    @Override
     public void actionPerformed(java.awt.event.ActionEvent evt) {
         JButton button = (JButton)evt.getSource();
        switch (button.getText()) {
            case "Show":
                view.showButtonActionPerformed(evt);
                break;
            case "Delete":
                view.deleteButtonActionPerformed(evt);
                break;
        }
    }
}
