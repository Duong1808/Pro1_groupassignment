package rmit.cosc2081.groupassignment;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class PortManagementSystem {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        doMenu();
    }

    private static void printMenu(){
        System.out.println("WELCOME TO THE PORT MANAGEMENT SYSTEM:");
        System.out.println("Please choose number to execute task: ");
        System.out.println("1. LOGIN");
        System.out.println("0. Quit");
    }

    private static void doMenu() {
        boolean flag = true;
        Scanner scan = new Scanner(System.in);
        UserList users = new UserList();
        User authenticatedUser = null;

        do {
            printMenu();
            int num = Integer.parseInt(scan.nextLine());
            switch (num) {
                case 1 -> {
                    authenticatedUser = users.authenticate();
                    if (authenticatedUser == null) {
                        System.out.println("Authentication failed. Please try again.");
                    } else {
                        if (authenticatedUser.getRole().equals("admin")) {
                            users.showAdminTasks();
                        } else if (authenticatedUser.getRole().equals("manager")) {
                            System.out.println("Login as manager successfully");
                        } else {
                            System.out.println("You do not have sufficient permissions.");
                        }
                    }
                }
                case 0 -> {
                    flag = false;
                    break;
                }
                default -> {
                    System.out.println("Wrong Input, Try again!!!");
                }
            }
        } while (flag);
    }

}
