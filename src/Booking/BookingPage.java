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
public class BookingPage {
    
    /// UI class
    // give me the user / staff name to my method !!!
    
    static Scanner sc = new Scanner(System.in);
    
    public static void userPage(String username) {

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

        // -------------------- ask for time --------------------
        Method.bookingTime();

        String timeChoice;

        while (true) {

            System.out.print("Enter time choice (1-4): ");
            timeChoice = sc.nextLine().trim();

            String selectedTime = Method.timeSelect(timeChoice);

            if (!selectedTime.equals("0")) {
                break;
            }

            System.out.println("Invalid choice! Please enter 1-4.");
        }


        String selectedTime = Method.timeSelect(timeChoice);

        // -------------------- show available training --------------------
        ArrayList<TrainingSlot> slots = File.viewEmptyTrainingByDate(targetDate, selectedTime);

        if (slots.isEmpty()) {
            System.out.println("No training available.");
            return;
        }

        System.out.println("\nAvailable Trainings:");
        for (int i = 0; i < slots.size(); i++) {
            System.out.println((i + 1) + ". " + slots.get(i).display());
        }

        // -------------------- choose training --------------------
        int choice;
        while (true) {
            System.out.print("Choose (1-" + slots.size() + "): ");

            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();

                if (choice >= 1 && choice <= slots.size()) {
                    break;
                }

            } else {
                sc.nextLine();
            }

            System.out.println("Invalid choice!");
        }

        String selectedTraining = slots.get(choice - 1).getName();

        // -------------------- create booking object --------------------
        String bookingId = Method.generateBookingId();

        Booking newBooking = new Booking(
                bookingId,
                username,
                targetDate,
                selectedTime,
                selectedTraining,
                "null", // staff (还没assign)
                "BOOKED"
        );

        // -------------------- save --------------------
        File.appendData(newBooking);

        System.out.println("Booking successful! ID: " + bookingId);
    }
    
    public static void viewPage(String username) {

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
        ArrayList<Booking> bookings = File.userNamedate(username, targetDate);

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\n============= Booking =============");

        for (Booking o : bookings) {
            System.out.println("---------------------------------");
            System.out.println("Booking ID : " + o.getBookingId());
            System.out.println("Name       : " + o.getUsername());
            System.out.println("Date       : " + o.getDate());
            System.out.println("Time       : " + Method.formatTime(o.getTime()));
            System.out.println("Type       : " + o.getTraining());
            System.out.println("Trainer    : " + (o.getStaff().equals("null") ? "-" : o.getStaff() ) );
            System.out.println("---------------------------------");
        }

        // -------------------- choose booking id --------------------
        System.out.print("\nEnter booking_id to delete (0 to exit): ");
        String input = sc.nextLine().trim().toUpperCase();

        if (input.equals("0")) {
            return;
        }

        // -------------------- validate id --------------------
        boolean found = false;

        for (Booking b : bookings) {
            if (b.getBookingId().equalsIgnoreCase(input)) {
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Invalid booking_id!");
            return;
        }

        // -------------------- delete process --------------------
        ArrayList<Booking> allBookings = File.getData();
        ArrayList<Booking> updated = new ArrayList<>();

        for (Booking b : allBookings) {
            if (!b.getBookingId().equalsIgnoreCase(input)) {
                updated.add(b);
            }
        }

        File.updateData(updated);

        System.out.println("\nBooking deleted successfully!");
    }

    public static void staffSelectPage(String staffname) {

        String targetDate;

        // -------------------- ask date --------------------
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

        boolean shouldRefreshList = true;

        while (true) {

            // Booking object
            ArrayList<Booking> bookings = File.withOutStaff(targetDate);

            if (bookings.isEmpty()) {
                System.out.println("\nNo available booking for this date.");
                break;
            }

            // -------------------- display --------------------
            if (shouldRefreshList) {
                System.out.println("\n----------------- booking ------------------");

                for (Booking b : bookings) {
                    System.out.println("\n------------------------------------------");
                    System.out.println("Booking ID: " + b.getBookingId()
                            + "\tName: " + b.getUsername());
                    System.out.println("Time: " + Method.formatTime(b.getTime())
                            + "\tType: " + b.getTraining());
                }
            }

            // -------------------- input --------------------
            System.out.print("\nEnter booking_id (or 0 to exit): ");
            String input = sc.nextLine().trim().toUpperCase();

            if (input.equals("0")) {
                System.out.println("Exiting to Staff Menu...");
                return;
            }

            // -------------------- validate --------------------
            boolean isValidId = false;

            for (Booking b : bookings) {
                if (b.getBookingId().equalsIgnoreCase(input)) {
                    isValidId = true;
                    break;
                }
            }

            // -------------------- update --------------------
            if (isValidId) {
                File.updateStaff(input, staffname);
                System.out.println("Staff assigned successfully!");
                shouldRefreshList = true;

            } else {
                System.out.println("Invalid booking_id!");
                shouldRefreshList = false;
            }
        }
    }
    
    public static void staffCompleteTraining(String staffname) {

        // Booking object
        ArrayList<Booking> bookings = File.staffBooked(staffname);

        if (bookings.isEmpty()) {
            System.out.println("\nNo available booking.");
            return;
        }

        // -------------------- display --------------------
        for (Booking b : bookings) {
            System.out.println("\n------------------------------------------");
            System.out.println("Booking ID: " + b.getBookingId()
                    + "\tName: " + b.getUsername());
            System.out.println("Time: " + Method.formatTime(b.getTime())
                    + "\tType: " + b.getTraining());
        }

        // -------------------- input --------------------
        System.out.print("\nEnter booking_id to complete (0 to exit): ");
        String input = sc.nextLine().trim().toUpperCase();

        if (input.equals("0")) {
            return;
        }

        // -------------------- validate --------------------
        boolean found = false;

        for (Booking b : bookings) {
            if (b.getBookingId().equalsIgnoreCase(input)) {
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Invalid booking_id!");
            return;
        }

        // -------------------- update --------------------
        File.updateStatus(input);

        System.out.println("Training marked as COMPLETED!");
    }
    
}
