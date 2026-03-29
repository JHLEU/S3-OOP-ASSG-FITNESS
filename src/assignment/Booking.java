/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Yi Hang
 */

public class Booking {
    public static void page(String username) {
        /// booking main page
        // -------------------- ask for date --------------------
        String targetDate;
        while (true) {
            System.out.print("\nYear: ");
            String year = sc.nextLine();
            System.out.print("Month: ");
            String month = sc.nextLine();
            System.out.print("Day: ");
            String day = sc.nextLine();

            targetDate = getValidatedDate(year, month, day);

            if (!targetDate.equals("INVALID")) {
                break;
            }
            System.out.println("Invalid date, try again.");
        }

        // -------------------- ask for time --------------------
        bookingTime(); // 显示时间菜单 (1-4)
        
        int timeChoice = -1;
        while (true) {
            if (sc.hasNextInt()) {
                timeChoice = sc.nextInt();
                sc.nextLine(); // 清除回车符缓冲区
                
                // 检查数字是否在 1 到 4 之间
                if (timeChoice >= 1 && timeChoice <= 4) {
                    break; // 输入正确，跳出循环
                } else {
                    System.out.println("Invalid choice! Please enter a number between 1 and 4:");
                }
            } else {
                // 如果输入的不是数字（如字母、符号）
                System.out.println("Invalid input! Please enter a number (E.g 1):");
                sc.nextLine(); // 清除错误的字符串输入
            }
        }

        // -------------------- ask for select training --------------------
        ArrayList<String> bookings = viewEmptyTrainingByDate(targetDate, timeSelect(timeChoice));
        
        // if the time not have emmpty traning will return to user menu
        if (bookings.isEmpty()) {
            System.out.println("No training available for this time slot.");
            return; 
        }
        
        //display empty training
        System.out.println("\nAvailable Trainings:");
        for (String line : bookings) {
            System.out.println(line);
        }
        
        System.out.println("Enter the training you want (1-9):");
        
        int trainingChoice = -1;
        while (true) {
            if (sc.hasNextInt()) {
                trainingChoice = sc.nextInt();
                sc.nextLine(); //clear buffer
                
                // check value is in 1~9 or not
                if (trainingChoice >= 1 && trainingChoice <= 9) {
                    break; 
                } else {
                    System.out.println("Invalid choice! Please enter a number between 1 and 9:");
                }
            } else {
                System.out.println("Invalid input! Please enter a valid number (E.g 1):");
                sc.nextLine(); // 清除错误的字符串输入
            }
        }

        // -------------------- execute add --------------------
        add(username, targetDate, timeSelect(timeChoice), trainingSelect(trainingChoice));
    }
    
    public static void select(String staffname) {
        // Let staff select the bookings that are no staff select
        String targetDate;
        
        // 1. 只输入一次日期
        while (true) {
            System.out.print("\nYear: ");
            String year = sc.nextLine();
            System.out.print("Month: ");
            String month = sc.nextLine();
            System.out.print("Day: ");
            String day = sc.nextLine();

            targetDate = getValidatedDate(year, month, day);

            if (!targetDate.equals("INVALID")) break;

            System.out.println("Invalid date, try again.");
        }
        
        boolean shouldRefreshList = true;
        
        // 2. 循环让员工连续选择 booking_id
        while (true) {
            // 每次循环重新获取最新的未分配 bookings，这样认领过的就会消失
            ArrayList<String> bookings = filterBookingWithOutStaff(targetDate);
            
            // empty condition
            if (bookings.isEmpty()) {
                System.out.println("\nNo available booking for this date.");
                break;
            }
            
            // 只有在需要时才打印这 90 多行列表
            if (shouldRefreshList) {
                System.out.println("\n---------- bookings ----------");
                for (String line : bookings) {
                    System.out.println(line);
                }
            }
            
            // Get booking_id or Exit
            System.out.print("\nEnter booking_id to select (or enter '0' to exit): ");
            String input = sc.nextLine().trim(); 
            if (input.equals("0")) {
                System.out.println("Exiting to Staff Menu...");
                return;
            }
            String targetId = input.toUpperCase();
           

            // check booking_id is inside the list or not
            boolean isValidId = false;
            for (String b : bookings) {
                if (b.startsWith(targetId + ",")) { 
                    isValidId = true;
                    break;
                }
            }
            
            if (isValidId) {
                updateStaff(targetId, staffname);
                shouldRefreshList = true; // 成功认领后，下次循环刷新列表
            } else {
                // --- 关键点：输入错误时不重新打印列表，只打印错误信息 ---
                System.out.println("❌ Invalid booking_id! Please enter a valid ID from the list.");
                shouldRefreshList = false; // 报错后，不刷新列表，直接再次等待输入
            }
        }
    }
    
    public static void completeTraining(String staffname) {
        
        viewStaffBooked(staffname);//display booking that equal staff name and status is BOOKED
        
        System.out.print("\nEnter booking_id to complete: ");
        String targetId = sc.nextLine().trim().toUpperCase(); // Makesure input format
        
        updateStatus(targetId);
    }
    
