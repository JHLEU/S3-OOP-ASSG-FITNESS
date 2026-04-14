package Admin;

import java.util.Scanner;

public class Restore {
    private Scanner sc = new Scanner(System.in);
    private Readfile rf = new Readfile();
    
    // Define paths here to keep code clean
    private final String MEMBER_PATH = "src/member/members.csv";
    private final String TRAINER_PATH = "src/Trainer/TrainerCredentials.csv";

    public void restoreMember() {
        MembershipData[] members = rf.readMemberFile(MEMBER_PATH);
        
        System.out.println("\n--- Deleted Members ---");
        boolean foundAny = false;
        
        for (int i = 0; i < members.length; i++) {
            if (members[i] != null && members[i].isDeleted()) {
                System.out.println("- " + members[i].getUsername());
                foundAny = true;
            }
        }

        if (!foundAny) {
            System.out.println("No deleted accounts found.");
            return;
        }

        System.out.print("Enter username to restore: ");
        String target = sc.nextLine();

        for (int i = 0; i < members.length; i++) {
            if (members[i] != null && members[i].getUsername().equalsIgnoreCase(target)) {
                members[i].setIsDeleted(false); 
                rf.saveMemberFile(MEMBER_PATH, members); 
                System.out.println("Member restored!");
                return;
            }
        }
        System.out.println("User not found in deleted list.");
    }

    // --- NEW METHOD FOR TRAINERS ---
    public void restoreTrainer() {
        TrainnerData[] trainers = rf.readTrainerFile(TRAINER_PATH);
        
        System.out.println("\n--- Deleted Trainers ---");
        boolean foundAny = false;
        
        for (int i = 0; i < trainers.length; i++) {
            if (trainers[i] != null && trainers[i].isDeleted()) {
                System.out.println("- ID: " + trainers[i].getId() + " | Name: " + trainers[i].getname());
                foundAny = true;
            }
        }

        if (!foundAny) {
            System.out.println("No deleted trainers found.");
            return;
        }

        System.out.print("Enter Trainer ID to restore: ");
        String targetId = sc.nextLine();

        for (int i = 0; i < trainers.length; i++) {
            if (trainers[i] != null && trainers[i].getId().equalsIgnoreCase(targetId)) {
                trainers[i].setIsDeleted(false); // Make sure setIsDeleted exists in TrainnerData
                rf.saveTrainerFile(TRAINER_PATH, trainers); // Saves the change
                System.out.println("Trainer restored!");
                return;
            }
        }
        System.out.println("Trainer ID not found in deleted list.");
    }
}