package Admin;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import java.util.Scanner;

public class AdminDashboard {
    private static final Scanner sc = new Scanner(System.in);
    private final Readfile rf = new Readfile();
    
    // File Paths
    private final String MEMBER_PATH = "src/member/members.csv";
    private final String TRAINER_PATH = "src/Trainer/TrainerCredentials.csv";

    public void displayMenu() {
        while (true) {
            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("1. Manage Members (Search/Update/Delete)");
            System.out.println("2. Manage Trainers (Search/Bookings/Salary)");
            System.out.println("3. Logout");
            System.out.print("Choice: ");
            
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1":
                    manageMembers();
                    break; 
                case "2":
                    manageTrainers();
                    break;
                case "3":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid selection.");
                    break;
            }
        }
    }

    private void manageMembers() {
        MembershipData[] members = rf.readMemberFile(MEMBER_PATH);
        System.out.print("Enter Member Username to search: ");
        String searchName = sc.nextLine();
        
        boolean found = false;
        for (MembershipData m : members) {
            if (m != null && m.getUsername().equalsIgnoreCase(searchName)) {
                m.manageMember(); // Opens the update/delete menu
                rf.saveMemberFile(MEMBER_PATH, members); // Save changes immediately
                found = true;
                break;
            }
        }
        if (!found) System.out.println("Member not found.");
    }

    private void manageTrainers() {
        TrainnerData[] trainers = rf.readTrainerFile(TRAINER_PATH);
        System.out.print("Enter Trainer ID to search (e.g. TR001): ");
        String searchId = sc.nextLine();
        
        boolean found = false;
        for (TrainnerData t : trainers) {
            if (t != null && t.getId().equalsIgnoreCase(searchId)) {
                t.manageTrainer(); // Opens the hours/bookings menu
                // You would add rf.saveTrainerFile here if you implemented it
                found = true;
                break;
            }
        }
        if (!found) System.out.println("Trainer not found.");
    }
}
