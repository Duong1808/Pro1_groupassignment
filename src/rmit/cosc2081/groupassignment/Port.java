package rmit.cosc2081.groupassignment;
import java.util.ArrayList;

public class Port {
    private String ID;
    private String portName;
    private double latitude;
    private double longitude;
    private double storingCapability;
    private double landingCapability;
    private boolean marked;
    private int numVehicles;
    private int numContainers;
    private int numTrips;
    private ArrayList<Trip> trips;

    public Port(String ID, String portName, double latitude, double longitude,
                double storingCapability, double landingCapability, boolean marked, int numVehicles,
                int numContainers, int numTrips,
                ArrayList<Trip> trips) {
        this.ID = ID;
        this.portName = portName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapability = storingCapability;
        this.landingCapability = landingCapability;
        this.numVehicles = numVehicles;
        this.numContainers = numContainers;
        this.numTrips = numTrips;
        this.marked = false;
        this.trips = trips;

    }

    public String getID() {
        return ID;
    }


    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getStoringCapability() {
        return storingCapability;
    }

    public void setStoringCapability(double storingCapability) {
        this.storingCapability = storingCapability;
    }

    public double getLandingCapability() {
        return landingCapability;
    }

    public void setLandingCapability(double landingCapability) {
        this.landingCapability = landingCapability;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public int getNumVehicles() {
        return numVehicles;
    }

    public void setNumVehicles(int numVehicles) {
        this.numVehicles = numVehicles;
    }

    public int getNumContainers() {
        return numContainers;
    }

    public void setNumContainers(int numContainers) {
        this.numContainers = numContainers;
    }

    public int getNumTrips() {
        return numTrips;
    }

    public void setNumTrips(int numTrips) {
        this.numTrips = numTrips;
    }


    public ArrayList<Trip> getTrips() {
        return trips;
    }

    @Override
    public String toString() {
        return "Port{" +
                "ID='" + ID + '\'' +
                ", portName='" + portName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", storingCapability=" + storingCapability +
                ", landingCapability=" + landingCapability +
                ", marked=" + marked +
                ", numVehicles=" + numVehicles +
                ", numContainers=" + numContainers +
                ", numTrips=" + numTrips +
                ", trips=" + trips +
                '}';
    }

    public void setTrips(ArrayList<Trip> trips) {
        this.trips = trips;
    }

    public void showPortInformation(ArrayList<Trip> trips, ArrayList<Vehicle> vehicles) {
        System.out.println(toString());
        for (Trip trip : trips
        ) {
            trip.showTripInformation(vehicles);
        }
    }

}