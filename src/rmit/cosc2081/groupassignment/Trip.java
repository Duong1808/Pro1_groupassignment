package rmit.cosc2081.groupassignment;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import static rmit.cosc2081.groupassignment.Port.findPortByID;
import static rmit.cosc2081.groupassignment.UserList.*;
import static rmit.cosc2081.groupassignment.Vehicle.findVehicleByID;


public class Trip implements TripInterface{
    private String tripID;
    private Vehicle vehicle;
    private String departureDate;
    private String arrivalDate;
    private Port departurePort;
    private Port arrivalPort;
    private String status;

    public Trip(String tripID, Vehicle vehicle, String departureDate, String arrivalDate, Port departurePort, Port arrivalPort, String status) {
        this.tripID = tripID;
        this.vehicle = vehicle;
        setDepartureDate(departureDate);
        setArrivalDate(arrivalDate);
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.status = status;
    }

    public Trip() {
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Port getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort(Port departurePort) {
        this.departurePort = departurePort;
    }

    public Port getArrivalPort() {
        return arrivalPort;
    }

    public void setArrivalPort(Port arrivalPort) {
        this.arrivalPort = arrivalPort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
            return "Trip{" +
                    "Trip ID= "+ tripID +
                    "vehicle= " + vehicle +
                    ", departureDate=" + getDepartureDate() +
                    ", arrivalDate=" + getArrivalDate() +
                    ", departurePort=" + departurePort.getPortID() +
                    ", arrivalPort=" + arrivalPort.getPortID() +
                    ", status='" + status + '\'' +
                    '}';
    }

    public static Trip updateTripInformation(ArrayList<Trip> trips, ArrayList<Vehicle> vehicles, ArrayList<Port> ports) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("================= Update Trip ==================");
        System.out.println("Enter the Trip ID to update that Trip: ");
        String tripID = scanner.nextLine();
        Trip trip = findTripByID(tripID, trips);

        if (trip == null) {
            System.out.println(YELLOW_TEXT + BLACK_BG + "Trip not found. Update failed." + RESET);
            return null;
        }

        System.out.print("Enter the ID of the new vehicle (must start with 'sh' or 'tr'): ");
        String newVehicleID = scanner.nextLine();
        Vehicle newVehicle = findVehicleByID(newVehicleID, vehicles);

        if (newVehicle == null) {
            System.out.println(YELLOW_TEXT + BLACK_BG + "Vehicle not found. Update failed." + RESET);
            return null;
        }

        System.out.print("Enter the ID of the new departure port (must start with 'P'): ");
        String newDeparturePortID = scanner.nextLine();
        Port newDeparturePort = findPortByID(newDeparturePortID, ports);

        if (newDeparturePort == null) {
            System.out.println(YELLOW_TEXT + BLACK_BG + "Departure port not found. Update failed." + RESET);
            return null;
        }

        System.out.print("Enter the ID of the new arrival port (must start with 'P'): ");
        String newArrivalPortID = scanner.nextLine();
        Port newArrivalPort = findPortByID(newArrivalPortID, ports);

        if (newArrivalPort == null) {
            System.out.println(YELLOW_TEXT + BLACK_BG + "Arrival port not found. Update failed."+ RESET);
            return null;
        }
        if (!isPortAccessibleByVehicle(newVehicle, newDeparturePort) || !isPortAccessibleByVehicle(newVehicle, newArrivalPort)) {
            System.out.println(YELLOW_TEXT + BLACK_BG + "Vehicle cannot access one or both of the specified ports."+ RESET);
            return null;
        }

        System.out.print("Enter the new departure date (dd-mm-yyyy): ");
        String newDepartureDate = scanner.nextLine();

        System.out.print("Enter the new arrival date (dd-mm-yyyy): ");
        String newArrivalDate = scanner.nextLine();

        if (!isDateBefore(newDepartureDate, newArrivalDate)) {
            System.out.println(YELLOW_TEXT + BLACK_BG + "Departure date must be before the arrival date." + RESET);
            return null;
        }

        if (newDeparturePort.getPortID().equalsIgnoreCase(newArrivalPort.getPortID())) {
            System.out.println(YELLOW_TEXT + BLACK_BG + "Departure port cannot be the same as the arrival port." + RESET);
            return null;
        }

        trip.setVehicle(newVehicle);
        trip.setDeparturePort(newDeparturePort);
        trip.setArrivalPort(newArrivalPort);
        trip.setDepartureDate(newDepartureDate);
        trip.setArrivalDate(newArrivalDate);
        System.out.println("=================== END ====================");
        return trip;
    }

    public static Trip findTripByID(String tripID, ArrayList<Trip> trips) {
        for (Trip trip : trips) {
            if (trip.getTripID().equalsIgnoreCase(tripID)) {
                return trip;
            }
        }
        return null;
    }
    private static boolean isPortAccessibleByVehicle(Vehicle vehicle, Port port) {
        if (port.isLandingAbility()) {
            return true;
        } else {
            return vehicle instanceof Ship;
        }
    }

