/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package UiPackage;

import Booking.BookingPage;
import Trainer.ReportEquipment;
import java.util.Scanner;
import java.util.List;

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
                    String loggedInTrainerName = trainerLogin();
                    if (loggedInTrainerName != null) {
                        staffPage(loggedInTrainerName);
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

    public static String trainerLogin() {
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

            String loggedInTrainerName = Trainer.Auth.verifyLogin(trainerId, password);
            if (loggedInTrainerName != null) {
                System.out.println("Login successful. Welcome, " + loggedInTrainerName + "!");
                return loggedInTrainerName; // Return the logged-in trainer's name
            }

            System.out.println("Invalid Trainer ID or password.");
            attempts--;
            System.out.println("Attempts left: " + attempts);
        }

        return null;
    }

    public static void staffPage(String loggedInTrainerName) {
        while (true) {
            long brokenCount = ReportEquipment.countBrokenEquipment();

            System.out.println("\n=== Staff Page ===");
            System.out.println("Welcome, " + loggedInTrainerName + "!");
            System.out.println("1. Select Booking");
            System.out.println("2. Complete Booking");
            System.out.printf("3. Report Equipment (%d equipment broken)\n", brokenCount);
            System.out.println("0. Back");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    BookingPage.staffSelectPage(loggedInTrainerName);
                    break;
                case "2":
                    BookingPage.staffCompleteTraining(loggedInTrainerName);
                    break;
                case "3":
                    equipmentReportPage();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    public static void equipmentReportPage() {
        while (true) {
            List<ReportEquipment> equipmentList = ReportEquipment.loadAll();

            System.out.println("\n=== Equipment Status ===");
            for (int i = 0; i < equipmentList.size(); i++) {
                ReportEquipment equipment = equipmentList.get(i);
                System.out.println((i + 1) + ". " + equipment.getEquipmentId() + " - "
                    + equipment.getEquipmentName() + " - " + equipment.getCondition());
            }

            System.out.println("0. Back");
            System.out.print("Select equipment number to mark as broken: ");
            String choice = sc.nextLine();

            if (choice.equals("0")) {
                return;
            }

            int selectedIndex;
            try {
                selectedIndex = Integer.parseInt(choice) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            if (selectedIndex < 0 || selectedIndex >= equipmentList.size()) {
                System.out.println("Invalid equipment selection.");
                continue;
            }

            ReportEquipment selectedEquipment = equipmentList.get(selectedIndex);
            System.out.println("Selected: " + selectedEquipment.getEquipmentName() +
                " (Current status: " + selectedEquipment.getCondition() + ")");

            if (selectedEquipment.getCondition().equalsIgnoreCase("Broken")) {
                System.out.println("This equipment is already marked as Broken.");
                continue;
            }

            if (!promptYesNo("Mark this equipment as Broken? (y/n): ")) {
                continue;
            }

            boolean saved = ReportEquipment.markEquipmentBrokenByIndex(selectedIndex);
            if (saved) {
                System.out.println("Equipment status updated to Broken.");
            } else {
                System.out.println("Failed to update equipment status.");
            }
        }
    }
}
