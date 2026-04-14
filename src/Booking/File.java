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
    
    public static ArrayList<Booking> getData() {
        /// get data
        ArrayList<Booking> list = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/Booking/bookings.csv"));
            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                // skip header
                if (data[0].equals("booking_id")) {
                    continue;
                }

                Booking b = new Booking(data[0],data[1],data[2],data[3],data[4],data[5],data[6]);

                list.add(b);
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static ArrayList<Booking> userNamedate(String username, String targetDate) {
        /// filter target username, target date, status = BOOKED
        
        ArrayList<Booking> result = new ArrayList<>();
        ArrayList<Booking> bookings = getData();
        
        for (Booking o : bookings){
            if (o.getUsername().equals(username) && o.getDate().equals(targetDate) && o.getStatus().equals("BOOKED")) {
                result.add(o);
            }
        }
        return result;
    }
    
    public static ArrayList<Booking> staffBooked(String staffname){
        /// filter staff name and status = BOOKED
        
        ArrayList<Booking> result = new ArrayList<>();
        ArrayList<Booking> bookings = getData();
        
        for(Booking o : bookings){
            //B0001,user,2026-03-30,10,Yoga, "staff" , "BOOKED" 
            if (o.getStaff().equals(staffname) && o.getStatus().equals("BOOKED")) {
                result.add(o);
            }
        }
        return result;
    }
    
    public static ArrayList<Booking> date(String targetDate) {
        /// filter target date
        
        ArrayList<Booking> result = new ArrayList<>();
        ArrayList<Booking> bookings = getData();
        
        for(Booking o : bookings){
            if (o.getDate().equals(targetDate)) {
                //B0001,cyh, "2026-03-25" ,10,Yoga,John,BOOKED
                result.add(o);
            }
        }
        return result;
    }
    
    public static ArrayList<Booking> withOutStaff(String targetDate){
        /// filter target date and staff = null
        
        ArrayList<Booking> result = new ArrayList<>();
        ArrayList<Booking> bookings = date(targetDate);
        
        for (Booking o : bookings){
            if (o.getDate().equals(targetDate) && o.getStaff().equals("null")) {
                result.add(o);
            }
        }
        return result;
    }
    
    public static ArrayList<TrainingSlot> viewEmptyTrainingByDate(String targetDate, String targetTime) {
        /// filter target date have empty or not
        
        ArrayList<TrainingSlot> result = new ArrayList<>();
        ArrayList<Booking> bookings = date(targetDate);
        int Yoga=0, HIIT=0, Chest=0, Arm=0, Leg=0, Bicep=0, Tricep=0, Abs=0, Back=0;
        
        System.out.println(); // display layout
        
        for (Booking o : bookings) {
            
            //B0001,cyh,2026-03-25, "10" ,Yoga,John,BOOKED
            if (o.getTime().equals(targetTime)) {
                switch (o.getTraining()) {
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
        if (Yoga < max) result.add(new TrainingSlot("Yoga", Yoga, max));
        if (HIIT < max) result.add(new TrainingSlot("HIIT", HIIT, max));
        if (Chest < max) result.add(new TrainingSlot("Chest", Chest, max));
        if (Arm < max) result.add(new TrainingSlot("Arm", Arm, max));
        if (Leg < max) result.add(new TrainingSlot("Leg", Leg, max));
        if (Bicep < max) result.add(new TrainingSlot("Bicep", Bicep, max));
        if (Tricep < max) result.add(new TrainingSlot("Tricep", Tricep, max));
        if (Abs < max) result.add(new TrainingSlot("Abs", Abs, max));
        if (Back < max) result.add(new TrainingSlot("Back", Back, max));

        return result;
    }
    
    public static void updateData(ArrayList<Booking> booking) {

        try (FileWriter fw = new FileWriter("src/Booking/bookings.csv")) {

            // header
            fw.write("booking_id,username,date,time,training,staff,status\n");

            for (Booking o : booking) {
                fw.write(o.toCSV() + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateStaff(String bookingId, String staffname) {
        /// updata staff name to boogkings.csv
        
        ArrayList<Booking> updated = new ArrayList<>();
        ArrayList<Booking> booking = File.getData();


        for (Booking o : booking) {
            if (o.getBookingId().equals(bookingId)) {
                o.setStaff(staffname);
            }
            
           updated.add(o);
        }

        updateData(updated);
    }

    public static void updateStatus(String targetId) {
        /// update status = COMPLETED to boogkings.csv
        
        ArrayList<Booking> updated = new ArrayList<>();
        ArrayList<Booking> booking = File.getData();
        
        boolean found = false;

        for (Booking o : booking) {
            if (o.getBookingId().equals(targetId)) {
                o.setStatus("COMPLETED");
                found = true;
            }

            updated.add(o);
        }

        if (found) {
            updateData(updated);
            System.out.println("Success: Training marked as COMPLETED!");
        } else {
            System.out.println("Error: Booking ID not found!");
        }
    }
    
    public static void appendData(Booking b) {
        /// add data to bookings.csv
        
        try {
            FileWriter fw = new FileWriter("src/Booking/bookings.csv", true);
            fw.write(b.toCSV() + "\n"); // 自动调用 toString()
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
