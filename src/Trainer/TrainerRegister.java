package Trainer;

import UiPackage.UiClasses;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TrainerRegister {
    private static final String CREDENTIALS_FILE = "Trainer/TrainerCredentials.csv";

    private String trainerID;
    private String name;
    private String joinDate;
    private String password;
    private boolean isDeleted;

    public TrainerRegister(String trainerID, String name, String joinDate, String password) {
        this.trainerID = trainerID;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.isDeleted = false;
    }

    // Save this trainer to CSV (leave salary column empty)
    public void saveToCSV(Path filePath) {
        try (FileWriter fw = new FileWriter(filePath.toString(), true)) {
            String line = String.format("%s,%s,%s,,%s,%s\n",
                trainerID, name, joinDate, password, isDeleted);
            fw.write(line);
        } catch (IOException e) {
            System.out.println("Error saving trainer: " + e.getMessage());
        }
    }

    // Registration page for new trainer with system-assigned ID and confirmation
    public static void registerPage() {
        Path filePath = resolveCredentialsPath();
        String trainerID = generateNextTrainerID(filePath);

        UiClasses.showTrainerRegistrationHeader();
        String name = inputUniqueName(filePath);
        if (name == null) {
            UiClasses.showRegistrationCancelled();
            return;
        }

        String joinDate = UiClasses.promptJoinDate();
        String password = inputValidPassword();
        if (password == null) {
            UiClasses.showRegistrationCancelled();
            return;
        }

        UiClasses.showAssignedTrainerId(trainerID);
        if (!confirmRegistration()) {
            UiClasses.showRegistrationCancelled();
            return;
        }

        TrainerRegister newTrainer = new TrainerRegister(trainerID, name, joinDate, password);
        newTrainer.saveToCSV(filePath);
        UiClasses.showRegistrationSuccess(trainerID);
    }

    private static String inputUniqueName(Path filePath) {
        while (true) {
            String name = UiClasses.promptTrainerName();

            if (name == null || name.trim().isEmpty()) {
                System.out.println("Name cannot be empty.");
                continue;
            }

            if (isNameExists(filePath, name)) {
                UiClasses.showDuplicateTrainerName();
                if (!askRetry()) {
                    return null;
                }
                continue;
            }

            return name.trim();
        }
    }

    private static String inputValidPassword() {
        while (true) {
            String password = UiClasses.promptPassword();

            if (Auth.isValidPasswordFormat(password)) {
                return password;
            }

            UiClasses.showInvalidPasswordFormat();
            if (!askRetry()) {
                return null;
            }
        }
    }

    private static boolean confirmRegistration() {
        return UiClasses.promptYesNo("Do you want to confirm registration with this ID? (y/n): ");
    }

    private static boolean askRetry() {
        return UiClasses.promptYesNo("Do you want to retry? (y/n): ");
    }

    private static boolean isNameExists(Path filePath, String name) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toString()))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length > 1 && name.equalsIgnoreCase(parts[1].trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading trainer file: " + e.getMessage());
            return true;
        }

        return false;
    }

    // Generate the next available Trainer ID in format TR###
    private static String generateNextTrainerID(Path filePath) {
        int maxNum = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toString()))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length > 0 && parts[0].trim().startsWith("TR")) {
                    String numStr = parts[0].trim().substring(2);
                    try {
                        int num = Integer.parseInt(numStr);
                        if (num > maxNum) {
                            maxNum = num;
                        }
                    } catch (NumberFormatException ignored) {
                        // Ignore malformed ID rows
                    }
                }
            }
        } catch (IOException ignored) {
            // If file doesn't exist, start from 0
        }

        return String.format("TR%03d", maxNum + 1);
    }

    private static Path resolveCredentialsPath() {
        Path primary = Paths.get(CREDENTIALS_FILE);
        if (Files.exists(primary)) {
            return primary;
        }
        return Paths.get("src", CREDENTIALS_FILE);
    }
}
