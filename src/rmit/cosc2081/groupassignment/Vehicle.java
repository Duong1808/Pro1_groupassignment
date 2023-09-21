package rmit.cosc2081.groupassignment;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static rmit.cosc2081.groupassignment.Container.findContainerByID;
import static rmit.cosc2081.groupassignment.Port.findPortByID;

public class Vehicle {
    private String vehicleID;
    private String vehicleName;
    private double fuelAmount;
    private double carryingCapability;
    private double fuelCapability;
    private Port currentPort = null;
    private ArrayList<Container> containers;
    private int numCons;

    private String vehicleType;

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Vehicle(String vehicleID, String vehicleName, double fuelAmount, double carryingCapability, double fuelCapability, Port currentPort, ArrayList<Container> containers) {
        this.vehicleID = vehicleID;
        this.vehicleName = vehicleName;
        this.fuelAmount = fuelAmount;
        this.carryingCapability = carryingCapability;
        this.fuelCapability = fuelCapability;
        this.currentPort = currentPort;
        this.containers = containers;
    }

    public Vehicle() {
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public double getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(double fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public double getCarryingCapability() {
        return carryingCapability;
    }

    public void setCarryingCapability(double carryingCapability) {
        this.carryingCapability = carryingCapability;
    }

    public double getFuelCapability() {
        return fuelCapability;
    }

    public void setFuelCapability(double fuelCapability) {
        this.fuelCapability = fuelCapability;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public void setContainers(ArrayList<Container> containers) {
        this.containers = containers;
    }

    public static Vehicle removeVehicle(ArrayList<Vehicle> vehicles) {
        Scanner scan = new Scanner(System.in);
        System.out.println("------------- Remove Vehicle ----------------");
        System.out.println("Please enter Vehicle ID: ");
        String vehicleID = scan.nextLine();
        Vehicle vehicle = findVehicleByID(vehicleID, vehicles);
        if (vehicle != null) {
            vehicles.remove(vehicle);
            updateFileVehicle(vehicles);
            System.out.println("The Vehicle is removed successfully");
        } else {
            System.out.println("The Vehicle not found");
        }
        return vehicle;
    }

    public static Vehicle findVehicleByID(String vehicleID, ArrayList<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleID().equalsIgnoreCase(vehicleID)) {
                return vehicle;
            }
        }
        return null;
    }


    public static Vehicle createVehicle(ArrayList<Vehicle> vehicles) {
        Scanner scan = new Scanner(System.in);
        String vehicleID;
        do {
            System.out.println("Enter the vehicle ID (must start with 'sh' or 'tr'): ");
            vehicleID = scan.nextLine();

            if (!vehicleID.toLowerCase().startsWith("sh") && !vehicleID.toLowerCase().startsWith("tr")) {
                System.out.println("Check vehicleID format, Start with 'sh' or 'tr'!!!");
            } else if (vehicleExisted(vehicles, vehicleID)) {
                System.out.println("Vehicle ID already exists. Please choose a different ID.");
            } else {
                break;
            }
        } while (true);


        System.out.println("Enter the vehicle name: ");
        String vehicleName = scan.nextLine();

        System.out.println("Enter the fuel amount: ");
        double fuelAmount = Double.parseDouble(scan.nextLine());

        System.out.println("Enter the carrying capability: ");
        double carryingCapability = Double.parseDouble(scan.nextLine());

        System.out.println("Enter the fuel capability: ");
        double fuelCapability = Double.parseDouble(scan.nextLine());

        Vehicle vehicle = null;
        if (vehicleID.startsWith("sh")) {
            vehicle = new Ship(vehicleID, vehicleName, fuelAmount, carryingCapability, fuelCapability, null, new ArrayList<>());
        } else {
            System.out.println("Select the Truck type:");
            System.out.println("1. Basic Truck");
            System.out.println("2. Reefer Truck");
            System.out.println("3. Tanker Truck");
            int truckType = Integer.parseInt(scan.nextLine());
            switch (truckType) {
                case 1 ->
                        vehicle = new BasicTruck(vehicleID, vehicleName, fuelAmount, carryingCapability, fuelCapability, null, new ArrayList<>());
                case 2 ->
                        vehicle = new ReeferTruck(vehicleID, vehicleName, fuelAmount, carryingCapability, fuelCapability, null, new ArrayList<>());
                case 3 ->
                        vehicle = new TankerTruck(vehicleID, vehicleName, fuelAmount, carryingCapability, fuelCapability, null, new ArrayList<>());
                default -> System.out.println("Invalid container type selection.");
            }
        }
        if(vehicle != null){
            writeToFileVehicle(vehicle);
        }
        vehicles.add(vehicle);
        return vehicle;
    }

    public static void writeToFileVehicle(Vehicle vehicle) {
        try {
            FileWriter fileWriter = new FileWriter("vehicles.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            StringBuilder containerIDsBuilder = new StringBuilder();
            for (Container container : vehicle.getContainers()) {
                if (containerIDsBuilder.length() > 0) {
                    containerIDsBuilder.append(", ");
                }
                containerIDsBuilder.append(container.getId());
            }
            String containerIDs = containerIDsBuilder.toString();

            printWriter.println(vehicle.getVehicleID() + " " + vehicle.getVehicleName() + " " +
                    vehicle.getVehicleType() + " " + vehicle.getFuelAmount() + " " +
                    vehicle.getCarryingCapability() + " " + vehicle.getFuelCapability() + " " +
                    null + " [" + containerIDs + "]");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateFileVehicle(ArrayList<Vehicle> vehicles) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("vehicles.txt"))) {
            for (Vehicle vehicle : vehicles) {
                StringBuilder containerIDsBuilder = new StringBuilder();
                for (Container container : vehicle.getContainers()) {
                    if (containerIDsBuilder.length() > 0) {
                        containerIDsBuilder.append(", ");
                    }
                    containerIDsBuilder.append(container.getId());
                }
                String containerIDs = containerIDsBuilder.toString();

                bufferedWriter.write(vehicle.getVehicleID() + " " + vehicle.getVehicleName() + " " +
                        vehicle.getVehicleType() + " " + vehicle.getFuelAmount() + " " +
                        vehicle.getCarryingCapability() + " " + vehicle.getFuelCapability() + " " +
                        vehicle.getCurrentPort().getPortID() + " [" + containerIDs + "]\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleID='" + vehicleID + '\'' +
                ", vehicleName='" + vehicleName + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", fuelAmount=" + fuelAmount +
                ", carryingCapability=" + carryingCapability +
                ", fuelCapability=" + fuelCapability +
                ", currentPort='" + getCurrentPort() + '\'' +
                ", containers=" + getContainers() +
                '}';
    }

    public static void clearVehicles(ArrayList<Vehicle> vehicles) {
        vehicles.removeIf(vehicle -> vehicle != null);
    }

    public static boolean vehicleExisted(ArrayList<Vehicle> vehicles, String vehicleID) {
        String lowercaseID = vehicleID.toLowerCase();
        return vehicles.stream().anyMatch(vehicle -> vehicle.getVehicleID().toLowerCase().equals(lowercaseID));
    }

    public static void readVehiclesFromFile(String filename, ArrayList<Vehicle> vehicles, ArrayList<Container> containers, ArrayList<Port> ports) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 7) {
                    String vehicleID = parts[0];
                    String vehicleName = parts[1];
                    String vehicleType = parts[2];
                    double fuelAmount = Double.parseDouble(parts[3]);
                    double carryingCapability = Double.parseDouble(parts[4]);
                    double fuelCapability = Double.parseDouble(parts[5]);
                    String portID = parts[6];
                    Port port = findPortByID(portID, ports);
                    if (port == null) {
                        port = new Port(portID, "", 0.0, 0.0, 0.0, false);
                    }

                    Vehicle vehicle;
                    if (vehicleType.equalsIgnoreCase("ship")) {
                        vehicle = new Ship(vehicleID, vehicleName, fuelAmount, carryingCapability, fuelCapability, port, new ArrayList<>());
                    } else if (vehicleType.equalsIgnoreCase("basic")) {
                        vehicle = new BasicTruck(vehicleID, vehicleName, fuelAmount, carryingCapability, fuelCapability, port, new ArrayList<>());
                    } else if (vehicleType.equalsIgnoreCase("reefer")) {
                        vehicle = new ReeferTruck(vehicleID, vehicleName, fuelAmount, carryingCapability, fuelCapability, port, new ArrayList<>());
                    } else if (vehicleType.equalsIgnoreCase("tanker")) {
                        vehicle = new TankerTruck(vehicleID, vehicleName, fuelAmount, carryingCapability, fuelCapability, port, new ArrayList<>());
                    } else {
                        System.out.println("Invalid vehicle type: " + vehicleType);
                        break;
                    }

                    if (parts.length > 7) {
                        String containersPart = parts[7];
                        if (!containersPart.equals("[]")) {
                            String[] containerIDs = containersPart.replaceAll("[\\[\\]]", "").split(",");
                            for (String containerID : containerIDs) {
                                Container container = findContainerByID(containerID, containers);
                                if (container != null) {
                                    vehicle.getContainers().add(container);
                                } else {
                                    new ArrayList<>();
                                }
                            }
                        }
                    }
                    vehicles.add(vehicle);
                    port.getVehicles().add(vehicle);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
