package member;

import Booking.BookingPage;
import java.io.*;
import java.util.*;

class User {
    String username, password, tier;
    double totalSpent;

    User(String u, String p, double s, String l) {
        this.username = u;
        this.password = p;
        this.totalSpent = s;
        this.tier = l;
    }

    void checkTier() {
        if (totalSpent >= 300) tier = "Gold";
        else if (totalSpent >= 200) tier = "Silver";
        else tier = "Bronze";
    }

    @Override
    public String toString() {
        return username + "," + password + "," + totalSpent + "," + tier;
    }
}

public class MemberSystem {
    private static final String MEMBER_FILE = "src/member/members.csv";
    private static final String BOOKING_FILE = "src/assignment/bookings.csv";
    private static Scanner sc = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        showMainMenu();
    }

    public static void showMainMenu() {
        OUTER:
        while (true) {
            System.out.println("\n=== Fitness Club System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select Option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": register(); break;
                case "2": login(); break;
                case "3": break OUTER;
                default: System.out.println("Invalid input! Please try again.");
            }
        }
    }

    public static void register() {
        ArrayList<User> userList = loadMembers();
        System.out.print("Enter New Username: ");
        String u = sc.nextLine();
        System.out.print("Enter New Password: ");
        String p = sc.nextLine();

        for (User existing : userList) {
            if (existing.username.equalsIgnoreCase(u)) {
                System.out.println("Error: Username already exists!");
                return;
            }
        }

        userList.add(new User(u, p, 0.0, "Bronze"));
        saveMembers(userList);
        System.out.println("Registration Successful!");
    }

    public static void login() {
        ArrayList<User> userList = loadMembers();
        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();

        for (User user : userList) {
            if (user.username.equals(u) && user.password.equals(p)) {
                currentUser = user;
                System.out.println("Login Success! Welcome, " + u);
                showMemberMenu();
                return;
            }
        }
        System.out.println("Login Failed! Incorrect username or password.");
    }

    public static void showMemberMenu() {
        while (currentUser != null) {
            System.out.println("\n--- Member Dashboard ---");
            System.out.println("User: " + currentUser.username + " | Tier: " + currentUser.tier);
            System.out.println("1. View My Booking Progress");
            System.out.println("2. Subscription");
            System.out.println("3. Make New Booking");
            System.out.println("4. Logout");
            System.out.print("Selection: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": viewMyProgress(); break;
                case "2": performTopUp(); break;
                case "3": BookingPage.viewPage(currentUser.username); break;
                case "4": currentUser = null; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void performTopUp() {
        try {
            System.out.print("Enter Subscription Amount: RM");
            double amt = Double.parseDouble(sc.nextLine());
            currentUser.totalSpent += amt;
            currentUser.checkTier();

            ArrayList<User> list = loadMembers();
            for (User u : list) {
                if (u.username.equals(currentUser.username)) {
                    u.totalSpent = currentUser.totalSpent;
                    u.tier = currentUser.tier;
                }
            }
            saveMembers(list);
            System.out.println("Top-up Success! Your current tier is now: " + currentUser.tier);
        } catch (Exception e) {
            System.out.println("Invalid amount entered.");
        }
    }

    public static void viewMyProgress() {
        System.out.println("\n--- My Booking Records ---");
        System.out.printf("%-10s | %-12s | %-15s | %-10s\n", "ID", "Date", "Activity", "Status");
        System.out.println("----------------------------------------------------------");

        try (BufferedReader br = new BufferedReader(new FileReader(BOOKING_FILE))) {
            String line;
            br.readLine();
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length >= 7 && d[1].equals(currentUser.username)) {
                    System.out.printf("%-10s | %-12s | %-15s | %-10s\n", d[0], d[2], d[4], d[6]);
                    found = true;
                }
            }
            if (!found) System.out.println("No records found in the system.");
        } catch (Exception e) {
            System.out.println("Error: Could not access booking data.");
        }
    }

    private static ArrayList<User> loadMembers() {
        ArrayList<User> list = new ArrayList<>();
        File file = new File(MEMBER_FILE);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length == 4) {
                    list.add(new User(d[0], d[1], Double.parseDouble(d[2]), d[3]));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    private static void saveMembers(ArrayList<User> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(MEMBER_FILE))) {
            pw.println("username,password,totalSpent,tier");
            for (User u : list) pw.println(u.toString());
        } catch (Exception e) { e.printStackTrace(); }
    }
}