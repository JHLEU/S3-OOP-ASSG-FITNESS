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
        /// auto create Booking ID
        try {
            ArrayList<Booking> bookings = File.getData();

            // if empty
            if (bookings.isEmpty()) {
                return "B0001";
            }

            // use last booking
            int max = 0;

            for (Booking b : bookings) {
                int num = Integer.parseInt(b.getBookingId().substring(1));
                if (num > max) {
                    max = num;
                }
            }

            return String.format("B%04d", max + 1);
            
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
    
    public static String timeSelect(String timeChoice) {
        /// time Choice 1 2 3 4 to String training
        
        switch (timeChoice) {
            case "1": return "10";
            case "2": return "13";
            case "3": return "16";
            case "4": return "19";
            default: return "0";
        }
    }

    public static String formatTime(String time) {
        /// display the actualy time
        
        switch (time) {
            case "10": return "10AM ~ 12PM";
            case "13": return "1PM ~ 3PM";
            case "16": return "4PM ~ 6PM";
            case "19": return "7PM ~ 9PM";
            default: return time;
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
