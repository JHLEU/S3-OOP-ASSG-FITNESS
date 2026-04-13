/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment;

/**
 *
 * @author User
 */

import Booking.Booking;
import java.util.Scanner;
import UiPackage.UiClasses;

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
                    userPage();
                    break;
                case "2":
                    if (UiClasses.trainerLogin()) {
                    staffPage();
                    } else {
                        System.out.println("Access denied.");
                    }
                    break;
                case "3":
                    //mengwei
                    break;    
                case "0":
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
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
                    Booking.userPage("user");
                    break;
                case "2":
                    Booking.viewPage("user");
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

    public static void staffPage() {
        while (true) {
            System.out.println("\n=== Staff Page ===");
            System.out.println("1. Select Booking");
            System.out.println("2. Complete Booking");
            System.out.println("3. Report equipment");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    Booking.staffSelectPage("staff");
                    break;
                case "2":
                    Booking.staffCompleteTraining("staff");
                    break;
                case "3":
                    //jianhow
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    
}
