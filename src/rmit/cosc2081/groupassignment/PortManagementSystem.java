package rmit.cosc2081.groupassignment;

import java.util.ArrayList;
import java.util.Scanner;

import static rmit.cosc2081.groupassignment.Container.clearContainers;
import static rmit.cosc2081.groupassignment.Container.readContainersFromFile;
import static rmit.cosc2081.groupassignment.Port.clearPorts;
import static rmit.cosc2081.groupassignment.Port.readPortsFromFile;
import static rmit.cosc2081.groupassignment.Trip.clearTrips;
import static rmit.cosc2081.groupassignment.Trip.readTripFromFile;
import static rmit.cosc2081.groupassignment.UserList.*;
import static rmit.cosc2081.groupassignment.Vehicle.clearVehicles;
import static rmit.cosc2081.groupassignment.Vehicle.readVehiclesFromFile;

public class PortManagementSystem {
    public static void main(String[] args) {
        doMenu();
    }

    private static void printMenu() {
        System.out.println("COSC2081 GROUP ASSIGNMENT\nCONTAINER PORT MANAGERMENT SYSTEM\n" +
                "Instructor: Mr.Minh Vu & Dr.Phong Ngo\nGroup: Team 29\nS3891919, Nguyen A Luy\n" +
                "S3916893, Tang Khanh Linh");
        System.out.println("USER LOGIN (SELECT NUMBER): ");
        System.out.println("1. LOGIN");
        System.out.println("0. Quit");
    }

    private static void doMenu() {
        boolean flag = true;
        Scanner scan = new Scanner(System.in);
        UserList users = new UserList();
        ArrayList<Container> containers = new ArrayList<>();
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        ArrayList<Trip> trips = new ArrayList<>();
        ArrayList<Port> ports = new ArrayList<>();
        clearContainers(containers);
        clearPorts(ports);
        clearVehicles(vehicles);
        clearTrips(trips);
        readContainersFromFile("containers.txt", containers);
        readPortsFromFile("ports.txt", ports);
        readVehiclesFromFile("vehicles.txt", vehicles, containers, ports);
        readTripFromFile("trips.txt", trips, vehicles, ports);
        User authenticatedUser = null;

        do {
            printMenu();
            int num = Integer.parseInt(scan.nextLine());
            switch (num) {
                case 1 -> {
                    System.out.println("Enter your Username: ");
                    String username = scan.nextLine();
                    System.out.println("Enter your Password: ");
                    String password = scan.nextLine();
                    authenticatedUser = users.authenticate(username, password, ports);
                    if (authenticatedUser == null) {
                        System.out.println(YELLOW_TEXT+ BLACK_BG +"Login Not Successfully, Please Try Again!!!" + RESET);
                    } else {
                        if (authenticatedUser.getRole().equalsIgnoreCase("admin")) {
                            users.showAdminTasks(trips, ports, vehicles, containers);
                        } else if (authenticatedUser.getRole().equalsIgnoreCase("manager") && authenticatedUser instanceof PortManager portManager) {
                            Port port = portManager.getPort();
                            if (port == null) {
                                System.out.println(YELLOW_TEXT+ BLACK_BG + "You must assign a port to perform actions. Please assign a port first."+ RESET);
                            } else {
                                users.showManagerTasks(port.getPortID(), ports, containers, trips, vehicles);
                            }
                        } else {
                            System.out.println(YELLOW_TEXT + BLACK_BG + "You do not have sufficient permissions." + RESET);
                        }
                    }
                }
                case 0 -> {
                    flag = false;
                    break;
                }
                default -> {
                    System.out.println(YELLOW_TEXT + BLACK_BG + "Wrong Input Number, Please Check Your Input!!!" + RESET);
                }
            }
        } while (flag);
    }
}
