import Admin.Admin;
import Admin.AdminDashboard;

public class Main {
    public static void main(String[] args) {
        // Step 1: Test the Login Logic
        System.out.println("--- Starting System Test ---");
        
        if (Admin.AdminLogin()) {
            // Step 2: If login succeeds, launch the Dashboard
            AdminDashboard dashboard = new AdminDashboard();
            dashboard.displayMenu();
        } else {
            System.out.println("System Test Failed: Unauthorized Access.");
        }
    }
}