package rmit.cosc2081.groupassignment;

import java.io.*;
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

    public static Container removeContainer(ArrayList<Container> containers){
        Scanner scan = new Scanner(System.in);
        System.out.println("------------- Remove Container ----------------");
        System.out.println("Please enter Container ID: ");
        String containerID = scan.nextLine();
        Container container = findContainerByID(containerID, containers);
        if (container != null) {
            containers.remove(container);
            updateFileContainer(containers);
            System.out.println("The Container is removed successfully");
        } else {
            System.out.println("The Container not found");
        }
        return container;
    }

    public static void updateFileContainer(ArrayList<Container> containers) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("containers.txt"))) {
            for (Container container : containers) {
                    bufferedWriter.write(container.getId() + " " + container.getWeight() + " " + container.getType() + "\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Container findContainerByID(String containerID, ArrayList<Container> containers) {
        for (Container container : containers) {
            if (container.getId().equalsIgnoreCase(containerID)) {
                return container;
            }
        }
        return null;
    }

    public static Container createContainer(ArrayList<Container> containers) {
        Scanner scan = new Scanner(System.in);
        clearContainers(containers);
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
        if(container != null){
            writeToFileContainer(container);
        }
        return container;
    }

    public static void writeToFileContainer(Container container){
        try {
            FileWriter fileWriter = new FileWriter("containers.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(container.getId() + " " + container.getWeight() + " " + container.getType());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearContainers(ArrayList<Container> containers) {
        containers.removeIf(container -> container != null);
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


}
