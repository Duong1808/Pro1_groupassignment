package rmit.cosc2081.groupassignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Container {
    private String type;
    private String containerID;
    private double weight;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Container(String containerID, double weight) {
        this.containerID = containerID;
        this.weight = weight;
    }

    public Container() {

    }

    public String getId() {
        return containerID;
    }

    public double getWeight() {
        return weight;
    }


    public void setContainerID(String containerID) {
        this.containerID = containerID;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double calculateShipFuelConsumption() {
        return weight * getShipFuelConsumptionPerKm();
    }

    public double calculateTruckFuelConsumption() {
        return weight * getTruckFuelConsumptionPerKm();
    }

    public double getShipFuelConsumptionPerKm() {
        return 0.0;
    }

    public double getTruckFuelConsumptionPerKm() {
        return 0.0;
    }

    public static Container createContainer(ArrayList<Container> containers) {
        Scanner scan = new Scanner(System.in);
        readContainersFromFile("containers.txt",containers);
        String containerID;
        while (true) {
            System.out.println("Please input the Container ID: ");
            containerID = scan.nextLine().toLowerCase();
            if (!containerExisted(containers, containerID)) {
                break;
            } else {
                System.out.println("Container ID already exists. Please choose a different ID.");
            }
        }
        System.out.println("Enter the weight of the container: ");
        double weight = Double.parseDouble(scan.nextLine());

        System.out.println("Select the container type:");
        System.out.println("1. Dry storage");
        System.out.println("2. Open top");
        System.out.println("3. Open side");
        System.out.println("4. Refrigerated");
        System.out.println("5. Liquid");
        int containerType = Integer.parseInt(scan.nextLine());
        Container container = null;
        switch (containerType) {
            case 1 -> container = new DryStorageContainer(containerID, weight);
            case 2 -> container = new OpenTopContainer(containerID, weight);
            case 3 -> container = new OpenSideContainer(containerID, weight);
            case 4 -> container = new RefrigeratedContainer(containerID, weight);
            case 5 -> container = new LiquidContainer(containerID, weight);
            default -> System.out.println("Invalid container type selection.");
        }
        return container;
    }

    public static boolean containerExisted(ArrayList<Container> containers, String containerID) {
        String lowercaseUsername = containerID.toLowerCase();
        return containers.stream().anyMatch(container -> container.getId().toLowerCase().equals(lowercaseUsername));
    }
    public static void readContainersFromFile(String filename, ArrayList<Container> containers) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                Container container = null;
                String[] parts = line.split(" ");
                if (parts.length >= 3) {
                    String containerID = parts[0];
                    double weight = Double.parseDouble(parts[1]);
                    String type = parts[2];
                    if(type.equalsIgnoreCase("dryStorage")){
                         container = new DryStorageContainer(containerID, weight);
                    } else if (type.equalsIgnoreCase("liquid")) {
                         container = new LiquidContainer(containerID, weight);
                    } else if (type.equalsIgnoreCase("openSide")) {
                         container = new OpenSideContainer(containerID, weight);
                    } else if (type.equalsIgnoreCase("openTop")) {
                         container = new OpenTopContainer(containerID, weight);
                    }
                    else if (type.equalsIgnoreCase("refrigerated")) {
                         container = new RefrigeratedContainer(containerID, weight);
                    }
                    containers.add(container);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Container{" +
                "containerID='" + containerID + '\'' +
                ", weight=" + weight +
                ", type= " + getType() +
                '}';
    }

    public static void main(String[] args) {
        ArrayList<Container> containers = new ArrayList<>();

        Container container1 = Container.createContainer(containers);
        containers.add(container1);
        for(Container con : containers){
            System.out.println(con.toString());
        }
    }
}
