package Admin;

import java.io.*;

public class Readfile {

    // 1. Reading Admin Login Credentials
    public Admin[] readAdminFile(String filename) {
        Admin[] adminArray = new Admin[100];
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length >= 3) {
                    adminArray[count] = new Admin(d[0].trim(), d[1].trim(), d[2].trim());
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading Admin file: " + e.getMessage());
        }
        return adminArray;
    }

    // 2. Reading Member Data (Supports Soft Delete via 5th column)
    public MembershipData[] readMemberFile(String filename) {
        MembershipData[] memberArray = new MembershipData[100];
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // skip header
            // Inside Readfile.java
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length >= 5) {
                    // Use equalsIgnoreCase to be safer than Boolean.parseBoolean
                    String statusString = d[4].trim();
                    boolean status = statusString.equalsIgnoreCase("true");

                    memberArray[count] = new MembershipData(
                        d[0].trim(), 
                        d[1].trim(), 
                        Double.parseDouble(d[2].trim()), 
                        d[3].trim(), 
                        status
                    );
                    count++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading member file: " + e.getMessage());
        }
        return memberArray;
    }

    // 3. Reading Trainer Data
    public TrainnerData[] readTrainerFile(String filename) {
        TrainnerData[] trainerArray = new TrainnerData[100];
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    // Using trainerID and Name. Defaulting hours to 0 initially.
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
            // Write Header
            pw.println("username,password,totalSpent,tier,isDeleted"); 

            for (MembershipData m : memberArray) {
                if (m != null) {
                    // Save every column including the boolean isDeleted status
                    pw.println(m.getUsername() + "," + 
                               m.getPassword() + "," + 
                               m.getTotalSpent() + "," + 
                               m.getTier() + "," + 
                               m.isDeleted());
                }
            }
            System.out.println("System: " + filename + " updated.");
        } catch (IOException e) {
            System.out.println("Error saving member file: " + e.getMessage());
        }
    }

    // 5. Saving Trainer Changes back to CSV
    public void saveTrainerFile(String filename, TrainnerData[] trainerArray) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("trainerID,Name,JoinDate,Sallary,Password");
            for (TrainnerData t : trainerArray) {
                if (t != null && !t.isDeleted()) {
                    // Placeholder for date and password to match teammate's CSV format
                    pw.println(t.getId() + "," + t.getname() + ",1-1-2026," + (t.gethours() * 15) + ",@Abc123");
                }
            }
            System.out.println("Trainer file updated.");
        } catch (IOException e) {
            System.out.println("Error saving trainer file: " + e.getMessage());
        }
    }
}