    private static boolean isDateBefore(String date1, String date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date parsedDate1 = dateFormat.parse(date1);
            Date parsedDate2 = dateFormat.parse(date2);
            return parsedDate1.before(parsedDate2);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Trip createTrip(ArrayList<Port> ports, ArrayList<Vehicle> vehicles) {
        Scanner scan = new Scanner(System.in);
        System.out.println("================= Add New Trip ==================");
        Vehicle vehicle = null;
        Port departurePort = null;
        Port arrivalPort = null;

        String tripID = generateTripID();

        do {
            System.out.println("Please enter the Vehicle ID: ");
            String vehicleID = scan.nextLine();
            vehicle = findVehicleByID(vehicleID, vehicles);
            if (vehicle == null) {
                System.out.println(YELLOW_TEXT + BLACK_BG + "Vehicle not found. Please Check Vehicle ID." + RESET);
            }
        } while (vehicle == null);

        do {
            System.out.println("Please enter the Port Departure ID: ");
            String portDepartureID = scan.nextLine();
            departurePort = findPortByID(portDepartureID, ports);
            if (departurePort == null) {
                System.out.println(YELLOW_TEXT + BLACK_BG + "Departure Port not found. Please Check Port Departure ID." + RESET);
            }
        } while (departurePort == null);

        do {
            System.out.println("Please enter the Port Arrival ID: ");
            String portArrivalID = scan.nextLine();
            arrivalPort = findPortByID(portArrivalID, ports);
            if (arrivalPort == null) {
                System.out.println(YELLOW_TEXT + BLACK_BG + "Arrival Port not found. Please Check Port Arrival ID." + RESET);
            }
        } while (arrivalPort == null);

        System.out.println("Please enter Departure Date (dd-mm-yyyy): ");
        String departureDate = scan.nextLine();
        System.out.println("Please enter Arrival Date (dd-mm-yyyy): ");
        String arrivalDate = scan.nextLine();
        System.out.println("==================== END =====================");
        return new Trip(tripID,vehicle, departureDate, arrivalDate, departurePort, arrivalPort, "INPROGRESS");
    }

    public static String generateTripID() {
        Random random = new Random();
        int randomNumber = random.nextInt(90000) + 10000;
        String tripID = "T" + randomNumber;
        return tripID;
    }

    public static void clearTrips(ArrayList<Trip> trips) {
        trips.removeIf(trip -> trip != null);
    }
    public static void writeToFileTrip(Trip trip) {
        try {
            FileWriter fileWriter = new FileWriter("trips.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(trip.getTripID() + " " + trip.getVehicle().getVehicleID() + " " +
                    trip.getDepartureDate() + " " + trip.getArrivalDate() + " " +
                    trip.getDeparturePort().getPortID() + " " + trip.getArrivalPort().getPortID() + " " +
                    trip.getStatus());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateFileTrip(ArrayList<Trip> trips) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("trips.txt"))) {
            for (Trip trip : trips) {
                bufferedWriter.write(trip.getTripID() + " " + trip.getVehicle().getVehicleID() + " " +
                        trip.getDepartureDate() + " " + trip.getArrivalDate() + " " +
                        trip.getDeparturePort().getPortID() + " " + trip.getArrivalPort().getPortID() + " " +
                        trip.getStatus() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readTripFromFile(String filename, ArrayList<Trip> trips, ArrayList<Vehicle> vehicles, ArrayList<Port> ports) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 7) {
                    String tripID = parts[0];
                    String vehicleID = parts[1];
                    String dateDeparture = parts[2];
                    String dateArrival = parts[3];
                    String portDepartureID = parts[4];
                    String portArrivalID = parts[5];
                    String status = parts[6];

                    Vehicle vehicle = findVehicleByID(vehicleID, vehicles);
                    Port portDeparture = findPortByID(portDepartureID, ports);
                    Port portArrival = findPortByID(portArrivalID, ports);

                    if (vehicle != null && portDeparture != null && portArrival != null) {
                        Trip trip = new Trip(tripID, vehicle, dateDeparture, dateArrival, portDeparture, portArrival, status);
                        trips.add(trip);
                        portDeparture.getTrips().add(trip);
                        portArrival.getTrips().add(trip);
                    }
                }
            }
            bufferedReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

interface TripInterface {
    String getTripID();
    Vehicle getVehicle();
    String getDepartureDate();
    String getArrivalDate();
    Port getDeparturePort();
    Port getArrivalPort();
    String getStatus();

    void setTripID(String tripID);
    void setVehicle(Vehicle vehicle);
    void setDepartureDate(String departureDate);
    void setArrivalDate(String arrivalDate);
    void setDeparturePort(Port departurePort);
    void setArrivalPort(Port arrivalPort);
    void setStatus(String status);
}

interface TripManagement {
    ArrayList<Trip> getTripsBetweenDates(ArrayList<Trip> trips);
    boolean isDateInRange(String dateString, String rangeStart, String rangeEnd);
    void calculateTotalFuelConsumptionInADay(ArrayList<Trip> trips);
    ArrayList<Trip> getTripsForDay(ArrayList<Trip> trips);
}
