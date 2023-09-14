package rmit.cosc2081.groupassignment;

import java.util.ArrayList;
import java.util.Scanner;

public class Vehicle {
    private String vehicleID;
    private String vehicleName;
    private double fuelAmount;
    private double carryingCapability;
    private double fuelCapability;
    private String currentPort;
    private ArrayList<Container> containers;
    private int numCons;

    public Vehicle(String vehicleID, String vehicleName, double fuelAmount, double carryingCapability, double fuelCapability, String currentPort, ArrayList<Container> containers) {
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

    public String getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(String currentPort) {
        this.currentPort = currentPort;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public void setContainers(ArrayList<Container> containers) {
        this.containers = containers;
    }

    public static Vehicle createVehicle(ArrayList<Container> containers){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the vehicle ID: ");
        String vehicleID = scan.nextLine();

        System.out.println("Enter the vehicle name: ");
        String vehicleName = scan.nextLine();

        System.out.println("Enter the fuel amount: ");
        double fuelAmount = Double.parseDouble(scan.nextLine());

        System.out.println("Enter the carrying capability: ");
        double carryingCapability = Double.parseDouble(scan.nextLine());

        System.out.println("Enter the fuel capability: ");
        double fuelCapability = Double.parseDouble(scan.nextLine());

        System.out.println("Enter the current port: ");
        String currentPort = scan.nextLine();

        ArrayList<Container> initialContainers = new ArrayList<>();
        String addContainers;
        do {
            System.out.println("Do you want to add initial containers? (yes/no): ");
            addContainers = scan.nextLine();
            if (addContainers.equalsIgnoreCase("yes")) {
                System.out.println("Please enter container ID: ");
                String containerID = scan.nextLine();
                for(Container con : containers){
                    if(con.getId().equalsIgnoreCase(containerID)){
                        initialContainers.add(con);
                    } else {
                        System.out.println("not existed ID");
                    }
                }
            }
        } while (!addContainers.equalsIgnoreCase("no"));
        return new Vehicle(vehicleID, vehicleName, fuelAmount, carryingCapability, fuelCapability, currentPort, initialContainers);
    }


    public void showContainers(){
        for(Container con : this.getContainers()){
            System.out.println(con.toString());
        }
    }


    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleName='" + vehicleName + '\'' +
                ", fuelAmount=" + fuelAmount +
                ", carryingCapability=" + carryingCapability +
                ", fuelCapability=" + fuelCapability +
                ", currentPort='" + currentPort + '\'' +
                '}';
    }

    public static void main(String[] args) {

//            Container container1 = Container.createContainer();

//            System.out.println(container1.toString());
//        System.out.println(container1.getType());
//        System.out.println(container1.getTruckFuelConsumptionPerKm());
//        System.out.println(container1.getShipFuelConsumptionPerKm());
//        System.out.println(container1.calculateShipFuelConsumption());
//        System.out.println(container1.calculateTruckFuelConsumption());
//            Vehicle vehicle1 = createVehicle(containers);
//            ArrayList<Vehicle> vehicles = new ArrayList<>();
//            vehicles.add(vehicle1);
//            for(Vehicle veh : vehicles){
//                System.out.println(veh.toString());
//                veh.showContainers();
//            }
//        Trip trip2 = new Trip("tr02",vehicles,"delivered",1,1,"22/09/2023","22/10/2023","19/09/2023");
//        Trip trip1 = new Trip("tr01",vehicles,"delivered",1,1,"22/09/2023","22/10/2023","19/09/2023");
//        trips.add(trip1);
//        trips.add(trip2);
    }
}
