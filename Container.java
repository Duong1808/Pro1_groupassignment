package Models;

import java.util.Scanner;
import java.util.UUID;
import java.util.HashMap;

public class Container {
    private String ID;
    private Double Weight;
    private String type;
    private static HashMap<String, Container> allContainers = new HashMap<>();
    public Container(double Weight, String type) {
        this.ID = "c-" + UUID.randomUUID().toString();
        this.Weight = Weight;
        this.type = type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Double getWeight() {
        return Weight;
    }

    public void setWeight(Double weight) {
        Weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getFuelConsumptionPerKm(String vehicleType) {
        double fuelPerKm = 0.0;
        if (vehicleType.equals("Ship")) {
            switch (type) {
                case "Dry storage":
                    fuelPerKm = 3.5;
                    break;
                case "Open top":
                    fuelPerKm = 2.8;
                    break;
                case "Open side":
                    fuelPerKm = 2.7;
                    break;
                case "Refrigerated":
                    fuelPerKm = 4.5;
                    break;
                case "Liquid":
                    fuelPerKm = 4.8;
                    break;
                default:
                    System.out.println("Invalid");
                    break;
            }
        } else if (vehicleType.equals("Truck")) {
            switch (type) {
                case "Dry storage":
                    fuelPerKm = 4.6;
                    break;
                case "Open top":
                    fuelPerKm = 3.2;
                    break;
                case "Open side":
                    fuelPerKm = 3.2;
                    break;
                case "Refrigerated":
                    fuelPerKm = 5.4;
                    break;
                case "Liquid":
                    fuelPerKm = 5.3;
                    break;
                default:
                    System.out.println("Invalid");
                    break;
            }
        }
        else{
            System.out.println("Invalid vehicle type");}
        return fuelPerKm;
    }

    public static void createContainer(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter container weight: ");
        Double conWeight = sc.nextDouble();
        System.out.println("Enter container type: ");
        String conType = sc.next();
        Container newContainer = new Container(conWeight, conType);
        allContainers.put(newContainer.ID, newContainer);
        System.out.println("Sucessfully created: " + newContainer.ID);
    }

    public static void updateContainer(){
        System.out.println("enter container ID: ");
        Scanner scanner = new Scanner(System.in);
        String ID = scanner.next();
        if (allContainers.containsKey(ID)) {
            System.out.println("Enter container weight: ");
            Double conWeight = scanner.nextDouble();
            System.out.println("Enter container type: ");
            String conType = scanner.next();
            Container existingContainer = allContainers.get(ID);
            existingContainer.type = conType;
            existingContainer.Weight = conWeight;
            System.out.println("Container type updated: " + existingContainer.type + " Container weight updated: " + existingContainer.Weight);
        } else {
            System.out.println("It doesn't contain the container ID: " + ID);
        }
    }

    public static void deleteContainer(){
        System.out.println("enter container ID: ");
        Scanner scanner = new Scanner(System.in);
        String ID = scanner.next();
        if (allContainers.containsKey(ID)) {
            allContainers.remove(ID);
            System.out.println("Deleted");
        } else {
            System.out.println("It doesn't contain the container ID: " + ID);
        }
    }


    public static void containerMenu(){
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        while (!exit){
            System.out.println(" 1. Create container \n 2. Read container \n 3. Update container \n 4. Delete container \n 5. Exit ");
            System.out.println("Enter option: ");
            int option = sc.nextInt();
            switch (option){
                case 1:
                    createContainer();
                    break;
                case 2:
                    break;
                case 3:
                    updateContainer();
                    break;
                case 4:
                    deleteContainer();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("invalid");
                    break;
            }
        }
    }


    public static void main(String[] args) {
        containerMenu();

    }
}


