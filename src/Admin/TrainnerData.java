/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

/**
 *
 * @author User
 */

import java.io.*;
import java.util.Scanner;

public final class TrainnerData {
    private String id;
    private String name;
    private double hours;
    private double salary;
    private final double RATE = 15.0;
    private boolean isDeleted = false;

    public TrainnerData(String id, String name, double hours) {
        this.id = id;
        this.name = name;
        this.hours = hours;
        calculateSalary();
    }
    
    // --- Getters/Setters and calculateSalary ---
    public String getId(){ 
        return id; 
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public String getname(){
        return name;
    }
    
    public void setname(String name){
        this.name = name;
    }
    
    public double gethours(){
        return hours;
    }
    
    public void sethours(double hours){
        this.hours = hours;
    }
    
    public double getsalary(){
        return salary;
    }
    
    public boolean isDeleted(){ 
        return isDeleted; 
    }
    
    public void setIsDeleted(boolean status){
        this.isDeleted = status;
    }
    
    public void calculateSalary() { 
        this.salary = this.hours * RATE; 
    }

    // --- NEW: Search Method to see bookings handled by this trainer ---
    public void viewBookings() {
        // Path to the booking file in your teammate's folder
        String bookingPath = "src/Booking/bookings.csv";
        System.out.println("\n--- Handling Bookings for " + name + " (" + id + ") ---");
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(bookingPath))) {
            String line;
            br.readLine(); // Skip header row

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Assuming bookings.csv: BookingID, MemberID, TrainerID, Date
                // We check if the TrainerID (data[2]) matches this object's id
                if (data.length >= 3 && data[2].trim().equalsIgnoreCase(this.id)) {
                    System.out.println("-> Booking ID: " + data[0] + " | Member: " + data[1] + " | Date: " + data[3]);
                    found = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Could not access bookings.csv. Check if path is correct.");
        }

        if (!found) {
            System.out.println("No active bookings found for this trainer.");
        }
    }

    public void manageTrainer() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n=== Trainer: " + name + " ===");
        System.out.println("1. Update Hours");
        System.out.println("2. View Handled Bookings");
        System.out.println("3. DELETE");
        System.out.println("0. RETURN"); // Updated to 4
        System.out.print("Choice: ");
        
        // Using String input prevents the program from crashing if a user types a letter
        String choice = sc.next(); 
        
        switch (choice) {
            case "1":
                System.out.print("Enter total hours worked: ");
                this.hours = sc.nextDouble();
                calculateSalary(); // Ensure this method exists in your class
                System.out.println("New Salary: RM" + salary);
                break;
            case "2":
                viewBookings(); 
                break;
            case "3":
                this.isDeleted = true;
                System.out.println("Trainer marked for deletion.");
                break;
            case "0":
                System.out.println("Returning to Dashboard...");
                return; 
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }
    
    @Override
    public String toString(){
        return "Trainner ID: " + id + "\n" +
                "Trainner name: " + name + "\n" +
                "Trainner working hours: " + hours + "\n" +
                "Trainner salary: " + salary ;
    }
    
}