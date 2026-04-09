/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Yi Hang
 */
public class Booking {
    static Scanner sc = new Scanner(System.in);
    
    public static void page(String username) {
        /// user booking page
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

            targetDate = getValidatedDate(year, month, day);

            if (!targetDate.equals("INVALID")) {
                break;
            }
            System.out.println("Invalid date, try again.");
        }

        // -------------------- ask for time --------------------
        bookingTime(); // display time table 
        
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
        ArrayList<String> bookings = Filter.viewEmptyTrainingByDate(targetDate, timeSelect(timeChoice));
        
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
        add(username, targetDate, timeSelect(timeChoice), trainingSelect(trainingChoice));
    }
    
    public static void deletePage(String username) {
        /// user delete bookinh page
        String targetDate;

        // -------------------- ask for date --------------------
        while (true) {
            System.out.print("\nYear(or enter '0' to exit): ");
            String year = sc.nextLine();
            if (year.equals("0")) return;

            System.out.print("Month: ");
            String month = sc.nextLine();

            System.out.print("Day: ");
            String day = sc.nextLine();

            targetDate = getValidatedDate(year, month, day);
            
            // valid check
            if (!targetDate.equals("INVALID")) break;

            System.out.println("Invalid date, try again.");
        }

        // -------------------- get user's booking --------------------
        ArrayList<String> bookings = Filter.UserNamedate(username, targetDate);
        
        System.out.println("\n=============== Booking ===============");
        
        for (String line : bookings){
            System.out.println(line);
        }
        
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        // -------------------- choose booking id --------------------
        System.out.print("\nEnter booking_id to delete (or '0' to exit): ");
        String input = sc.nextLine().trim().toUpperCase();
        
        // enter 0 for exit
        if (input.equals("0")) return;

        // -------------------- validate id --------------------
        boolean found = false; // for display layout
        
        for (String b : bookings) {
            if (b.startsWith(input + ",")) {
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("❌ Invalid booking_id!");
            return; // return to user menu
        }

        // -------------------- delete process --------------------
        ArrayList<String> updated = new ArrayList<>(); // create new array to store change
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/assignment/bookings.csv"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // skip target line
                if (data[0].equals(input)) {
                    continue;
                }
                
                updated.add(line); //store all data to "update" array without the target line
            }

            br.close();

            // rewrite file
            FileWriter fw = new FileWriter("src/assignment/bookings.csv");
            for (String l : updated) {
                fw.write(l + "\n");
            }
            fw.close();

            System.out.println(" Booking deleted successfully! ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public static void select(String staffname) {
        /// Let staff select the bookings that are no staff select
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

            targetDate = getValidatedDate(year, month, day);
            
            // valid check
            if (!targetDate.equals("INVALID")) break;

            System.out.println("Invalid date, try again.");
        }
        
        boolean shouldRefreshList = true; // for display layout
        
        // loop for staff can continue choose booking_id
        while (true) {
            // refresh booking table
            ArrayList<String> bookings = Filter.WithOutStaff(targetDate);
            
            // check is empty or not
            if (bookings.isEmpty()) {
                System.out.println("\nNo available booking for this date.");
                break;
            }
            
            // only in needed that will display the booking list
            if (shouldRefreshList) {
                System.out.println("\n---------- bookings ----------");
                for (String line : bookings) {
                    System.out.println(line);
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
                updateStaff(targetId, staffname);
                shouldRefreshList = true;
            } else {
                // input error display error message and don't display booking list again 
                System.out.println("❌ Invalid booking_id! Please enter a valid ID from the list.");
                shouldRefreshList = false;
            }
        }
    }
    
    public static void completeTraining(String staffname) {
        
        Filter.viewStaffBooked(staffname); //display booking that equal staff name and status is BOOKED
            
        System.out.print("\nEnter booking_id to complete: ");
        String targetId = sc.nextLine().trim().toUpperCase(); // Makesure input format
            
        updateStatus(targetId); // update bookings.csv
    }
    
    

    public static void updateStaff(String bookingId, String staffname) {
        //update bookings.csv

        ArrayList<String> updated = new ArrayList<>();
        
        //crete "update" array to store data
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/assignment/bookings.csv"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data[0].equals(bookingId)) {
                    //B0003,user,2026-03-29,10,Back,null,BOOKED
                    // change null to staff
                    data[5] = staffname;

                    line = String.join(",", data);
                }

                updated.add(line);
            }

            br.close();

            //rewritter booking.csv
            FileWriter fw = new FileWriter("src/assignment/bookings.csv");
            for (String l : updated) {
                fw.write(l + "\n");
            }
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void updateStatus(String targetId) {
        ArrayList<String> update = new ArrayList<>();
        boolean found = false; // valid control

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/assignment/bookings.csv"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // match ID
                if (data[0].equals(targetId)) {
                    data[6] = "COMPLETED";
                    line = String.join(",", data);
                    found = true; 
                }
                update.add(line);
            }
            br.close();

            // only found is true will rewriter booking.csv
            if (found) {
                FileWriter fw = new FileWriter("src/assignment/bookings.csv");
                for (String l : update) {
                    fw.write(l + "\n");
                }
                fw.close();
                System.out.println(" Success: Training marked as COMPLETED!");
            } else {
                System.out.println(" Error: Booking ID: \"" + targetId + "\" not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void viewTheDate() {
        ///display all the document inside the bookings.csv in the day
        
        String targetDate;
        while (true) {
            System.out.print("\nYear: ");
            String year = sc.nextLine();
            System.out.print("Month: ");
            String month = sc.nextLine();
            System.out.print("Day: ");
            String day = sc.nextLine();

            targetDate = getValidatedDate(year, month, day);

            if (!targetDate.equals("INVALID")) {
                break;
            }

            System.out.println("Invalid date, try again.");
        }
        
        //拿当天的booking array
        ArrayList<String> bookings = Filter.Date(targetDate);
        for(String line : bookings){
            System.out.println("========== booking ==========");
            System.out.println(line);
        }
    }
    
    public static void add(String username, String targetDate, int time, String training) {
            ///upload to bookings.csv
            
            /*
            booking_id,username,date,time,activity,staff,status
            B0001,cyh,2026-03-25,10,Chest Workout,John,BOOKED
            B0002,ali,2026-03-25,13,Cardio,Sarah,COMPLETED
            
            booking_id
            username    ✓
            date        ✓
            time        ✓
            activity    ✓
            staff
            status
            
            */
            
            //------------------- booking id ------------------------
            String booking_id = generateBookingId();
                    
            //------------------- staff ------------------------
            String staff = null;
            //------------------- status ------------------------
            //(BOOKED / COMPLETED / CANCELLED)
            String status = "BOOKED";
            
            //------------------- process ------------------------
            try {    
                FileWriter fw = new FileWriter("src/assignment/bookings.csv", true); 
                /*
                booking_id,
                username,
                date,
                time,
                activity,
                staff,
                status
                */
                fw.write(booking_id + "," + 
                        username  + "," + 
                        targetDate + "," + 
                        time + "," + 
                        training + "," + 
                        staff + "," + 
                        status+ 
                        "\n");
                fw.close();
                System.out.println("Booking successful!");

            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
    public static String generateBookingId() {
        String lastLine = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/assignment/bookings.csv"));
            String line;

            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lastLine = line;
                }
            }
            br.close();

            if (lastLine.equals("")) {
                return "B0001"; // if file is empty return b0001
            }

            String[] parts = lastLine.split(",");
            String lastId = parts[0]; // get last booking_id

            // use substring(1) skip "b"，let "0001" change to integer
            int num = Integer.parseInt(lastId.substring(1));
            num++;

            // 使用 %04d 格式化为 4 位数字，前面自动补 0
            return String.format("B%04d", num); 
            // ----------------------------------------------

        } catch (Exception e) {
            e.printStackTrace(); // 调试用
            return "B0001"; // 发生异常时的回退方案
        }
    }
    
    public static String getValidatedDate(String year, String month, String day) {
        /// check the date is valid or not

        // check input is integer or not
        if (!year.matches("\\d+") || !month.matches("\\d+") || !day.matches("\\d+")) {
            // "\\d" = integer      "+" = at least one
            return "INVALID";
        }

        int y = Integer.parseInt(year);
        int m = Integer.parseInt(month);
        int d = Integer.parseInt(day);

        // ✅ 年份自动补 2000
        if (y < 100) {
            y += 2000;
        }

        try {
            // ✅ 自动检查月份 & 日期是否合法
            LocalDate date = LocalDate.of(y, m, d);

            // 格式化成 yyyy-MM-dd
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return date.format(formatter);

        } catch (Exception e) {
            return "INVALID";
        }
    }
    
    public static String trainingSelect(int trainingChoice){
        //timeChoice 1 2 3 4 to correct training
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
        //timeChoice 1 2 3 4 to correct time
        switch (timeChoice) {
            case 1: return 10;
            case 2: return 13;
            case 3: return 16;
            case 4: return 19;
            default: return 0;
        }
    }
    
    public static void bookingTime() {
        ///just display time that can be choose
        
        System.out.println("\nAvailable Time:");
        System.out.println("1. 10AM ~ 12PM");
        System.out.println("2. 1PM ~ 3PM");
        System.out.println("3. 4PM ~ 6PM");
        System.out.println("4. 7PM ~ 9PM");
        System.out.println("Enter the no. to select time");
    }
    
}
