/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Booking;

/**
 *
 * @author Yi Hang
 */
public class TrainingSlot {
    private String name;
    private int current;
    private int max;

    public TrainingSlot(String name, int current, int max) {
        this.name = name;
        this.current = current;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public int getCurrent() {
        return current;
    }

    public int getMax() {
        return max;
    }

    public String display() {
        return name + " (" + current + "/" + max + ")";
    }    
}

