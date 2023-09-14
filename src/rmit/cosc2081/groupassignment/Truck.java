package rmit.cosc2081.groupassignment;

import java.util.ArrayList;

public class Truck extends Vehicle{
    public Truck(String vehicleID, String vehicleName, double fuelAmount, double carryingCapability, double fuelCapability, String currentPort, ArrayList<Container> containers) {
        super(vehicleID, vehicleName, fuelAmount, carryingCapability, fuelCapability, currentPort, containers);
    }

    public Truck() {
    }
}
