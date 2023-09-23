package rmit.cosc2081.groupassignment;

public class OpenTopContainer extends Container{
    public OpenTopContainer(String id, double weight) {
        super(id, weight);
        setType("openTop");
    }

    @Override
    public double getShipFuelConsumptionPerKm() {
        return 2.8;
    }

    @Override
    public double getTruckFuelConsumptionPerKm() {
        return 3.2;
    }
}
