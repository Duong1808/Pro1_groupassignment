package rmit.cosc2081.groupassignment;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserList {
    //1. attribute
    private ArrayList<PortManager> listOfManagers;
    //2. constructor

    public UserList() {
        listOfManagers= new ArrayList<>();
    }

    //3. get, set methods


    public ArrayList<PortManager> getListOfManagers() {
        return listOfManagers;
    }

    public void setListOfManagers(ArrayList<PortManager> listOfManagers) {
        this.listOfManagers = listOfManagers;
    }

    //4. input, output
    private void printAdminMenu() {
        System.out.println("1. Add New Port Manager");
        System.out.println("2. View All Port Managers");
        System.out.println("3. Remove Port Manager By Username");
        System.out.println("0. Exit");
    }

    public void showAdminTasks() {
        Scanner scan = new Scanner(System.in);
        boolean flag = true;
        do {
            printAdminMenu();
            System.out.println("Please select the following option >>>>>>>");
            int selected = Integer.parseInt(scan.nextLine());
            switch (selected) {
                case 1 -> {
                    addPortManager();
                    break;
                }
                case 2 -> {
                    showManagerList();
                    break;
                }
                case 3 -> {
                    removePortManager();
                    break;
                }
                case 0 -> flag = false;
                default -> System.out.println("Please enter the valid option!!!");
            }
        } while (flag);
    }

    public void removePortManager() {
        Scanner scan = new Scanner(System.in);
        System.out.println("------------- Remove Port Manager ----------------");
        System.out.println("Please enter Username of Manager: ");
        String username = scan.nextLine();
        PortManager manager = findManagerByUsername(username);
        if (manager != null) {
            this.listOfManagers.remove(manager);
            writeToFile();
            System.out.println("The Manager is removed successfully");
        } else {
            System.out.println("The Username not found");
        }
    }

    public void writeToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("managers.txt"))) {
            for (User user : this.listOfManagers) {
                if (user instanceof PortManager manager) {
                    bufferedWriter.write(manager.getUsername() + " " + manager.getPassword() + " " + manager.getRole() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PortManager findManagerByUsername(String username) {
        for (User user : this.listOfManagers) {
            if (user instanceof PortManager manager && manager.getUsername().equalsIgnoreCase(username)) {
                return manager;
            }
        }
        return null;
    }

    public void addPortManager() {
        PortManager portManager = PortManager.createPortManager(listOfManagers);
        this.listOfManagers.add(portManager);
            try {
                FileWriter fileWriter = new FileWriter("managers.txt", true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.println(portManager.getUsername() + " " + portManager.getPassword() + " " + portManager.getRole());
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    public void showManagerList() {
        clearPortManagers();
        readPortManagersFromFile("managers.txt");
        for (User user : this.listOfManagers) {
            if (user.getRole().equals("manager")) {
                user.output();
            }
        }
    }

    public void clearPortManagers() {
        this.listOfManagers.removeIf(user -> user != null);
    }


    public User authenticate(String username, String password) {
        readAdminFromFile("admin.txt");
        readPortManagersFromFile("managers.txt");
        SystemAdmin admin = SystemAdmin.getInstance();
        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            return admin;
        } else {
           for (PortManager manager : this.listOfManagers){
               if(manager.getUsername().equals(username) && manager.getPassword().equals(password)){
                   return manager;
               }
           }
            System.out.println("Authentication failed. Invalid credentials.");
            return null;
        }
    }


    public void readPortManagersFromFile(String filename) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 3) {
                    String username = parts[0];
                    String password = parts[1];
                    String role = parts[2];
                    PortManager portManager = new PortManager(username, password, role, null);
                    this.listOfManagers.add(portManager);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readAdminFromFile(String filename) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            if ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    User admin = SystemAdmin.getInstance();
                    admin.setUsername(username);
                    admin.setPassword(password);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

