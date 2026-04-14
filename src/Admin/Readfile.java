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

public class Readfile{

    public Admin[] readAdminFile(String filename) {

        Admin[] adminArray = new Admin[100];
        int count = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/Admin/Admin.csv"));
            String line;

            // skip header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                adminArray[count] = new Admin(
                        data[0],
                        data[1],
                        data[2]
                );

                count++;
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error: Could not read the Admin file. Please check if Admin.csv exists.");
        }

        return adminArray; // array
    }
    
    // 2. Reading Member Data (Matches your members.csv: username, password, totalSpent, tier)
    public MembershipData[] readMemberFile(String filename) {
        MembershipData[] memberArray = new MembershipData[100];
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    // data[0] = username, data[3] = tier
                    memberArray[count] = new MembershipData(data[0].trim(), data[3].trim());
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Could not read member file.");
        }
        return memberArray;
    }
    
    // Reading Trainer Data for Salary Calculation
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
    
    // Add this method to your Readfile.java
    public void saveMemberFile(String filename, MembershipData[] memberArray) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("src/Member/members.csv"))) {
            // 1. Write the header first
            pw.println("username,tier,price");

            // 2. Loop through the array
            for (MembershipData m : memberArray) {
                // 3. Only save if the slot is not empty AND not marked for deletion
                if (m != null && !m.isDeleted()) {
                    pw.println(m.getUsername() + "," + m.getTier() + "," + m.getPrice());
                }
            }
            System.out.println("System: members.csv updated successfully.");

        } catch (IOException e) {
            System.out.println("Error: Could not save to " + filename);
        }
    }
    
}
