/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author Yi Hang
 */
public class Filter {
    public static ArrayList<String> UserNamedate(String username, String targetDate) {
        ///filter target username, target date, status = BOOKED
        ArrayList<String> result = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/Booking/bookings.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // only show target username, target date, status = BOOKED
                if (data[1].equals(username) && 
                    data[2].equals(targetDate) &&
                    data[6].equals("BOOKED")) {

                    result.add(line);
                }
            }
            br.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static ArrayList<String> viewEmptyTrainingByDate(String targetDate, int time) {
        ///filter target date have empty or not
        ArrayList<String> result = new ArrayList<>();
        
        System.out.println(); // display layout
        
        ArrayList<String> bookings = DateTime(targetDate, time);
        
        int Yoga=0, HIIT=0, Chest=0, Arm=0, Leg=0, Bicep=0, Tricep=0, Abs=0, Back=0;

        for (String line : bookings) {
            //B0001,cyh,2026-03-25,10,Yoga,John,BOOKED
            String[] data = line.split(",");

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
    
    

    public static ArrayList<String> WithOutStaff(String targetDate){
        // find the bookings that are no staff select
        ArrayList<String> result = new ArrayList<>();
        
        ArrayList<String> bookings = Date(targetDate);
        
        for (String line : bookings){
            String[] data = line.split(",");
            //B0006,user,2026-03-30,10,Yoga,null,BOOKED
            if (data[2].equals(targetDate) && data[5].equals("null")) {
                result.add(line);
            }
        }
        return result;
    }
    
    public static ArrayList<String> DateTime(String targetDate, int time) {
        ///根据时间获得当天的booking array
        ArrayList<String> result = new ArrayList<>();
        
        //拿当天的booking array
        ArrayList<String> bookings = Date(targetDate);
        
        int targetTime = time;
                
        for (String line : bookings){
            String[] data = line.split(",");

            if (Integer.parseInt(data[3]) == targetTime) {
                result.add(line);
            }
        }
        return result;
    }    
    
    public static ArrayList<String> Date(String targetDate) {
        ///根据年月日获得当天的booking array
        ArrayList<String> result = new ArrayList<>();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/Booking/bookings.csv"));
            //
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                /*
                data[0]=B0001
                data[1]=user
                */
                
                // skip header (if need)
                if (data[0].equals("booking_id")) continue;

                if (data[2].equals(targetDate)) {
                    //B0001,cyh,2026-03-25,10,Yoga,John,BOOKED
                    result.add(line);
                    
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static ArrayList<String> viewStaffBooked(String staffname){
        // find the bookings that equal staff name and status is BOOKED
        ArrayList<String> result = new ArrayList<>();
       
        //get booking that equal staff name and status is BOOKED
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/Booking/bookings.csv"));
            System.out.println("\n---------- bookings ----------" );
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                //B0006,user,2026-03-30,10,Yoga,null,BOOKED
                
                // only display the booking that equal staff name and status is BOOKED
                if (data[5].equals(staffname) && data[6].equals("BOOKED")) {
                    System.out.println(line);
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
}
