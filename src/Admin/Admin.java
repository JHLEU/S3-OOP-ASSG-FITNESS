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
    private String Adminid;
    private String Password;
    
    public Admin(String Adminid,String Password){
        this.Adminid = Adminid;
        this.Password = Password;
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
    
    public void AdminLogin(String Adminid, String Password){
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------Admin Log in--------");
        System.out.println("Please insert your ID: ");
        
        }
}
