package rmit.cosc2081.groupassignment;

import java.util.ArrayList;
import java.util.Scanner;

import static rmit.cosc2081.groupassignment.Container.clearContainers;
import static rmit.cosc2081.groupassignment.Container.readContainersFromFile;
import static rmit.cosc2081.groupassignment.Port.clearPorts;
import static rmit.cosc2081.groupassignment.Port.readPortsFromFile;
import static rmit.cosc2081.groupassignment.Trip.clearTrips;
import static rmit.cosc2081.groupassignment.Trip.readTripFromFile;
import static rmit.cosc2081.groupassignment.Vehicle.clearVehicles;
import static rmit.cosc2081.groupassignment.Vehicle.readVehiclesFromFile;

public class PortManagementSystem {
    public static void main(String[] args) {
        doMenu();
    }

    private static void printMenu() {
        System.out.println("WELCOME TO THE PORT MANAGEMENT SYSTEM:");
        System.out.println("Please choose number to execute task: ");
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
        clearVehicles(vehicles);
        clearPorts(ports);
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
                        System.out.println("Please try again.");
                    } else {
                        if (authenticatedUser.getRole().equalsIgnoreCase("admin")) {
                            users.showAdminTasks(trips, ports, vehicles, containers);
                        } else if (authenticatedUser.getRole().equalsIgnoreCase("manager") && authenticatedUser instanceof PortManager portManager) {
                            Port port = portManager.getPort();
                            if (port == null) {
                                System.out.println("You must assign a port to perform actions. Please assign a port first.");
                            } else {
                                users.showManagerTasks(port.getPortID(), ports, containers, trips);
                            }
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
