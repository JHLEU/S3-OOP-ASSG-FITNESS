package Admin;

import java.util.Scanner;
import Trainer.ReportEquipment;

public class AdminDashboard {
    private static final Scanner sc = new Scanner(System.in);
    private final Readfile rf = new Readfile();
    private final Restore restorer = new Restore();
    
    // File Paths
    private final String MEMBER_PATH = "src/member/members.csv";
    private final String TRAINER_PATH = "src/Trainer/TrainerCredentials.csv";

    public void displayMenu() {
        while (true) {
            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("1. Manage Members (Search/Update/Delete)");
            System.out.println("2. Restore Member");
            System.out.println("3. Manage Trainers (Search/Bookings/Salary)");
            System.out.println("4. Restore Trainer");
            long brokenCount = ReportEquipment.countBrokenEquipment();
            System.out.printf("5. Manage Equipment Condition (%d equipment broken)\n", brokenCount);
            System.out.println("6. Logout");
            System.out.print("Choice: ");
            
            String choice = sc.nextLine();
            
            switch (choice) {
                case "1":
                    manageMembers();
                    break;
                case "2":
                    restorer.restoreMember(); 
                    break;
                case "3":
                    manageTrainers();
                    break;
                case "4":
                    restorer.restoreTrainer(); 
                    break;
                case "5":
                    Admin.manageEquipmentCondition();
                    break;
                case "6":
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

        for (MembershipData m : members) {
            if (m != null && m.getUsername().equalsIgnoreCase(searchName) && !m.isDeleted()) {
                m.manageMember(); 
                rf.saveMemberFile(MEMBER_PATH, members); 
                return;
            }
        }
        System.out.println("Active member not found.");
    }
    
    private void manageTrainers() {
        TrainnerData[] trainers = rf.readTrainerFile(TRAINER_PATH);
        System.out.print("Enter Trainer ID to search (e.g. TR001): ");
        String searchId = sc.nextLine();
        
        boolean found = false;
        for (TrainnerData t : trainers) {
            // Only search for active trainers
            if (t != null && t.getId().equalsIgnoreCase(searchId) && !t.isDeleted()) {
                t.manageTrainer(); // This triggers the sub-menu with Delete option
                
                // CRITICAL: Save the array to the CSV after the change
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