package fi.helsinki.cs.thois.lapreader;

import fi.helsinki.cs.thois.lapreader.controller.Controller;
import fi.helsinki.cs.thois.lapreader.ui.text.TextUi;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    

        
    public static void main( String[] args )
    {
        String databaseUrl = "jdbc:sqlite:db.db";
        Controller controller;
        try {
            controller = new Controller(databaseUrl);
        } catch (SQLException ex) {
            System.out.println("Virhe tietokannan " + databaseUrl + " avaamisessa");
            return;
        }
        TextUi ui = new TextUi(controller);
        ui.mainMenu();
        
    }
}
