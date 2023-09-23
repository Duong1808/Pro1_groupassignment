package rmit.cosc2081.groupassignment;


public class OpenSideContainer extends Container {
    public OpenSideContainer(String id, double weight) {
        super(id, weight);
        setType("openSide");
    }

    @Override
    public double getShipFuelConsumptionPerKm() {
        return 2.7;
    }

    @Override
    public double getTruckFuelConsumptionPerKm() {
        return 3.2;
    }
}
