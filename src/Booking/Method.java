/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Yi Hang
 */
public class Method {
    /// smaller Method 
    
    public static String getValidatedDate(String year, String month, String day) {
        /// check the date is valid or not and return

        // check input is integer or not
        if (!year.matches("\\d+") || !month.matches("\\d+") || !day.matches("\\d+")) {
            // "\\d" = integer      "+" = at least one
            return "INVALID";
        }

        int y = Integer.parseInt(year);
        int m = Integer.parseInt(month);
        int d = Integer.parseInt(day);

        // auto plus 2000
        if (y < 100) {
            y += 2000;
        }

        try {
            // valid check
            LocalDate date = LocalDate.of(y, m, d);

            // format to yyyy-MM-dd
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return date.format(formatter);

        } catch (Exception e) {
            return "INVALID";
        }
    }
    
    public static String generateBookingId() {
        /// auto create booking id
        
        try {
            ArrayList<String> booking = File.getData();

            // if only have header
            if (booking.size() <= 1) {
                return "B0001";
            }

            String lastLine = "";

            // Find the last valid row of data from the end to the beginning (safer).
            for (int i = booking.size() - 1; i >= 1; i--) { // Start from end, skip the header.
                if (!booking.get(i).trim().isEmpty()) {
                    lastLine = booking.get(i);
                    break;
                }
            }
            
            // if is empty
            if (lastLine.equals("")) {
                return "B0001";
            }

            String[] data = lastLine.split(",");
            String lastId = data[0];

            int num = Integer.parseInt(lastId.substring(1));
            num++;

            return String.format("B%04d", num);

        } catch (Exception e) {
            e.printStackTrace();
            return "B0001";
        }
    }

    public static String trainingSelect(int trainingChoice){
        /// training Choice 1 2 3 4 to String training
        
        switch(trainingChoice){
            case 1: return "Yoga";
            case 2: return "HIIT";
            case 3: return "Chest";
            case 4: return "Arm";
            case 5: return "Leg";
            case 6: return "Bicep";
            case 7: return "Tricep";
            case 8: return "Abs";
            case 9: return "Back";
            default: return "Invalid";
        }
    }
    
    public static int timeSelect(int timeChoice){
        /// timeChoice 1 2 3 4 to correct time
        
        switch (timeChoice) {
            case 1: return 10;
            case 2: return 13;
            case 3: return 16;
            case 4: return 19;
            default: return 0;
        }
    }
    
    public static String formatTime(String time) {
        int t = Integer.parseInt(time);

        switch (t) {
            case 10: return "10AM ~ 12PM";
            case 13: return "1PM ~ 3PM";
            case 16: return "4PM ~ 6PM";
            case 19: return "7PM ~ 9PM";
            default: return time; // fallback
        }
    }


    
    public static void bookingTime() {
        /// just display time that can be choose
        
        System.out.println("\nAvailable Time:");
        System.out.println("1. 10AM ~ 12PM");
        System.out.println("2. 1PM ~ 3PM");
        System.out.println("3. 4PM ~ 6PM");
        System.out.println("4. 7PM ~ 9PM");
        System.out.println("Enter the no. to select time");
    }
    
}
