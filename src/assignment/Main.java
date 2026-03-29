/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package assignment;

/**
 *
 * @author User
 */
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
public class Main {
     public static void main(String[] args){
         
        Booking.page("user");
        ArrayList<MembershipData> list = new ArrayList();
        
        System.out.println("Fitness Club System Testing ===\n");
        try{
            
            File myfile = new File("E:\\TARUMT\\Java\\Assignment\\Assignment\\src\\assignment\\members.csv");
            Scanner reader = new Scanner(myfile);
            
            if (reader.hasNextLine()){
                reader.nextLine();
                
            }
            
            while (reader.hasNextLine()){
                String line = reader.nextLine();
                String[] parts = line.split(",");
                
                
                MembershipData m = new MembershipData(parts[2]);
                list.add(m);
            }
            reader.close();
            
        } catch (Exception e){
            System.out.println("Error: Could not find the CSV file!");
        }
        
        System.out.println("Total Members Loaded: " + list.size());
        
    }
    
}
