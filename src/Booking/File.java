/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 *
 * @author Yi Hang
 */
public class File {
    /// read and writer bookings.csv function
    
    public static ArrayList<String> getData(){
        /// get data from bookings.csv
        
         ArrayList<String> result = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/Booking/bookings.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
            br.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static ArrayList<String> userNamedate(String username, String targetDate) {
        /// filter target username, target date, status = BOOKED
        
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> bookings = getData();
        
        for (String line : bookings){
            String[] data = line.split(",");
            //B0001, "user" , "2026-03-30" ,10,Yoga,null, "BOOKED" 
            if (data[1].equals(username) && data[2].equals(targetDate) && data[6].equals("BOOKED")) {
                result.add(line);
            }
        }
        return result;
    }
    
    public static ArrayList<String> staffBooked(String staffname){
        /// filter staff name and status = BOOKED
        
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> bookings = getData();
        
        for(String line : bookings){
            String[] data = line.split(",");
            //B0001,user,2026-03-30,10,Yoga, "staff" , "BOOKED" 
            if (data[5].equals(staffname) && data[6].equals("BOOKED")) {
                result.add(line);
            }
        }
        return result;
    }
    
    public static ArrayList<String> date(String targetDate) {
        /// filter target date
        
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> bookings = getData();
        
        for(String line : bookings){
            String[] data = line.split(",");
            
            // skip header (if need)
            if (data[0].equals("booking_id")) continue;

            if (data[2].equals(targetDate)) {
                //B0001,cyh, "2026-03-25" ,10,Yoga,John,BOOKED
                result.add(line);
            }
        }
        return result;
    }
    
    public static ArrayList<String> withOutStaff(String targetDate){
        /// filter target date and staff = null
        
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> bookings = date(targetDate);
        
        for (String line : bookings){
            String[] data = line.split(",");
            //B0006,user, "2026-03-30" ,10,Yoga, "null" ,BOOKED
            if (data[2].equals(targetDate) && data[5].equals("null")) {
                result.add(line);
            }
        }
        return result;
    }
    
    public static ArrayList<String> viewEmptyTrainingByDate(String targetDate, int targetTime) {
        /// filter target date have empty or not
        
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> bookings = date(targetDate);
        int Yoga=0, HIIT=0, Chest=0, Arm=0, Leg=0, Bicep=0, Tricep=0, Abs=0, Back=0;
        
        System.out.println(); // display layout
        
        for (String line : bookings) {
            String[] data = line.split(",");
            
            //B0001,cyh,2026-03-25, "10" ,Yoga,John,BOOKED
            if (Integer.parseInt(data[3]) == targetTime) {
                switch (data[4]) {
                    case "Yoga": Yoga++; break;
                    case "HIIT": HIIT++; break;
                    case "Chest": Chest++; break;
                    case "Arm": Arm++; break;
                    case "Leg": Leg++; break;
                    case "Bicep": Bicep++; break;
                    case "Tricep": Tricep++; break;
                    case "Abs": Abs++; break;
                    case "Back": Back++; break;
                }
            }
        }

        int max = 10;

        // only add array if have empty
        if (Yoga < max) result.add("1.Yoga (" + Yoga + "/" + max + ")");//yoga(2/10)
        if (HIIT < max) result.add("2.HIIT (" + HIIT + "/" + max + ")");
        if (Chest < max) result.add("3.Chest (" + Chest + "/" + max + ")");
        if (Arm < max) result.add("4.Arm (" + Arm + "/" + max + ")");
        if (Leg < max) result.add("5.Leg (" + Leg + "/" + max + ")");
        if (Bicep < max) result.add("6.Bicep (" + Bicep + "/" + max + ")");
        if (Tricep < max) result.add("7.Tricep (" + Tricep + "/" + max + ")");
        if (Abs < max) result.add("8.Abs (" + Abs + "/" + max + ")");
        if (Back < max) result.add("9.Back (" + Back + "/" + max + ")");

        return result;
    }
    
    public static void updateData(ArrayList<String> data) {
        /// update data to bookings.csv
        
        try {
            FileWriter fw = new FileWriter("src/Booking/bookings.csv");

            for (String line : data) {
                fw.write(line + "\n");
            }

            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
}
    
    public static void updateStaff(String bookingId, String staffname) {
        /// updata staff name to boogkings.csv
        
        ArrayList<String> updated = new ArrayList<>();
        ArrayList<String> booking = File.getData();


        for (String line : booking) {
            String[] data = line.split(",");

            if (data[0].equals(bookingId)) {
                data[5] = staffname;
                line = String.join(",", data);
            }

            updated.add(line);
        }

        updateData(updated);
    }

    public static void updateStatus(String targetId) {
        /// update status = COMPLETED to boogkings.csv
        
        ArrayList<String> updated = new ArrayList<>();
        ArrayList<String> booking = File.getData();
        
        boolean found = false;

        for (String line : booking) {
            String[] data = line.split(",");

            if (data[0].equals(targetId)) {
                data[6] = "COMPLETED";
                line = String.join(",", data);
                found = true;
            }

            updated.add(line);
        }

        if (found) {
            File.updateData(updated);
            System.out.println("Success: Training marked as COMPLETED!");
        } else {
            System.out.println("Error: Booking ID not found!");
        }
    }
    
    public static void appendData(String line) {
        /// append data to boogkings.csv
        
        try {
            FileWriter fw = new FileWriter("src/Booking/bookings.csv", true);
            fw.write(line + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void add(String username, String targetDate, int time, String training) {
            /// formating data to appendData method
            
            // booking id 
            String booking_id = Method.generateBookingId();
                    
            // staff 
            String staff = null;
            
            // status 
            String status = "BOOKED";
            
            //------------------- process ------------------------
            File.appendData(
                booking_id + "," + 
                username  + "," + 
                targetDate + "," + 
                time + "," + 
                training + "," + 
                staff + "," + 
                status
            );
    }

}
