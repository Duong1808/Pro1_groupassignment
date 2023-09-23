package rmit.cosc2081.groupassignment;

public class DryStorageContainer extends Container{
    public DryStorageContainer(String id, double weight) {
        super(id, weight);
        setType("dryStorage");
    }

    @Override
    public double getShipFuelConsumptionPerKm() {
        return 3.5;
    }

    @Override
    public double getTruckFuelConsumptionPerKm() {
        return 4.6;
    }

}
