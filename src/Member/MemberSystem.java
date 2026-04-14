package member;

import Booking.BookingPage;
import java.io.*;
import java.util.*;

class User {
    private String username;
    private String password; 
    private String tier;
    private double totalSpent;
    private boolean isDeleted;

    User(String u, String p, double s, String l, boolean deleted) {
        this.username = u;
        this.password = p;
        this.totalSpent = s;
        this.tier = l;
        this.isDeleted = deleted;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTier() { return tier; }
    public void setTier(String tier) { this.tier = tier; }

    public double getTotalSpent() { return totalSpent; }
    public void setTotalSpent(double totalSpent) { this.totalSpent = totalSpent; }

    public boolean isIsDeleted() { return isDeleted; }
    public void setIsDeleted(boolean isDeleted) { this.isDeleted = isDeleted; }

    void checkTier() {
        if (totalSpent >= 300) tier = "Gold";
        else if (totalSpent >= 200) tier = "Silver";
        else tier = "Bronze";
    }

    @Override
    public String toString() {
        return username + "," + password + "," + totalSpent + "," + tier + "," + isDeleted;
    }
}

public class MemberSystem {
    private static final String MEMBER_FILE = "src/member/members.csv";
    private static final String BOOKING_FILE = "src/Booking/bookings.csv";
    private static Scanner sc = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        initializeFiles();
        showMainMenu();
    }

    private static void initializeFiles() {
        String[] files = {MEMBER_FILE, BOOKING_FILE};
        for (String path : files) {
            File file = new File(path);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            if (!file.exists()) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                    if (path.equals(MEMBER_FILE)) {
                        pw.println("username,password,totalSpent,tier,isDeleted");
                    } else {
                        pw.println("ID,Username,Date,Time,Activity,Venue,Status");
                    }
                } catch (IOException e) {
                    System.out.println("Error initializing file: " + path);
                }
            }
        }
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
                default: System.out.println("Invalid input!");
            }
        }
    }

    public static void register() {
        ArrayList<User> userList = loadMembers();
        System.out.print("Enter New Username: ");
        String u = sc.nextLine().trim();
        System.out.print("Enter New Password: ");
        String p = sc.nextLine();

        for (User existing : userList) {
            if (existing.getUsername().equalsIgnoreCase(u)) {
                System.out.println("Error: Username already exists!");
                return;
            }
        }

        userList.add(new User(u, p, 0.0, "Bronze", false));
        saveMembers(userList);
        System.out.println("Registration Successful!");
    }

    public static void login() {
        ArrayList<User> userList = loadMembers();
        System.out.print("Username: ");
        String u = sc.nextLine().trim();
        System.out.print("Password: ");
        String p = sc.nextLine();

        for (User user : userList) {
            if (user.getUsername().equals(u) && user.getPassword().equals(p)) {
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
            System.out.println("User: " + currentUser.getUsername() + " | Tier: " + currentUser.getTier());
            System.out.println("1. View My Booking Progress");
            System.out.println("2. Subscription (Top-up)");
            System.out.println("3. Make New Booking");
            System.out.println("4. Logout");
            System.out.print("Selection: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": viewMyProgress(); break;
                case "2": performTopUp(); break;
                case "3": BookingPage.userPage(currentUser.getUsername()); break;
                case "4": currentUser = null; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void performTopUp() {
        try {
            System.out.print("Enter Subscription Amount: RM");
            double amt = Double.parseDouble(sc.nextLine());
            currentUser.setTotalSpent(currentUser.getTotalSpent() + amt);
            currentUser.checkTier();

            ArrayList<User> list = loadMembers();
            for (User u : list) {
                if (u.getUsername().equals(currentUser.getUsername())) {
                    u.setTotalSpent(currentUser.getTotalSpent());
                    u.setTier(currentUser.getTier());
                }
            }
            saveMembers(list);
            System.out.println("Top-up Success! Your current tier is now: " + currentUser.getTier());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount entered.");
        }
    }

    public static void viewMyProgress() {
        System.out.println("\n--- My Booking Records ---");
        System.out.printf("%-10s | %-12s | %-15s | %-10s\n", "ID", "Date", "Activity", "Status");
        System.out.println("----------------------------------------------------------");

        try (BufferedReader br = new BufferedReader(new FileReader(BOOKING_FILE))) {
            br.readLine(); // 跳过标题
            String line;
            boolean found = false;  
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; 
                String[] d = line.split(",");
                if (d.length >= 7 && d[1].trim().equalsIgnoreCase(currentUser.getUsername().trim())) {
                    System.out.printf("%-10s | %-12s | %-15s | %-10s\n", 
                        d[0].trim(), d[2].trim(), d[4].trim(), d[6].trim());
                    found = true;
                }
            }
            if (!found) System.out.println("No records found for user: " + currentUser.getUsername());
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
                if (line.trim().isEmpty()) continue;
                String[] d = line.split(",");
                if (d.length >= 4) {
                    String u = d[0].trim();
                    String p = d[1].trim();
                    double s = Double.parseDouble(d[2].trim());
                    String t = d[3].trim();
                    boolean del = (d.length >= 5) ? Boolean.parseBoolean(d[4].trim()) : false;
                    list.add(new User(u, p, s, t, del));
                }
            }
        } catch (Exception e) { 
            System.out.println("Error loading members: " + e.getMessage());
        }
        return list;
    }

    private static void saveMembers(ArrayList<User> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(MEMBER_FILE))) {
            pw.println("username,password,totalSpent,tier,isDeleted");
            for (User u : list) {
                pw.println(u.toString());
            }
        } catch (Exception e) { 
            System.out.println("Error saving members: " + e.getMessage());
        }
    }
}