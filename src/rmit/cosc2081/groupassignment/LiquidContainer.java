package rmit.cosc2081.groupassignment;

public class LiquidContainer extends Container {
    public LiquidContainer(String id, double weight) {
        super(id, weight);
        setType("liquid");
    }

    @Override
    public double getShipFuelConsumptionPerKm() {
        return 4.8;
    }

    @Override
    public double getTruckFuelConsumptionPerKm() {
        return 5.3;
    }
}
