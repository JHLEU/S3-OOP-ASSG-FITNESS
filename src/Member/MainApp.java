package member;

import Booking.BookingPage;
import java.util.*;

public class MainApp {
    private static Scanner sc = new Scanner(System.in);
    private static MemberManager manager = new MemberManager();
    private static User currentUser = null;

    public static void showMainMenu() {
        manager.initializeFile();
        while (true) {
            System.out.println("\n=== Fitness Club Member System ===");
            System.out.println("1. Register\n2. Login\n0. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                System.out.print("New Username: ");
                String u = sc.nextLine();
                System.out.print("New Password: ");
                String p = sc.nextLine();
                if (manager.registerUser(u, p)) System.out.println("Registration Successful!");
                else System.out.println("Error: Username already exists!");
            } else if (choice.equals("2")) {
                System.out.print("Username: ");
                String u = sc.nextLine();
                System.out.print("Password: ");
                String p = sc.nextLine();
                currentUser = manager.loginUser(u, p);
                if (currentUser != null) showDashboard();
                else System.out.println("Invalid username or password!");
            } else if (choice.equals("0")) break;
        }
    }

    private static void showDashboard() {
        while (currentUser != null) {
            System.out.println("\n--- Member Dashboard ---");
            System.out.println("User: " + currentUser.getUsername() + " | Tier: " + currentUser.getTier());
            System.out.println("1. View My Progress\n2. Subscription (Top-up)\n3. New Booking\n0. Logout");
            System.out.print("Selection: ");
            String c = sc.nextLine();

            switch (c) {
                case "1": viewProgress(); break;
                case "2": topUp(); break;
                case "3": BookingPage.userPage(currentUser.getUsername()); break;
                case "0": currentUser = null; break;
            }
        }
    }

    private static String formatTime(String t) {
        try {
            int start = Integer.parseInt(t.trim());
            return String.format("%02d:00 - %02d:00", start, start + 1);
        } catch (Exception e) { return t; }
    }

    private static void viewProgress() {
        ArrayList<String[]> data = manager.loadUserBookings(currentUser.getUsername());
        System.out.println("\n" + "=".repeat(90));
        System.out.printf("%-6s | %-12s | %-15s | %-15s | %-12s | %-10s\n", 
                          "ID", "Date", "Activity", "Time Range", "Staff", "Status");
        System.out.println("-".repeat(90));

        if (data.isEmpty()) {
            System.out.println("No booking records found.");
        } else {
            for (String[] row : data) {
                System.out.printf("%-6s | %-12s | %-15s | %-15s | %-12s | %-10s\n", 
                                  row[0], row[2], row[4], formatTime(row[3]), row[5], row[6]);
            }
        }
        System.out.println("=".repeat(90));
    }

    private static void topUp() {
        try {
            System.out.print("Enter Amount: RM");
            double amt = Double.parseDouble(sc.nextLine());
            currentUser.setTotalSpent(currentUser.getTotalSpent() + amt);
            currentUser.checkTier();
            
            ArrayList<User> list = manager.loadMembers();
            for (User u : list) {
                if (u.getUsername().equals(currentUser.getUsername())) {
                    u.setTotalSpent(currentUser.getTotalSpent());
                    u.setTier(currentUser.getTier());
                }
            }
            manager.saveMembers(list);
            System.out.println("Top-up success! Current Tier: " + currentUser.getTier());
        } catch (Exception e) { System.out.println("Invalid amount!"); }
    }
}