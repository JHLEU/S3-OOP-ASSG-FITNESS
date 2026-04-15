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
import java.util.Scanner;
import static member.MemberSystem.showMainMenu;

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
                    showMainMenu();
                    break;
                case "2":
                    if (UiClasses.trainerLogin()) {
                        UiClasses.staffPage();
                    } else {
                        System.out.println("Access denied.");
                    }
                    break;
                case "3":
                    if (Admin.AdminLogin()) {
                        adminPage(); // Launch the Admin-specific menu
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
    
    public static void adminPage() {
        AdminDashboard dashboard = new AdminDashboard();
        dashboard.displayMenu();
    }

    public static void userPage() {
        while (true) {
            System.out.println("\n=== User Page ===");
            System.out.println("1. Booking");
            System.out.println("2. View Booking");
            System.out.println("3. Tracking progress");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();
            
            switch (choice) {
                case "1":
                    BookingPage.userPage("user");
                    break;
                case "2":
                    BookingPage.viewPage("user");
                    break;
                case "3":
                    // Zerry
                    break;
                case "0":
                    return; 
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

}
