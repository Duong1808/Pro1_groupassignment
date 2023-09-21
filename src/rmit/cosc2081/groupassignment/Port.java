package rmit.cosc2081.groupassignment;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Port {
    private String portID;
    private String name;
    private double latitude;
    private double longitude;
    private double storingCapacity;
    private boolean landingAbility;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Container> containers;
    private ArrayList<Trip> trips;

    private PortManager portManager;

    public Port(String id, String name, double latitude, double longitude, double storingCapacity, boolean landingAbility) {
        this.portID = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
        this.vehicles = new ArrayList<>();
        this.containers = new ArrayList<>();
        this.trips = new ArrayList<>();
    }

    public Port() {
    }

    public PortManager getPortManager() {
        return portManager;
    }

    public void setPortManager(PortManager portManager) {
        this.portManager = portManager;
    }

    public String getPortID() {
        return portID;
    }

    public void setPortID(String portID) {
        this.portID = portID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getStoringCapacity() {
        return storingCapacity;
    }

    public void setStoringCapacity(double storingCapacity) {
        this.storingCapacity = storingCapacity;
    }

    public boolean isLandingAbility() {
        return landingAbility;
    }

    public void setLandingAbility(boolean landingAbility) {
        this.landingAbility = landingAbility;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public void setContainers(ArrayList<Container> containers) {
        this.containers = containers;
    }

    public ArrayList<Trip> getTrips() {
        return trips;
    }

    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public String toString() {
        return "Port{" +
                "portID='" + portID + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", storingCapacity=" + storingCapacity +
                ", landingAbility=" + landingAbility +
                '}';
    }

    public static Port removePort(ArrayList<Port> ports){
        Scanner scan = new Scanner(System.in);
        System.out.println("------------- Remove Port ----------------");
        System.out.println("Please enter Port ID: ");
        String portID = scan.nextLine();
        Port port = findPortByID(portID, ports);
        if (port != null) {
            ports.remove(port);
            updateFilePort(ports);
            System.out.println("The Port is removed successfully");
        } else {
            System.out.println("The Port is not found");
        }
        return port;
    }

    public static Port findPortByID(String portID, ArrayList<Port> ports) {
        for (Port port : ports) {
            if (port.getPortID().equalsIgnoreCase(portID)) {
                return port;
            }
        }
        return null;
    }
    public static Port createPort(ArrayList<Port> ports){
        Scanner scan = new Scanner(System.in);
        String portID;
        do {
            System.out.println("Enter the port ID (must start with 'P'): ");
            portID = scan.nextLine();
            if (!portID.toLowerCase().startsWith("p")) {
                System.out.println("Check port format, Start with 'P'!!!");
            } else if (portExisted(ports, portID)) {
                System.out.println("Port ID already exists. Please choose a different ID.");
            } else {
                break;
            }
        } while (true);
        Port port = null;
        System.out.println("Please enter group.asm.Port Name: ");
        String portName = scan.nextLine();
        System.out.println("Please enter the group.asm.Port Latitude: ");
        double latitude = Double.parseDouble(scan.nextLine());
        System.out.println("Please enter the group.asm.Port Longitude: ");
        double longitude = Double.parseDouble(scan.nextLine());
        System.out.println("Please enter the group.asm.Port Storing Capacity: ");
        double storingCapacity = Double.parseDouble(scan.nextLine());
        boolean landingAbility = selectLandingAbility(scan);
        port = new Port(portID,portName,latitude,longitude,storingCapacity,landingAbility);
        if(port != null){
            writeToFilePort(port);
        }
        return port;
    }
    public static boolean selectLandingAbility(Scanner scan) {
        System.out.println("Select Landing Ability for this Port: ");
        System.out.println("1. Landing");
        System.out.println("2. Not Landing");

        int select = Integer.parseInt(scan.nextLine());

        switch (select) {
            case 1 -> {
                return true;
            }
            case 2 -> {
                return false;
            }
            default -> {
                System.out.println("Invalid selection. Please choose 1 (Landing) or 2 (Not Landing).");
                return selectLandingAbility(scan);
            }
        }
    }
    public static void clearPorts(ArrayList<Port> ports) {
        ports.removeIf(port -> port != null);
    }

    public static boolean portExisted(ArrayList<Port> ports, String portID) {
        String lowercaseID = portID.toLowerCase();
        return ports.stream().anyMatch(port -> port.getPortID().toLowerCase().equals(lowercaseID));
    }

    public static void writeToFilePort(Port port) {
        try {
            FileWriter fileWriter = new FileWriter("ports.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(port.getPortID() + " " + port.getName() + " " +
                    port.getLatitude() + " " + port.getLongitude() + " " +
                    port.getStoringCapacity() + " " + port.isLandingAbility());

            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateFilePort(ArrayList<Port> ports) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("ports.txt"))) {
            for (Port port : ports) {
                bufferedWriter.write(port.getPortID() + " " + port.getName() + " " +
                        port.getLatitude() + " " + port.getLongitude() + " " +
                        port.getStoringCapacity() + " " + port.isLandingAbility() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readPortsFromFile(String filename, ArrayList<Port> ports) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 6) {
                    String id = parts[0];
                    String name = parts[1];
                    double latitude = Double.parseDouble(parts[2]);
                    double longitude = Double.parseDouble(parts[3]);
                    double storingCapacity = Double.parseDouble(parts[4]);
                    boolean landingAbility = Boolean.parseBoolean(parts[5]);

                    Port port = new Port(id, name, latitude, longitude, storingCapacity, landingAbility);
                    ports.add(port);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}