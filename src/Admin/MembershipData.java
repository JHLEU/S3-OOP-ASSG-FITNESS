/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

import java.util.Scanner;

public class MembershipData {
    private String username; // Matches your CSV "username"
    private String tier;
    private double price;
    private boolean isDeleted = false; // Flag for the delete function

    public MembershipData(String username, String tier) {
        this.username = username;
        this.tier = tier;
        ranking(tier); // Set initial price based on tier
    }

    // --- GETTERS AND SETTERS ---
    public String getUsername() { 
        return username; 
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getTier() { 
        return tier; 
    }
    
    public void setTier(String tier){
       this.tier = tier;
    }
    
    public double getPrice() { 
        return price; 
    }
    
    public void setPrice(double price){
        this.price = price;
    }
    public boolean isDeleted() { 
        return isDeleted; 
    }

    // --- THE CHOICE METHOD ---
    public void manageMember() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Managing Member: " + username + " ---");
        System.out.println("Current Tier: " + tier);
        System.out.println("1. Upgrade/Change to Gold (RM300)");
        System.out.println("2. Upgrade/Change to Silver (RM200)");
        System.out.println("3. Upgrade/Change to Bronze (RM100)");
        System.out.println("4. DELETE Member Account");
        System.out.print("Enter your choice (1-4): ");
        
        int choice = sc.nextInt();

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
                deleteMember();
                break;
            default:
                System.out.println("Invalid choice. No changes made.");
        }
    }

    // Helper to update tier and price together
    private void updateTier(String newTier) {
        this.tier = newTier;
        ranking(newTier);
        System.out.println("Member " + username + " updated to " + tier + ". New Price: RM" + price);
    }

    private void deleteMember() {
        this.isDeleted = true;
        System.out.println("Member " + username + " marked for deletion.");
    }

    public void ranking(String tierName) {
        if (tierName.equalsIgnoreCase("gold")) {
            this.price = 300;
        } else if (tierName.equalsIgnoreCase("silver")) {
            this.price = 200;
        } else if (tierName.equalsIgnoreCase("bronze")) {
            this.price = 100;
        } else {
            this.price = 0;
            System.out.println("Unknown tier. Price set to 0.");
        }
    }
    
    @Override
    public String toString() {
    // Returns a nicely formatted summary of the member
        return String.format("Member ID: %-15s | Tier: %-10s | Price: RM%.2f", 
            username, tier, price);
    }
}

