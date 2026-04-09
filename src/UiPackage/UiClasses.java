/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package UiPackage;

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
}
