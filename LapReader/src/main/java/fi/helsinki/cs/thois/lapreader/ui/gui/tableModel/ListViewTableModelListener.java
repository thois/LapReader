package fi.helsinki.cs.thois.lapreader.ui.gui.tableModel;

import fi.helsinki.cs.thois.lapreader.ui.gui.ListView;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * Listener for table changes for forms inherited from listView
 */
public class ListViewTableModelListener implements TableModelListener {
    
    ListView view;

    public ListViewTableModelListener(ListView view) {
    this.view = view;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
            int firstRow = e.getFirstRow();
            int lastRow = e.getLastRow();
            for (int i = firstRow; i <= lastRow; i++) {
                view.rowChangedAction(i);
            }
        }
    }
    
}
