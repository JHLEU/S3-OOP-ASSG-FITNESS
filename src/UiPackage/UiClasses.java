/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package UiPackage;

import Booking.BookingPage;
import java.util.Scanner;

/**
 *
 * @author jianh
 */
public class UiClasses {
    private static final Scanner sc = new Scanner(System.in);

    public static boolean trainerLogin() {
        int attempts = 3;

        while (attempts > 0) {
            System.out.println("\n=== Trainer Login ===");
            System.out.print("Enter Trainer ID (example TR001): ");
            String trainerId = sc.nextLine();

            if (!Trainer.Auth.isValidTrainerIdFormat(trainerId)) {
                System.out.println("Invalid Trainer ID format. Use TR followed by 3 digits.");
                attempts--;
                System.out.println("Attempts left: " + attempts);
                continue;
            }

            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            if (Trainer.Auth.verifyLogin(trainerId, password)) {
                System.out.println("Login successful.");
                return true;
            }

            System.out.println("Invalid Trainer ID or password.");
            attempts--;
            System.out.println("Attempts left: " + attempts);
        }

        return false;
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
                    BookingPage.staffSelectPage("staff");
                    break;
                case "2":
                    BookingPage.staffCompleteTraining("staff");
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
