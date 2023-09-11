/*
package Models;

import java.util.List;
import java.util.Objects;

public class Vehicle {
    private String vehicleID;
    private String Name;
    private Double currentFuel;
    private Double carryingCapability;
    private Double fuelCapacity;

    private Port currentPort;
    private int numOfcontainers;
    private List<Container> containers;

    public Vehicle(String vehicleID, String Name, Double currentFuel, Double carryingCapability, Double fuelCapacity, Port currentPort, int numOfcontainers, List<Container> containers) {
        this.vehicleID = vehicleID;
        this.Name = Name;
        this.currentFuel = currentFuel;
        this.carryingCapability = carryingCapability;
        this.fuelCapacity = fuelCapacity;
        this.currentPort = currentPort;
        this.numOfcontainers = numOfcontainers;
        this.containers = containers;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Double getCurrentFuel() {
        return currentFuel;
    }

    public void setCurrentFuel(Double currentFuel) {
        this.currentFuel = currentFuel;
    }

    public Double getCarryingCapability() {
        return carryingCapability;
    }

    public void setCarryingCapability(Double carryingCapability) {
        this.carryingCapability = carryingCapability;
    }

    public Double getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(Double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(Port currentPort) {
        this.currentPort = currentPort;
    }

    public int getNumOfcontainers() {
        return numOfcontainers;
    }

    public void setNumOfcontainers(int numOfcontainers) {
        this.numOfcontainers = numOfcontainers;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    public boolean loadContainer(Container container){
        if (currentPort == null){
            System.out.println("Vehicle doesn't belong to the port");
            return false;
        }
        if ()
        double currentTotalWeight = calculateTotalContainerWeight();
        if (currentTotalWeight + container.getWeight() <= carryingCapability){
            containers.add(container);
        }
        else
            System.out.println("Weight is over the limit");
    }

    public void unloadContainer(Container container){

    }

    public abstract boolean canMoveToPort(Port port);

    public void moveToPort(Port port){}

    public void refuel(){
        this.currentFuel = this.fuelCapacity;
        System.out.println("Full tank now");
    }

    public void calculateFuelConsumption(){}
    public void updateContainerCount(){}
    public double calculateTotalContainerWeight(){
    }
    public boolean canCarryContainerType(){}
}
*/
