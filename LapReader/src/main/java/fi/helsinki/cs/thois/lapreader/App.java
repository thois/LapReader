package fi.helsinki.cs.thois.lapreader;

import fi.helsinki.cs.thois.lapreader.ui.gui.Gui;
import fi.helsinki.cs.thois.lapreader.ui.text.TextUi;
import java.sql.SQLException;

/**
 * Application's main class that creates controller for main logic and opens UI
 */
public class App 
{
    

    /**
     * Runnable main method that starts the program ui
     * @param args command line arguments
     */    
    public static void main( String[] args )
    {
        String databaseUrl = "jdbc:sqlite:db.db";
        Controller controller;
        try {
            controller = new Controller(databaseUrl);
            controller.foreigKeysOn();
        } catch (SQLException ex) {
            System.out.println("Virhe tietokannan " + databaseUrl + " avaamisessa");
            return;
        }
        //TextUi ui = new TextUi(controller);
        //ui.mainMenu();
        Gui gui = new Gui(controller);
        gui.run();
        
    }
}
