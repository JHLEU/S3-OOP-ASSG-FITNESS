package Admin;

import java.util.Scanner;
import Trainer.ReportEquipment;

public class AdminDashboard {
    // 1. All variables MUST be static to be used by the static displayMenu()
    private static final Scanner sc = new Scanner(System.in);
    private static final Readfile rf = new Readfile();
    private static final Restore restorer = new Restore();
    
    // 2. These paths must also be static
    private static final String MEMBER_PATH = "src/member/members.csv";
    private static final String TRAINER_PATH = "src/Trainer/TrainerCredentials.csv";

    // 3. Changed to 'public static void' so Main.java can call it directly
    public static void displayMenu() {
        while (true) {
            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("1. Manage Members (Search/Update/Delete)");
            System.out.println("2. Restore Member");
            System.out.println("3. Manage Trainers (Search/Bookings/Salary)");
            System.out.println("4. Restore Trainer");
            
            long brokenCount = ReportEquipment.countBrokenEquipment();
            System.out.printf("5. Manage Equipment Condition (%d equipment broken)\n", brokenCount);
            System.out.println("0. Logout");
            System.out.print("Choice: ");
            
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1":
                    manageMembers(); // Must be a static method
                    break;
                case "2":
                    restorer.restoreMember(); 
                    break;
                case "3":
                    manageTrainers(); // Must be a static method
                    break;
                case "4":
                    restorer.restoreTrainer(); 
                    break;
                case "5":
                    Admin.manageEquipmentCondition();
                    break;
                case "0":
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid selection.");
                    break;
            }
        }
    }

    // 4. These sub-methods MUST also be 'private static void'
    private static void manageMembers() {
        MembershipData[] members = rf.readMemberFile(MEMBER_PATH);
        
        // 1. Show all active usernames first
        System.out.println("\n--- Active Members List ---");
        boolean anyActive = false;
        for (MembershipData m : members) {
            if (m != null && !m.isDeleted()) {
                System.out.print("\n[" + m.getUsername() + "]");
                anyActive = true;
            }
        }
        System.out.println(); // New line for formatting

        if (!anyActive) {
            System.out.println("No active members found in the system.");
            return;
        }

        // 2. Ask for the specific username to manage
        System.out.print("\nEnter Member Username to manage: ");
        String searchName = sc.nextLine();

        for (MembershipData m : members) {
            if (m != null && m.getUsername().equalsIgnoreCase(searchName) && !m.isDeleted()) {
                m.manageMember(); 
                rf.saveMemberFile(MEMBER_PATH, members); 
                return;
            }
        }
        System.out.println("Error: That username is not in the active list.");
    }
    
    private static void manageTrainers() {
        TrainnerData[] trainers = rf.readTrainerFile(TRAINER_PATH);
        System.out.print("Enter Trainer ID to search (e.g. TR001): ");
        String searchId = sc.nextLine();
        
        boolean found = false;
        for (TrainnerData t : trainers) {
            if (t != null && t.getId().equalsIgnoreCase(searchId) && !t.isDeleted()) {
                t.manageTrainer(); 
                rf.saveTrainerFile(TRAINER_PATH, trainers); 
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("Active trainer not found.");
        }
    }
}