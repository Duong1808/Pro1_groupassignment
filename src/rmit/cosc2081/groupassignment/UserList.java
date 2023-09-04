package rmit.cosc2081.groupassignment;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserList {
    //1. attribute
    private ArrayList<User> listOfUser;
    //2. constructor

    public UserList() {
        listOfUser = new ArrayList<User>();
    }

    //3. get, set methods

    public ArrayList<User> getListOfUser() {
        return listOfUser;
    }

    public void setListOfUser(ArrayList<User> listOfUser) {
        this.listOfUser = listOfUser;
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
            this.listOfUser.remove(manager);
            writeToFile();
            System.out.println("The Manager is removed successfully");
        } else {
            System.out.println("The Username not found");
        }
    }

    public void writeToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("managers.txt"))) {
            for (User user : listOfUser) {
                if (user instanceof PortManager manager) {
                    bufferedWriter.write(manager.getUsername() + " " + manager.getPassword() + " " + manager.getRole() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PortManager findManagerByUsername(String username) {
        for (User user : listOfUser) {
            if (user instanceof PortManager manager && manager.getUsername().equalsIgnoreCase(username)) {
                return manager;
            }
        }
        return null;
    }

    public void addPortManager() {
        PortManager portManager = PortManager.createPortManager();
        if (usernameExists(portManager.getUsername())) {
            System.out.println("Username already exists. Please choose a different username.");
        } else {
            this.listOfUser.add(portManager);
            try {
                FileWriter fileWriter = new FileWriter("managers.txt", true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.println(portManager.getUsername() + " " + portManager.getPassword() + " " + portManager.getRole());
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean usernameExists(String username) {
        readAdminFromFile("admin.txt");
        readPortManagersFromFile("managers.txt");
        String lowercaseUsername = username.toLowerCase();
        return listOfUser.stream().anyMatch(user -> user.getUsername().toLowerCase().equals(lowercaseUsername));
    }

    public void showManagerList() {
        clearPortManagers(); // Clear the existing port managers when calling to many times
        readPortManagersFromFile("managers.txt");
        for (User user : this.listOfUser) {
            if (user.getRole().equals("manager")) {
                user.output();
            }
        }
    }

    public void clearPortManagers() {
        this.listOfUser.removeIf(user -> user instanceof PortManager);
    }



    public User authenticate() {
        readAdminFromFile("admin.txt");

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your Username: ");
        String username = scan.nextLine();
        System.out.println("Enter your Password: ");
        String password = scan.nextLine();
        for (User user : this.listOfUser) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        System.out.println("Authentication failed. Invalid credentials.");
        return null;
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
                    this.listOfUser.add(portManager);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readAdminFromFile(String filename) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    String username = parts[0];
                    String password = parts[1];
                    SystemAdmin systemAdmin = new SystemAdmin(username, password);
                    this.listOfUser.add(systemAdmin);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

