/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

/**
 *
 * @author User
 */
import java.io.*;

public class Readfile {

    // Reading Admin Login Credentials
    public Admin[] readAdminFile(String filename) {
        Admin[] adminArray = new Admin[100];
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                adminArray[count] = new Admin(data[0], data[1], data[2]);
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error: Could not read " + filename);
        }
        return adminArray;
    }
    // 2. Reading Member Data (Matches your members.csv: username, password, totalSpent, tier)
    public MembershipData[] readMemberFile(String filename) {
        MembershipData[] memberArray = new MembershipData[100];
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); 
            // Inside readMemberFile
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 4) {
                // Capture: username[0], password[1], totalSpent[2], tier[3]
                memberArray[count] = new MembershipData(data[0], data[1], Double.parseDouble(data[2]), data[3]);
                count++;
            }
        }
        } catch (IOException e) {
            System.out.println("Error: Could not read member file.");
        }
        return memberArray;
    }

    // 3. Reading Trainer Data (Matches your TrainerCredentials.csv: trainerID, Name, JoinDate, Sallary, Password)
    // We will add a "0" for hours worked initially as it's not in your CSV yet
    public TrainnerData[] readTrainerFile(String filename) {
        TrainnerData[] trainerArray = new TrainnerData[100];
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    // Using trainerID and Name. Defaulting hours to 0 for calculation.
                    trainerArray[count] = new TrainnerData(data[0].trim(), data[1].trim(), 0);
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Could not read trainer file.");
        }
        return trainerArray;
    }

    // 4. Saving Member Changes back to CSV
    public void saveMemberFile(String filename, MembershipData[] memberArray) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("username,password,totalSpent,tier"); 
            for (MembershipData m : memberArray) {
                if (m != null && !m.isDeleted()) {
                    // SUCCESS: This preserves the real password and spending
                    pw.println(m.getUsername() + "," + m.getPassword() + "," + m.getTotalSpent() + "," + m.getTier());
                }
            }
            System.out.println("System: " + filename + " updated.");
        } catch (IOException e) {
            System.out.println("DEBUG: Failed to save to " + filename);
            System.out.println("Reason: " + e.getMessage());
        }
    }
    
    // 5. Saving Trainer Changes back to CSV
    public void saveTrainerFile(String filename, TrainnerData[] trainerArray) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("trainerID,Name,JoinDate,Sallary,Password");
            for (TrainnerData t : trainerArray) {
                if (t != null && !t.isDeleted()) {
                    // Use placeholders for date and password to keep it simple
                    pw.println(t.getId() + "," + t.getname() + ",1-1-2026," + (t.gethours() * 15) + ",@Abc123");
                }
            }
            System.out.println("Trainer file updated.");
        } catch (IOException e) {
            System.out.println("Error saving trainer file: " + e.getMessage());
        }
    }
}
