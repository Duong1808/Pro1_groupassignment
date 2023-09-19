package rmit.cosc2081.groupassignment;

import java.util.ArrayList;

public class ReeferTruck extends Truck{
    public ReeferTruck(String vehicleID, String vehicleName, double fuelAmount, double carryingCapability, double fuelCapability, Port currentPort, ArrayList<Container> containers) {
        super(vehicleID, vehicleName, fuelAmount, carryingCapability, fuelCapability, currentPort, containers);
        setVehicleType("Reefer");
    }

    public ReeferTruck() {
    }
}
