/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

/**
 *
 * @author User
 */
import java.util.Scanner;
import Trainer.Auth; // You MUST import this to use Trainer's logic

public class Admin {
    private static final Scanner sc = new Scanner(System.in);
    private String Adminid;
    private String Password;
    private String role;
    private boolean isDeleted = false;
    
    public Admin(String Adminid, String Password, String role){
        this.Adminid = Adminid;
        this.Password = Password;
        this.role = role;
    }
    
    // --- GETTERS AND SETTERS ---
    public String getAdminid(){ 
        return Adminid; 
    }
    
    public void setAdminid(String Adminid) { 
        this.Adminid = Adminid; 
    }
    public String getPassword(){ 
        return Password; 
    }
    
    public void setPassword(String Password){ 
        this.Password = Password; 
    }
    public String getRole(){ 
        return role; 
    }
    
    public void setRole(String role){ 
        this.role = role; 
    }
    
    public boolean isDeleted(){ 
        return isDeleted; 
    }
    
    public void setDeleted(boolean isDeleted){ 
        this.isDeleted = isDeleted; 
    }
    
    // --- LOGIN METHOD ---
    public static boolean AdminLogin() {
        int attempts = 3;
        
        Readfile rf = new Readfile();

        while (attempts > 0) {
            
            // Load ONLY the Admin data
            Admin[] admins = rf.readAdminFile("src/Admin/Admin.csv");
            
            System.out.println("\n=== Admin Login ===");
            System.out.print("Enter Admin ID (e.g., A001): ");
            String inputId = sc.nextLine();

            // Simple format check: Must start with 'A' and have digits
            if (!inputId.startsWith("A") || inputId.length() < 2) {
                System.out.println("Invalid Admin ID format. Must start with 'A'.");
                attempts--;
                continue;
            }

            System.out.print("Enter Password: ");
            String inputPass = sc.nextLine();

            // Loop through your Admin array to find a match
            for (Admin a : admins) {
                if (a != null && a.verify(inputId, inputPass)) {
                    // Check if account is deleted/deactivated
                    if (a.isDeleted()) {
                        System.out.println("Login Failed: This account is deactivated.");
                        return false;
                    }
                    System.out.println("Login successful. Welcome " + a.getRole());
                    return true;
                }
            }

            System.out.println("Invalid Admin ID or Password.");
            attempts--;
            System.out.println("Attempts left: " + attempts);
        }
        return false;
    }
    
    public boolean verify(String inputId, String inputPass) {
        return this.Adminid.equals(inputId) && this.Password.equals(inputPass);
    }
}

    