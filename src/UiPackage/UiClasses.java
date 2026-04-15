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

    public static void showTrainerRegistrationHeader() {
        System.out.println("\n=== Trainer Registration ===");
    }

    public static String promptTrainerName() {
        System.out.print("Enter Name: ");
        return sc.nextLine();
    }

    public static String promptJoinDate() {
        System.out.print("Enter Join Date (e.g., 1-1-2026): ");
        return sc.nextLine();
    }

    public static String promptPassword() {
        System.out.print("Enter Password: ");
        return sc.nextLine();
    }

    public static void showInvalidPasswordFormat() {
        System.out.println("Invalid password format.");
        System.out.println("Password must be at least 6 characters and include uppercase, lowercase, number, and symbol.");
    }

    public static void showDuplicateTrainerName() {
        System.out.println("Trainer Name already exists. Please enter a unique Name.");
    }

    public static void showAssignedTrainerId(String trainerId) {
        System.out.println("System assigned Trainer ID: " + trainerId);
    }

    public static boolean promptYesNo(String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("y")) {
                return true;
            }
            if (input.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Invalid choice. Please enter y or n.");
        }
    }

    public static void showRegistrationCancelled() {
        System.out.println("Registration cancelled.");
    }

    public static void showRegistrationSuccess(String trainerId) {
        System.out.println("Trainer registered successfully! Your Trainer ID is: " + trainerId);
    }

    // Trainer authentication menu: choose login or register
    public static void trainerAuthPage() {
        while (true) {
            System.out.println("\n=== Trainer Authentication ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    if (trainerLogin()) {
                        staffPage();
                    } else {
                        System.out.println("Access denied.");
                    }
                    break;
                case "2":
                    Trainer.TrainerRegister.registerPage();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

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
