/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.ui.text;

import fi.helsinki.cs.thois.lapreader.controller.Controller;
import fi.helsinki.cs.thois.lapreader.model.*;
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
                controller.addDay(scanner.nextLine());
                break;
            } catch (ParseException e) {
                    System.out.println("Virheellien päivä!");
            }
        }
    }
    
    private void printDays(List<TestDay> days) {
        System.out.println("Tietokannassa olevat päivät:");
        for (int i = 0; i < days.size(); i++) {
            System.out.println((1+i)+".: "+days.get(i));
        }
    }
    
    private void selectDay() throws SQLException {
        List<TestDay> days = controller.getDays();
        if (days == null || days.isEmpty()) {
            System.out.println("Ei päiviä tietokannassa. Luo ensin päivä.");
            addDay();
            return;
        }
        printDays(days);
        int option;
        while (true) {
            System.out.print("Valitse päivä: ");
            try {
            option = Integer.parseInt(scanner.nextLine());
            if (option > 0 && option <= days.size())
                break;
            break;
            } catch (NumberFormatException e) {
                System.out.println("Virheellinen valinta.");
            }
        }
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
                System.out.println("Anna tiedostonnimi: ");
                try {
                    controller.addHeat(currentDay, scanner.nextLine(), time);
                } catch (IOException e) {
                    System.out.println("Ongelma tiedoston lukemisessa!");
                }
                break;
            } catch (ParseException e) {
                    System.out.println("Virheellien aika!");
                    e.printStackTrace();
            }
        }
        
    }
    
    public void mainMenu() {

        while(true) {
            System.out.println("Valittu päivä: " + currentDay);
            System.out.println("Toiminnot:");
            System.out.println("1. Lisää päivä");
            System.out.println("2. Valitse päivä");
            System.out.println("3. Lisää heatti");
            System.out.println("4. Lopeta");
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
                    case 4:
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
