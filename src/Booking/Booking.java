/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Yi Hang
 */
public class Booking {
    
    /// UI class
    // give me the user / staff name to my method !!!
    
    static Scanner sc = new Scanner(System.in);
    
    public static void userPage(String username) {
        /// user add booking page
        
        // -------------------- ask for date --------------------
        String targetDate;
        
        while (true) {
            System.out.print("\nYear(or enter '0' to exit): ");
            String year = sc.nextLine();
            if (year.equals("0")) return;
            
            System.out.print("Month: ");
            String month = sc.nextLine();
            
            System.out.print("Day: ");
            String day = sc.nextLine();

            targetDate = Method.getValidatedDate(year, month, day);

            if (!targetDate.equals("INVALID")) {
                break;
            }
            System.out.println("Invalid date, try again.");
        }

        // -------------------- ask for time --------------------
        Method.bookingTime(); // display time table 
        
        int timeChoice = -1;
        while (true) {
            if (sc.hasNextInt()) {
                timeChoice = sc.nextInt();
                sc.nextLine(); // clear buffer
                
                // check input is between 1~4 or not
                if (timeChoice >= 1 && timeChoice <= 4) {
                    break; // input correct, break out loop
                } else {
                    System.out.println("Invalid choice! Please enter a number between 1 and 4:");
                }
                
            } else {
                // input is not the number
                System.out.println("Invalid input! Please enter a number (E.g 1):");
                sc.nextLine(); // clear error input
            }
        }

        // -------------------- ask for select training --------------------
        ArrayList<String> bookings = File.viewEmptyTrainingByDate(targetDate, Method.timeSelect(timeChoice));
        
        // if the time not have emmpty traning will return to user menu
        if (bookings.isEmpty()) {
            System.out.println("No training available for this time slot.");
            return; 
        }
        
        //display empty training
        System.out.println("\nAvailable Trainings:");
        for (String line : bookings) {
            System.out.println(line);
        }
        
        System.out.println("Enter the training you want (1-9):");
        
        int trainingChoice = -1;
        while (true) {
            if (sc.hasNextInt()) {
                trainingChoice = sc.nextInt();
                sc.nextLine(); //clear buffer
                
                // check value is in 1~9 or not
                if (trainingChoice >= 1 && trainingChoice <= 9) {
                    break; 
                } else {
                    System.out.println("Invalid choice! Please enter a number between 1 and 9:");
                }
            } else {
                System.out.println("Invalid input! Please enter a valid number (E.g 1):");
                sc.nextLine(); // clear error input
            }
        }

        // -------------------- execute add --------------------
        File.add(username, targetDate, Method.timeSelect(timeChoice), Method.trainingSelect(trainingChoice));
    }
    
