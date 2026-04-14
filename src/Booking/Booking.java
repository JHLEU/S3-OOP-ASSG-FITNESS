/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

/**
 *
 * @author Yi Hang
 */

public class Booking {

    private String bookingId;
    private String username;
    private String date;
    private String time;
    private String training;
    private String staff;
    private String status;

    // Constructor
    public Booking(String bookingId, String username, String date, String time, String training, String staff, String status) {
        this.bookingId = bookingId;
        this.username = username;
        this.date = date;
        this.time = time;
        this.training = training;
        this.staff = staff;
        this.status = status;
    }

    // Getter & Setter
    public String getBookingId() {return bookingId;}
    public void setBookingId(String bookingId) {this.bookingId = bookingId;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getDate() {return date;}
    public void setDate(String date) {this.date = date;}

    public String getTime() {return time;}
    public void setTime(String time) {this.time = time;}

    public String getTraining() {return training;}
    public void setTraining(String training) {this.training = training;}

    public String getStaff() {return staff;}
    public void setStaff(String staff) {this.staff = staff;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
    
    public String toCSV() {
        return bookingId + "," + 
                username + "," + 
                date + ","+ 
                time + "," + 
                training + "," + 
                staff + "," + 
                status;
    }
    // change to CSV format
    
    @Override
    public String toString() {
        return bookingId + "," + 
                username + "," + 
                date + ","+ 
                time + "," + 
                training + "," + 
                staff + "," + 
                status;
    }
    
}


