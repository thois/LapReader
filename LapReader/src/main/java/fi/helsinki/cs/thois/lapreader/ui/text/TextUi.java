package fi.helsinki.cs.thois.lapreader.ui.text;

import fi.helsinki.cs.thois.lapreader.Controller;
import fi.helsinki.cs.thois.lapreader.model.*;
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import fi.helsinki.cs.thois.lapreader.parser.OrionParser;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

/**
 * Contains text-mode UI for the app
 */
public class TextUi {
    /**
     * Reference to scanner, it is needed almost all user interface methods
     */
    Scanner scanner = new Scanner(System.in);
    
    /**
     * Contains selected day
     */
    private TestDay currentDay;
    
    /**
     * Reference to controller providing the program logic
     * @param controller 
     */
    private final Controller controller;
    
    /**
     * Creates textUi attacs controller
     * @param controller 
     */
    public TextUi(Controller controller) {
    this.controller = controller;
}
    /**
     * Asks day from user and adds it to the database
     * @throws SQLException when database error occurs
     */
    private void addDay() throws SQLException {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        while(true) {
            System.out.print("Anna päivämäärä muodossa dd.MM.yyyy (" +
                    df.format(new Date()) + "): ");
            try {
                currentDay = controller.addDay(scanner.nextLine());
                break;
            } catch (ParseException e) {
                    System.out.println("Virheellien päivä!");
            }
        }
    }
    
    /**
     * calculates digits in a number
     * @param n number to be evaluated
     * @return digits of number
     */
    public static int digits(int n) {
        return (int) Math.log10(n) + 1;
    }
    
    /**
     * Formats order number for printing by adding leading zeroes
     * @param number to be formatted
     * @param max largest number to be displayed
     * @return 
     */
    public static String formatOrderNumber(int number, int max) {
        String str = "";
        int zeros = digits(max)-digits(number);
        for (int i = 0; i < zeros; i++)
            str += 0;
        return str + number;
    }
    
    /**
     * Print models
     * @param <T> Model to be printed
     * @param l list of models to be printed
     */
    private <T> void printModels(T[] l) {
        System.out.println("Tietokannassa olevat:");
        for (int i = 0; i < l.length; i++) {
            System.out.println(formatOrderNumber(1+i, l.length)+".: " + l[i]);
        }
    }
    
    /**
     * Asks user to select day
     * @throws SQLException when database error occurs
     */
    private void selectDay() throws SQLException {
        List<TestDay> days = controller.getDays();
        if (days == null || days.isEmpty()) {
            System.out.println("Ei päiviä tietokannassa. Luo ensin päivä.");
            addDay();
            return;
        }
        printModels(days.toArray(new TestDay[0]));
        int option = selectOption(days.size());
        currentDay = days.get(option-1);
    }
    
    /**
     * Asks heat time and filename including laptimes
     * @throws SQLException when database error occurs
     */
    public void addHeat() throws SQLException {
        if (currentDay == null) {
            System.out.println("Ei valittua päivää!");
            return;
        }
        DateFormat df = new SimpleDateFormat("HH:mm");
        while(true) {
            System.out.print("Anna aika muodossa HH:mm (" +
                    df.format(new Date()) + "): ");
            try {
                String time = scanner.nextLine();
                System.out.print("Anna tiedostonnimi: ");
                try {
                    controller.addHeatFromFile(currentDay, scanner.nextLine(),
                            time, new OrionParser());
                } catch (IOException e) {
                    System.out.println("Ongelma tiedoston lukemisessa!");
                }
                break;
            } catch (ParseException e) {
                    System.out.println("Virheellien aika!");
            }
        }
        
    }
    
    /**
     * Asks option from user
     * @param options numbers of options possible
     * @return option selected by user
     */
    public int selectOption(int options) {
        int option;
        while (true) {
            System.out.print("Valinta: ");
            try {
            option = Integer.parseInt(scanner.nextLine());
            if (option > 0 && option <= options)
                break;
            } catch (NumberFormatException e) {
                System.out.println("Virheellinen valinta.");
            }
        }
        return option;
    }
    
    /**
     * Prints a heat
     * @param h heat to be printed
     * @throws SQLException  when database error occurs
     */
    private void printHeat(Heat h) throws SQLException {
        Lap[] laps = controller.getLaps(h).toArray(new Lap[0]);
        printModels(laps);
    }
    
    /**
     * Asks heat to be desplayed and print it
     * @throws SQLException when database error occurs
     */
    public void showHeat() throws SQLException {
        if (currentDay == null) {
            System.out.println("Ei valittua päivää!");
            return;
        }
        Heat[] heats = controller.getHeats(currentDay).toArray(new Heat[0]);
        printModels(heats);
        int option = selectOption(heats.length);
        printHeat(heats[option-1]);
    }
    
    /**
     * Compares two heats in text mode
     */
    public void compareHeats() {
        //TODO
        System.out.println("Ei toteutettu!");
    }
    
    /**
     * Opens main menu
     */
    public void mainMenu() {

        while(true) {
            System.out.println("Valittu päivä: " + currentDay);
            System.out.println("Toiminnot:");
            System.out.println("1. Lisää päivä");
            System.out.println("2. Valitse päivä");
            System.out.println("3. Lisää heatti");
            System.out.println("4. Näytä heatti");
            System.out.println("5. Vertaa heatteja");
            System.out.println("0. Lopeta");
            System.out.print("Valitse toiminto: ");
            int option;
            try {
            option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Virheellinen valinta.");
                continue;
            }
            try {
                switch (option) {
                    case 1: addDay();
                        break;
                    case 2: selectDay();
                        break;
                    case 3: addHeat();
                        break;
                    case 4: showHeat();
                        break;
                    case 5: compareHeats();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Virheellinen valinta.");
                        break;
                }
            } catch (SQLException ex) {
                System.out.println("Tietokantavirhe! Yritä uudelleen.");
            }
        }
    }
}
