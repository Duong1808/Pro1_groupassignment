package rmit.cosc2081.groupassignment;

public class RefrigeratedContainer extends Container {
    public RefrigeratedContainer(String id, double weight) {
        super(id, weight);
        setType("refrigerated");
    }

    @Override
    public double getShipFuelConsumptionPerKm() {
        return 4.5;
    }

    @Override
    public double getTruckFuelConsumptionPerKm() {
        return 5.4;
    }
}
