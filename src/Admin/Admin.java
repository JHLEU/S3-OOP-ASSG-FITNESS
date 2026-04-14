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
public class Admin {
    private static final Scanner sc = new Scanner(System.in);
    private String Adminid;
    private String Password;
    private String role;
    
    public Admin(String Adminid,String Password, String role){
        this.Adminid = Adminid;
        this.Password = Password;
        this.role = role;
    }
    
    public String getAdminid(){
        return Adminid;
    }
    
    public void setAdminid(String Adminid){
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
    
    public static boolean AdminLogin() {
        
        int attempts = 3;

        while (attempts > 0) {
            System.out.println("\n=== Admin Login ===");
            System.out.print("Enter Admin ID (example A001): ");
            String Adminid = sc.nextLine();

            if (!Trainer.Auth.isValidTrainerIdFormat(Adminid)) {
                System.out.println("Invalid Admin ID format. Use 'AdminId' followed by 3 digits.");
                attempts--;
                System.out.println("Attempts left: " + attempts);
                continue;
            }

            System.out.print("Enter Password: ");
            String password = sc.nextLine();

            if (Trainer.Auth.verifyLogin(Adminid, password)) {
                System.out.println("Login successful.");
                return true;
            }

            System.out.println("Invalid Trainer ID or password.");
            attempts--;
            System.out.println("Attempts left: " + attempts);
        }

        return false;
    }
    
    // In your Admin class
    public boolean verify(String inputId, String inputPass) {
    // This compares the typed input with THIS specific admin's data
        return this.Adminid.equals(inputId) && this.Password.equals(inputPass);
    }
    
}
