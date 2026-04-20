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
        br.readLine(); // Skip header
        while ((line = br.readLine()) != null) {
            // 1. Skip completely empty lines
            if (line.trim().isEmpty()) continue;

            String[] d = line.split(",");
            if (d.length >= 5) {
                String id = d[0].trim();
                String name = d[1].trim();
                String date = d[2].trim();
                
                // 2. SAFE PARSING: Handle empty salary (like in TR006)
                String salaryStr = d[3].trim();
                double sal = 0.0; 
                if (!salaryStr.isEmpty()) {
                    try {
                        sal = Double.parseDouble(salaryStr);
                    } catch (NumberFormatException e) {
                        sal = 0.0; // Default to 0 if data is corrupted
                    }
                }

                String pass = d[4].trim();
                
                // 3. SAFE STATUS: Handle missing isDeleted column
                boolean status = (d.length >= 6) ? d[5].trim().equalsIgnoreCase("true") : false;

                trainerArray[count] = new TrainnerData(id, name, date, sal, pass, status);
                count++;
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading trainer file: " + e.getMessage());
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
    try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
        pw.println("trainerID,Name,JoinDate,Sallary,Password,isDeleted"); 

        for (TrainnerData t : trainerArray) {
            if (t != null) {
                // Use getters to write the ORIGINAL data back to the file
                pw.println(t.getId() + "," + 
                           t.getname() + "," + 
                           t.getjoinDate() + "," + 
                           t.getsalary() + "," + 
                           t.getpassword() + "," + 
                           t.isDeleted()); 
            }
        }
        System.out.println("System: Trainer file updated (Data preserved).");
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
}