    public static ArrayList<String> viewEmptyTrainingByDate(String targetDate, int time) {
        ///根据当天array判断是否有空位
        ArrayList<String> result = new ArrayList<>();
        
        System.out.println();//排版用
        
        //拿当天的booking array
        ArrayList<String> bookings = filterBookingByDateTime(targetDate, time);
        
        int Yoga=0, HIIT=0, Chest=0, Arm=0, Leg=0, Bicep=0, Tricep=0, Abs=0, Back=0;

        for (String line : bookings) {
            //B0001,cyh,2026-03-25,10,Yoga,John,BOOKED
            String[] data = line.split(",");

            switch (data[4]) {
                case "Yoga": Yoga++; break;
                case "HIIT": HIIT++; break;
                case "Chest": Chest++; break;
                case "Arm": Arm++; break;
                case "Leg": Leg++; break;
                case "Bicep": Bicep++; break;
                case "Tricep": Tricep++; break;
                case "Abs": Abs++; break;
                case "Back": Back++; break;
            }
        }

        int max = 10;

        // 只显示还有位置的
        if (Yoga < max) result.add("1.Yoga (" + Yoga + "/" + max + ")");//yoga(2/10)
        if (HIIT < max) result.add("2.HIIT (" + HIIT + "/" + max + ")");
        if (Chest < max) result.add("3.Chest (" + Chest + "/" + max + ")");
        if (Arm < max) result.add("4.Arm (" + Arm + "/" + max + ")");
        if (Leg < max) result.add("5.Leg (" + Leg + "/" + max + ")");
        if (Bicep < max) result.add("6.Bicep (" + Bicep + "/" + max + ")");
        if (Tricep < max) result.add("7.Tricep (" + Tricep + "/" + max + ")");
        if (Abs < max) result.add("8.Abs (" + Abs + "/" + max + ")");
        if (Back < max) result.add("9.Back (" + Back + "/" + max + ")");

        return result;
    }
    
    public static ArrayList<String> filterBookingWithOutStaff(String targetDate){
        // find the bookings that are no staff select
        ArrayList<String> result = new ArrayList<>();
        
        ArrayList<String> bookings = filterBookingByDate(targetDate);
        
        for (String line : bookings){
            String[] data = line.split(",");
            //B0006,user,2026-03-30,10,Yoga,null,BOOKED
            if (data[2].equals(targetDate) && data[5].equals("null")) {
                result.add(line);
            }
        }
        return result;
    }
    
    public static ArrayList<String> filterBookingByDateTime(String targetDate, int time) {
        ///根据时间获得当天的booking array
        ArrayList<String> result = new ArrayList<>();
        
        //拿当天的booking array
        ArrayList<String> bookings = filterBookingByDate(targetDate);
        
        int targetTime = time;
                
        for (String line : bookings){
            String[] data = line.split(",");

            if (Integer.parseInt(data[3]) == targetTime) {
                result.add(line);
            }
        }
        return result;
    }    
    
