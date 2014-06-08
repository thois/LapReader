/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.ui.text;

import fi.helsinki.cs.thois.lapreader.controller.Controller;
import fi.helsinki.cs.thois.lapreader.model.*;
import fi.helsinki.cs.thois.lapreader.model.TestDay;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author nlindval
 */
public class TextUi {
    Scanner scanner = new Scanner(System.in);    
    private TestDay currentDay;
    private Controller controller;
    
    public TextUi(Controller controller) {
    this.controller = controller;
}
    
    private void addDay() throws SQLException {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        while(true) {
            System.out.print("Anna päivämäärä muodossa dd.MM.yyyy (" + df.format(new Date()) + "): ");
            try {
                currentDay = controller.addDay(scanner.nextLine());
                break;
            } catch (ParseException e) {
                    System.out.println("Virheellien päivä!");
            }
        }
    }
    
    private <T> void printModels(T[] l) {
        System.out.println("Tietokannassa olevat:");
        for (int i = 0; i < l.length; i++) {
            System.out.println((1+i)+".: " + l[i]);
        }
    }
    
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
    
    public void addHeat() throws SQLException {
        if (currentDay == null) {
            System.out.println("Ei valittua päivää!");
            return;
        }
        DateFormat df = new SimpleDateFormat("HH.mm");
        while(true) {
            System.out.print("Anna aika muodossa HH.mm (" + df.format(new Date()) + "): ");
            try {
                String time = scanner.nextLine();
                System.out.print("Anna tiedostonnimi: ");
                try {
                    controller.addHeat(currentDay, scanner.nextLine(), time);
                } catch (IOException e) {
                    System.out.println("Ongelma tiedoston lukemisessa!");
                }
                break;
            } catch (ParseException e) {
                    System.out.println("Virheellien aika!");
            }
        }
        
    }
    
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
    
    private void printHeat(Heat h) {
        //TODO
    }
    
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
    
    public void compareHeats() {
        //TODO
        System.out.println("Ei toteutettu!");
    }
    
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
