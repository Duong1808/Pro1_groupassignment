package rmit.cosc2081.groupassignment;

import java.util.ArrayList;

public class BasicTruck extends Truck{
    public BasicTruck(String vehicleID, String vehicleName, double fuelAmount, double carryingCapability, double fuelCapability, Port currentPort, ArrayList<Container> containers) {
        super(vehicleID, vehicleName, fuelAmount, carryingCapability, fuelCapability, currentPort, containers);
        setVehicleType("Basic");
    }

    public BasicTruck() {
    }
}
