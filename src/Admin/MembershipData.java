/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;


public class MembershipData {
    private String memberId;
    private String tier;
    private double price;
  
    public MembershipData(String memberId, String tier){
        this.memberId = memberId;
        this.tier = tier;
        ranking(tier);
    }
    
    public String getmemberId(){
        return memberId;
    }
    
    public void setmemberId(String memberId){
        this.memberId = memberId;
    }
    
    public String getTier(){
        return tier;
    }
    
    public void setTier(String tier){
        this.tier = tier;
        ranking(tier);
    }
   
    
    public double getPrice(){
        return price;
    }
    
    public void setPrice(double price){
        this.price = price;     
    }
    
    public void ranking(String tierName){
        
        if (tierName.equalsIgnoreCase("gold")){
            this.price = 300;
        }else if (tierName.equalsIgnoreCase("Silver")){
            this.price = 200;
        }else if (tierName.equalsIgnoreCase("Basic")){
            this.price = 100;
        }else{
            this.price = 0;
            System.out.println("Wrong value please enter again");
        }
        
    }
}