    public static void viewPage(String username) {
        /// user view and delete booking page
        
        String targetDate;

        // -------------------- ask for date --------------------
        while (true) {
            System.out.print("\nYear(or enter '0' to exit): ");
            String year = sc.nextLine();
            if (year.equals("0")) {
                return;
            }

            System.out.print("Month: ");
            String month = sc.nextLine();

            System.out.print("Day: ");
            String day = sc.nextLine();

            targetDate = Method.getValidatedDate(year, month, day);

            if (!targetDate.equals("INVALID")) {
                break;
            }

            System.out.println("Invalid date, try again.");
        }

        // -------------------- get user's booking --------------------
        ArrayList<String> bookings = File.userNamedate(username, targetDate);

        System.out.println("\n============= Booking =============");

        for (String line : bookings) {
            String[] data = line.split(",");
            
            System.out.println("---------------------------------");
            System.out.println("Booking ID : " + data[0]);
            System.out.println("Name       : " + data[1]);
            System.out.println("Date       : " + data[2]);
            System.out.println("Time       : " + Method.formatTime(data[3]) );
            System.out.println("Type       : " + data[4]);
            System.out.println("Trainer    : " + (data[5].equals("null") ? "-" : data[5]));
            System.out.println("---------------------------------");
        }

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        // -------------------- choose booking id --------------------
        System.out.print("\nEnter booking_id to delete your booking");
        System.out.print("\nIf you just want to view your booking that enter '0': ");
        String input = sc.nextLine().trim().toUpperCase();

        if (input.equals("0")) {
            return;
        }

        // -------------------- validate id --------------------
        boolean found = false;

        for (String b : bookings) {
            if (b.startsWith(input + ",")) {
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Invalid booking_id!");
            return;
        }

        // -------------------- delete process --------------------
        ArrayList<String> booking = File.getData();
        ArrayList<String> updated = new ArrayList<>();

        for (String line : booking) {
            String[] data = line.split(",");

            // sava header and not target line
            if (data[0].equalsIgnoreCase("booking_id") || !data[0].equals(input)) {
                updated.add(line);
            }
        }

        File.updateData(updated);

        System.out.println("\nBooking deleted successfully!");
    }

    public static void staffSelectPage(String staffname) {
        /// staff select the bookings that are no staff select
        
        String targetDate;
        
        // only need input one time year month day
        while (true) {
            System.out.print("\nYear(or enter '0' to exit): ");
            String year = sc.nextLine();
            if (year.equals("0")) return;
            
            System.out.print("Month: ");
            String month = sc.nextLine();
            
            System.out.print("Day: ");
            String day = sc.nextLine();

            targetDate = Method.getValidatedDate(year, month, day);
            
            // valid check
            if (!targetDate.equals("INVALID")) break;

            System.out.println("Invalid date, try again.");
        }
        
        boolean shouldRefreshList = true; // for display layout
        
        // loop for staff can continue choose booking_id
        while (true) {
            // refresh booking table
            ArrayList<String> bookings = File.withOutStaff(targetDate);
            
            // check is empty or not
            if (bookings.isEmpty()) {
                System.out.println("\nNo available booking for this date.");
                break;
            }
            
            // only in needed that will display the booking list
            if (shouldRefreshList) {
                System.out.println("\n----------------- booking ------------------");
                for (String line : bookings) {
                    String[] data = line.split(",");
                    
                    System.out.println("\n------------------------------------------");
                    System.out.println("Booking ID: " + data[0] + "\tName: " + data[1]);
                    System.out.println("time: " + Method.formatTime(data[3])+ "\tType: " + data[4]);
                }
            }
            
            // Get booking_id or Exit
            System.out.print("\nEnter booking_id to select (or enter '0' to exit): ");
            String input = sc.nextLine().trim(); 
            // enter 0 to break out to staff menu
            if (input.equals("0")) {
                System.out.println("Exiting to Staff Menu...");
                return;
            }
            String targetId = input.toUpperCase();
           

            // check booking_id is inside the list or not
            boolean isValidId = false;
            for (String b : bookings) {
                if (b.startsWith(targetId + ",")) { 
                    isValidId = true;
                    break;
                }
            }
            
            if (isValidId) {
                // correctly find the booking id and display booking list again
                File.updateStaff(targetId, staffname);
                shouldRefreshList = true;
            } else {
                // input error display error message and don't display booking list again 
                System.out.println(" Invalid booking_id! Please enter a valid ID from the list.");
                shouldRefreshList = false;
            }
        }
    }
    
    public static void staffCompleteTraining(String staffname) {
        /// staff completed training use this page
        
        //display booking that equal staff name and status is BOOKED
        ArrayList<String> bookings =File.staffBooked(staffname);
        
        if (bookings.isEmpty()) {
            System.out.println("\nNo available booking for this date.");
            return;
        }
        
        for(String line : bookings){
            String[] data = line.split(",");
            System.out.println("\n------------------------------------------");
            System.out.println("Booking ID: " + data[0] + "\tName: " + data[1]);
            System.out.println("time: " + Method.formatTime(data[3])+ "\tType: " + data[4]);
        }
        
        System.out.print("\nEnter booking_id to complete: ");
        String targetId = sc.nextLine().trim().toUpperCase(); // Makesure input format
            
        File.updateStatus(targetId); // update bookings.csv
    }
    
}
