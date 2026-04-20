/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment;

/**
 *
 * @author User
 */

import Booking.BookingPage;
import UiPackage.UiClasses;
import Admin.Admin;         
import Admin.AdminDashboard;
import member.MainApp;
import java.util.Scanner;


public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n=== Main Page ===");
            System.out.println("1. User");// zerry
            System.out.println("2. Staff");// jianhow
            System.out.println("3. Admin");// mengwei
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    MainApp.showMainMenu();
                    break;
                case "2":
                    UiClasses.trainerAuthPage();
                    break;
                case "3":
                    if (Admin.AdminLogin()) {
                        AdminDashboard.displayMenu(); // Launch the Admin-specific menu
                    } else {
                        System.out.println("Admin Access denied.");
                    }
                    break;    
                case "0":
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
