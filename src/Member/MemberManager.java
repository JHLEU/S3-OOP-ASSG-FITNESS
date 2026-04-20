package member;

import java.io.*;
import java.util.*;

public class MemberManager {
    private static final String MEMBER_FILE = "src/member/members.csv";
    private static final String BOOKING_FILE = "src/Booking/bookings.csv";

    public void initializeFile() {
        try {
            File file = new File(MEMBER_FILE);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                    pw.println("username,password,totalSpent,tier,isDeleted");
                }
            }
        } catch (IOException e) {
            System.out.println("Error initializing file.");
        }
    }

    public ArrayList<User> loadMembers() {
        ArrayList<User> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(MEMBER_FILE))) {
            br.readLine(); 
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] d = line.split(",");
                list.add(new User(d[0].trim(), d[1].trim(), Double.parseDouble(d[2].trim()), d[3].trim(), Boolean.parseBoolean(d[4].trim())));
            }
        } catch (Exception e) {
            System.out.println("Error loading members.");
        }
        return list;
    }

    public void saveMembers(ArrayList<User> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(MEMBER_FILE))) {
            pw.println("username,password,totalSpent,tier,isDeleted");
            for (User u : list) pw.println(u.toString());
        } catch (IOException e) {
            System.out.println("Error saving members.");
        }
    }

    public boolean registerUser(String u, String p) {
        ArrayList<User> list = loadMembers();
        for (User user : list) {
            if (user.getUsername().trim().equalsIgnoreCase(u.trim())) {
                return false; 
            }
        }
        list.add(new User(u.trim(), p, 0.0, "Bronze", false));
        saveMembers(list);
        return true;
    }

    public User loginUser(String u, String p) {
        ArrayList<User> list = loadMembers();
        for (User user : list) {
            if (user.getUsername().trim().equals( u.trim() ) && user.getPassword().equals( p ) && !user.isDeleted()) {
                return user;
            }
        }
        return null;
    }

    public ArrayList<String[]> loadUserBookings(String username) {
        ArrayList<String[]> bookings = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKING_FILE))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length >= 7 && d[1].trim().equalsIgnoreCase(username.trim())) {
                    bookings.add(d);
                }
            }
        } catch (Exception e) {
            System.out.println("No booking records found.");
        }
        return bookings;
    }
}