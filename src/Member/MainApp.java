package member;

import Booking.BookingPage;
import Booking.Method;
import java.util.*;

public class MainApp {
    private static Scanner sc = new Scanner(System.in);
    private static MemberManager manager = new MemberManager();
    private static User currentUser = null;

    public static void showMainMenu() {
        manager.initializeFile();
        while (true) {
            System.out.println("\n=== Member System ===");
            System.out.println("1. Login\n2. Register\n0. Back");
            System.out.print("Choice: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                System.out.print("Username: ");
                String u = sc.nextLine();
                System.out.print("Password: ");
                String p = sc.nextLine();
                currentUser = manager.loginUser(u, p);
                if (currentUser != null) showDashboard();
                else System.out.println("Login Failed or got ban!");
            } else if (choice.equals("2")) {
                System.out.print("Username: ");
                String u = sc.nextLine();
                System.out.print("Password: ");
                String p = sc.nextLine();
                if (manager.registerUser(u, p)) System.out.println("Success!");
                else System.out.println("Error: Username taken!");
            } else if (choice.equals("0")) break;
        }
    }

    private static void showDashboard() {
        while (currentUser != null) {
            System.out.println("\n--- Member Dashboard ---");
            System.out.println("User: " + currentUser.getUsername() + " | Tier: " + currentUser.getTier());
            System.out.println("1. Booking\n2. View Progress\n3. Top-up \n0. Logout\n");
            System.out.print("Selection: ");
            String c = sc.nextLine();

            switch (c) {
                case "1": BookingPage.userPage(currentUser.getUsername()); break;
                case "2": viewProgress(); break;
                case "3": topUp(); break;
                case "0": currentUser = null; break;
            }
        }
    }

    private static void viewProgress() {
        ArrayList<String[]> data = manager.loadUserBookings(currentUser.getUsername());
        System.out.printf("\n%-6s | %-10s | %-10s | %-12s | %-10s | %-10s\n", "ID", "Date", "Activity", "Time", "Trainner", "Status");
        System.out.println("------------------------------------------------------------------------");
        for (String[] row : data) {
            System.out.printf("%-6s | %-10s | %-10s | %-12s | %-10s | %-10s\n",
                    row[0], row[2], row[4], Method.formatTime(row[3]), row[5], row[6]);
        }
        
        BookingPage.deletePage(currentUser.getUsername());
    }

    private static void topUp() {
        System.out.print("Amount: RM");
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
        System.out.println("New Tier: " + currentUser.getTier());
    }
}