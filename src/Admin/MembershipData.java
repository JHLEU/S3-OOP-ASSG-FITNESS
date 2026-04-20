/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;



import java.util.Scanner;

public final class MembershipData {
    private String username; // Changed from memberId to match your constructor
    private String tier;
    private double price;
    private boolean isDeleted = false;
    private String password;
    private double totalSpent;

    public MembershipData(String username, String password, double totalSpent, String tier, boolean isDeleted) {
        this.username = username;
        this.password = password;
        this.totalSpent = totalSpent;
        this.tier = tier;
        this.isDeleted = isDeleted;
        ranking(tier);
    }
    
    // --- GETTERS AND SETTERS ---
    public String getUsername(){ 
        return username; 
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getPassword(){ 
        return password; 
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public double getTotalSpent(){
        return totalSpent; 
    }
    
    public void setTotalSpent (double totalspent){
        this.totalSpent = totalspent;
    }
    
    public String getTier(){ 
        return tier; 
    }
    
    public void setTier(String tier) {
        this.tier = tier;
        ranking(tier); 
    }
    
    public double getPrice(){ 
        return price; 
    }
    
    public boolean isDeleted(){ 
        return isDeleted; 
    }
    
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    

    public void restoreAccount() {
        this.isDeleted = false;
        System.out.println("System: Account for " + username + " has been restored.");
    }

    // --- CHOICE METHOD (The Menu) ---
    public void manageMember() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Managing Member: " + username + " ---");
        System.out.println("Current Tier: " + tier + " | Total Spent: RM" + totalSpent);
        System.out.println("1. Upgrade to Gold (RM300)");
        System.out.println("2. Upgrade to Silver (RM200)");
        System.out.println("3. Upgrade to Bronze (RM100)");
        System.out.println("4. DELETE Member Account");
        System.out.println("0. Exit");
        System.out.print("Selection: ");
        
        int choice = sc.nextInt();
        sc.nextLine(); // Clear the buffer

        switch (choice) {
            case 1:
                updateTier("Gold");
                break;
            case 2:
                updateTier("Silver");
                break;
            case 3:
                updateTier("Bronze");
                break;
            case 4:
                this.isDeleted = true;
                System.out.println("Member " + username + " marked for deletion.");
                break;
            case 0:
                System.out.println("Returning to Dashboard...");
                return;
            default:
                System.out.println("Invalid choice. No changes made.");
                break;
        }
    }

    private void updateTier(String newTier) {
        this.tier = newTier;
        ranking(newTier);
        System.out.println("Tier updated to " + tier);
    }

    // --- RANKING LOGIC ---
    public void ranking(String tierName) {
        if (tierName.equalsIgnoreCase("Gold")) {
            this.price = 300;
        } else if (tierName.equalsIgnoreCase("Silver")) {
            this.price = 200;
        } else if (tierName.equalsIgnoreCase("Bronze") || tierName.equalsIgnoreCase("Basic")) {
            this.price = 100;
        } else {
            this.price = 0;
        }
    }

    
}

