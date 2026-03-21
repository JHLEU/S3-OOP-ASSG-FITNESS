/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

/**
 *
 * @author User
 */

public class MembershipData {
    private String tier;
    private double price;
  
    public MembershipData(String tier){
        this.tier = tier;
        
        if (tier.equalsIgnoreCase("gold")){
            this.price = 300;
        }else if (tier.equalsIgnoreCase("Silver")){
            this.price = 200;
        }else if (tier.equalsIgnoreCase("Basic")){
            this.price = 100;
        }else{
            System.out.println("Wrong value please enter again");
        }
        
    }
    
    public String getTier(){
        return tier;
    }
    
    public double getPrice(){
        return price;
    }
}
