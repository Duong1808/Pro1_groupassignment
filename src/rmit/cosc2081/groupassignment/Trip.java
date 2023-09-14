package rmit.cosc2081.groupassignment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Trip {
    private String tripID;
    private ArrayList<Vehicle> vehicles;
    private String status;
    private int numberOfship;
    private int numberOftruck;
    private Date dateDeparture;
    private String portDeparture;
    private String portArrival;


    public Trip(String tripID, ArrayList<Vehicle> vehicles, String status, int numberOfship, int numberOftruck,
                Date dateDeparture, String portDeparture, String portArrival) {
        this.tripID = tripID;
        this.vehicles = vehicles;
        this.status = status;
        this.numberOfship = numberOfship;
        this.numberOftruck = numberOftruck;
        setDateDeparture(String.valueOf(dateDeparture));
        this.portDeparture = portDeparture;
        this.portArrival = portArrival;
    }

    public Trip() {
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumberOfship() {
        return numberOfship;
    }

    public void setNumberOfship(int numberOfship) {
        this.numberOfship = numberOfship;
    }

    public int getNumberOftruck() {
        return numberOftruck;
    }

    public void setNumberOftruck(int numberOftruck) {
        this.numberOftruck = numberOftruck;
    }

    public Date getDateDeparture() {
        return dateDeparture;
    }

    public void setDateDeparture(String dateDeparture) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date date = dateFormat.parse(dateDeparture);
        } catch (ParseException e) {
            System.out.println("Invalid date format");
            e.printStackTrace();
        }
    }

    public String getPortDeparture() {
        return portDeparture;
    }

    public void setPortDeparture(String portDeparture) {
        this.portDeparture = portDeparture;
    }

    public String getPortArrival() {
        return portArrival;
    }

    public void setPortArrival(String portArrival) {
        this.portArrival = portArrival;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripID='" + tripID + '\'' +
                ", vehicles=" + vehicles +
                ", status='" + status + '\'' +
                ", numberOfship=" + numberOfship +
                ", numberOftruck=" + numberOftruck +
                ", dateDeparture=" + dateDeparture +
                ", portDeparture=" + portDeparture +
                ", portArrival=" + portArrival;
    }

    public void showTripInformation(ArrayList<Vehicle> vehicles) {
        System.out.println(toString());
        System.out.println(" Containers Of This Trip = ");
        for (Vehicle vehicle : vehicles) {
            vehicle.showContainers();
        }
    }

}
