/*
package Models;

public class portManagement {
    private Port port;
    private Trip trip;
    private Ship ship;
    private Container container;
    private int numTruck;
    private int numShip;
    private int numContainers;
    private int numTrips;

    public portManagement(){
    };
    public portManagement(Port port){
    };
    public portManagement(Port port, Trip trip, Container container,
                          int numShip, int numTruck, int numContainers, int numTrips) {
        this.port = port;
        this.trip = trip;
        this.container = container;
        this.numTruck = numTruck;
        this.numShip = numShip;
        this.numContainers = numContainers;
        this.numTrips = numTrips;
    }


    public double calculateDistance(double lat1,double long1, double lat2, double long2) {
        double R = 6371.0; // Earth's radius in kilometers

        double lat1Rad = Math.toRadians(lat1);
        double long1Rad = Math.toRadians(long1);
        double lat2Rad = Math.toRadians(lat2);
        double long2Rad = Math.toRadians(long2);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = long2Rad - long1Rad;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c;
        return distance;
    }
    public void getDistance(double distance){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        System.out.println(decimalFormat.format(distance));
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public int getNumTruck() {
        return numTruck;
    }

    public void setNumTruck(int numTruck) {
        this.numTruck = numTruck;
    }

    public int getNumShip() {
        return numShip;
    }

    public void setNumShip(int numShip) {
        this.numShip = numShip;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
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


}
*/
