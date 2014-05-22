/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.helsinki.cs.thois.lapreader.ui.text;

import com.avaje.ebean.EbeanServer;
import fi.helsinki.cs.thois.lapreader.data.*;
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
    EbeanServer server;
    
    public TextUi(EbeanServer server) {
    this.server = server;
}
    
    private void addDay() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        TestDay createdTestDay;
        while(true) {
            System.out.print("Anna päivämäärä muodossa dd.MM.yyyy: ");
            String dateString = scanner.nextLine();
            if (dateString.isEmpty()) {
                createdTestDay = new TestDay();
                break;
            }
            try {
                createdTestDay = new TestDay(df.parse(scanner.nextLine()));
                break;
            } catch (ParseException e) {
                    System.out.println("Virheellien päivä!");
            }
        }
        server.save(createdTestDay);
    }
    
    private void printDays(List<TestDay> days) {
        System.out.println("Tietokannassa olevat päivät:");
        for (int i = 0; i < days.size(); i++) {
            System.out.println((1+i)+".: "+days.get(i));
        }
    }
    
    private void selectDay() {
        List<TestDay> days = (List<TestDay>) server.find(TestDay.class);
        printDays(days);
//        int option;
//        while (true) {
//            System.out.print("Valitse päivä: ");
//            
//        }
    }
    
    public void mainMenu() {

        while(true) {
            System.out.println("Toiminnot:");
            System.out.println("1. Lisää päivä");
            System.out.println("2. Valitse päivä");
            System.out.println("3. Lopeta");
            System.out.print("Valitse toiminto: ");
            int option;
            try {
            option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Virheellinen valinta.");
                continue;
            }
            switch (option) {
                case 1: addDay();
                    break;
                case 2: selectDay();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Virheellinen valinta.");
                    break;
            }
        }
    }
}
