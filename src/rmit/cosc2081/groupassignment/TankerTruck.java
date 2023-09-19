package rmit.cosc2081.groupassignment;

import java.util.ArrayList;

public class TankerTruck extends Truck{
    public TankerTruck(String vehicleID, String vehicleName, double fuelAmount, double carryingCapability, double fuelCapability, Port currentPort, ArrayList<Container> containers) {
        super(vehicleID, vehicleName, fuelAmount, carryingCapability, fuelCapability, currentPort, containers);
        setVehicleType("Tanker");
    }

    public TankerTruck() {
    }
}