    public static ArrayList<String> filterBookingByDate(String targetDate) {
        ///根据年月日获得当天的booking array
        ArrayList<String> result = new ArrayList<>();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/assignment/bookings.csv"));
            //
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                /*
                data[0]=B0001
                data[1]=user
                */
                
                // skip header (if need)
                if (data[0].equals("booking_id")) continue;

                if (data[2].equals(targetDate)) {
                    //B0001,cyh,2026-03-25,10,Yoga,John,BOOKED
                    result.add(line);
                    
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static void updateStaff(String bookingId, String staffname) {
        //update bookings.csv

        ArrayList<String> updated = new ArrayList<>();
        
        //crete "update" to store data
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/assignment/bookings.csv"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data[0].equals(bookingId)) {
                    //B0003,user,2026-03-29,10,Back,null,BOOKED
                    // 改 staff
                    data[5] = staffname;

                    line = String.join(",", data);
                }

                updated.add(line);
            }

            br.close();

            //rewritter booking.csv
            FileWriter fw = new FileWriter("src/assignment/bookings.csv");
            for (String l : updated) {
                fw.write(l + "\n");
            }
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean updateStatus(String targetId) {
        ArrayList<String> update = new ArrayList<>();
        boolean found = false; // 增加一个标志位

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/assignment/bookings.csv"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // match ID
                if (data[0].equals(targetId)) {
                    data[6] = "COMPLETED";
                    line = String.join(",", data);
                    found = true; 
                }
                update.add(line);
            }
            br.close();

            // only found is true will rewriter booking.csv
            if (found) {
                FileWriter fw = new FileWriter("src/assignment/bookings.csv");
                for (String l : update) {
                    fw.write(l + "\n");
                }
                fw.close();
                System.out.println("✅ Success: Training marked as COMPLETED!");
            } else {
                System.out.println("❌ Error: Booking ID " + targetId + " not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return found; // 返回结果
    }
    
    public static ArrayList<String> viewStaffBooked(String staffname){
        // find the bookings that equal staff name and status is BOOKED
        ArrayList<String> result = new ArrayList<>();
       
        //get booking that equal staff name and status is BOOKED
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/assignment/bookings.csv"));
            System.out.println("\n---------- bookings ----------" );
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                //B0006,user,2026-03-30,10,Yoga,null,BOOKED
                
                // only display the booking that equal staff name and status is BOOKED
                if (data[5].equals(staffname) && data[6].equals("BOOKED")) {
                    System.out.println(line);
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public static void viewTheDate() {
        ///display all the document inside the bookings.csv in the day
        
        String targetDate;
        while (true) {
            System.out.print("\nYear: ");
            String year = sc.nextLine();
            System.out.print("Month: ");
            String month = sc.nextLine();
            System.out.print("Day: ");
            String day = sc.nextLine();

            targetDate = getValidatedDate(year, month, day);

            if (!targetDate.equals("INVALID")) {
                break;
            }

            System.out.println("Invalid date, try again.");
        }
        
        //拿当天的booking array
        ArrayList<String> bookings = filterBookingByDate(targetDate);
        for(String line : bookings){
            System.out.println("========== booking ==========");
            System.out.println(line);
        }
    }
    
    public static void add(String username, String targetDate, int time, String training) {
            ///upload to bookings.csv
            
            /*
            booking_id,username,date,time,activity,staff,status
            B0001,cyh,2026-03-25,10,Chest Workout,John,BOOKED
            B0002,ali,2026-03-25,13,Cardio,Sarah,COMPLETED
            
            booking_id
            username    ✓
            date        ✓
            time        ✓
            activity    ✓
            staff
            status
            
            */
            
            //------------------- booking id ------------------------
            String booking_id = generateBookingId();
                    
            //------------------- staff ------------------------
            String staff = null;
            //------------------- status ------------------------
            //(BOOKED / COMPLETED / CANCELLED)
            String status = "BOOKED";
            
            //------------------- process ------------------------
            try {    
                FileWriter fw = new FileWriter("src/assignment/bookings.csv", true); 
                /*
                booking_id,
                username,
                date,
                time,
                activity,
                staff,
                status
                */
                fw.write(booking_id + "," + 
                        username  + "," + 
                        targetDate + "," + 
                        time + "," + 
                        training + "," + 
                        staff + "," + 
                        status+ 
                        "\n");
                fw.close();
                System.out.println("Booking successful!");

            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    
    public static String generateBookingId() {
        String lastLine = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("bookings.csv"));
            String line;

            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lastLine = line;
                }
            }
            br.close();

            if (lastLine.equals("")) {
                return "B0001"; // if file is empty return b0001
            }

            String[] parts = lastLine.split(",");
            String lastId = parts[0]; // get last booking_id

            // use substring(1) skip "b"，let "0001" change to integer
            int num = Integer.parseInt(lastId.substring(1));
            num++;

            // 使用 %04d 格式化为 4 位数字，前面自动补 0
            return String.format("B%04d", num); 
            // ----------------------------------------------

        } catch (Exception e) {
            e.printStackTrace(); // 调试用
            return "B0001"; // 发生异常时的回退方案
        }
    }
    
    public static void bookingTime() {
        ///just display time that can be choose
        
        System.out.println("\nAvailable Time:");
        System.out.println("1. 10AM ~ 12PM");
        System.out.println("2. 1PM ~ 3PM");
        System.out.println("3. 4PM ~ 6PM");
        System.out.println("4. 7PM ~ 9PM");
        System.out.println("Enter the no. to select time");
    }
    
    public static int timeSelect(int timeChoice){
        //timeChoice 1 2 3 4 to correct time
        switch (timeChoice) {
            case 1: return 10;
            case 2: return 13;
            case 3: return 16;
            case 4: return 19;
            default: return 0;
        }
    }
    
    public static String trainingSelect(int trainingChoice){
        //timeChoice 1 2 3 4 to correct training
        switch(trainingChoice){
            case 1: return "Yoga";
            case 2: return "HIIT";
            case 3: return "Chest";
            case 4: return "Arm";
            case 5: return "Leg";
            case 6: return "Bicep";
            case 7: return "Tricep";
            case 8: return "Abs";
            case 9: return "Back";
            default: return "Invalid";
        }
    }
    
    public static String getValidatedDate(String year, String month, String day) {
        ///check  the date is valid or not
        int y = Integer.parseInt(year);
        int m = Integer.parseInt(month);
        int d = Integer.parseInt(day);

        // ✅ 年份自动补 2000
        if (y < 100) {
            y += 2000;
        }

        try {
            // ✅ 自动检查月份 & 日期是否合法
            LocalDate date = LocalDate.of(y, m, d);

            // 格式化成 yyyy-MM-dd
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return date.format(formatter);

        } catch (Exception e) {
            return "INVALID";
        }
    }

}
