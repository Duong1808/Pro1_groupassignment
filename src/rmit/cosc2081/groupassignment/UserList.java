package rmit.cosc2081.groupassignment;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static rmit.cosc2081.groupassignment.Port.*;
import static rmit.cosc2081.groupassignment.Trip.updateFileTrip;
import static rmit.cosc2081.groupassignment.Trip.writeToFileTrip;
import static rmit.cosc2081.groupassignment.Vehicle.findVehicleByID;
import static rmit.cosc2081.groupassignment.Vehicle.updateFileVehicle;

public class UserList implements ContainerManagement, PortManagement, TripManagement, ManagerModifiable, AuthenticationConfigurable{
    //1. attribute
    private ArrayList<PortManager> listOfManagers;
    //2. constructor

    public UserList() {
        listOfManagers = new ArrayList<>();
    }

    //3. get, set methods

    public ArrayList<PortManager> getListOfManagers() {
        return listOfManagers;
    }

    public void setListOfManagers(ArrayList<PortManager> listOfManagers) {
        this.listOfManagers = listOfManagers;
    }

    //4. input, output
    private void printAdminMenu() {
        System.out.println("1. Add New Port Manager");
        System.out.println("2. View All Port Managers");
        System.out.println("3. Remove Port Manager By Username");
        System.out.println("4. Show Container Menu");
        System.out.println("5. Show Vehicle Menu");
        System.out.println("6. Show Port Menu");
        System.out.println("7. Show Trip Menu");
        System.out.println("0. Exit");
    }

    private void printManagerMenu() {
        System.out.println("1. Show Container Menu");
        System.out.println("2. Show List Of Vehicle Of Manager");
        System.out.println("3. List Ship(s) In Manager Port");
        System.out.println("4. List Trip(s) For A Day");
        System.out.println("5. List Trip(s) From Day A To Day B");
        System.out.println("6. Load Container On A Vehicle In Manager Port");
        System.out.println("7. Unload Container On A Vehicle In Manager Port");
        System.out.println("8. Move To Port");
        System.out.println("0. Exit");
    }

    private void printContainerMenu() {
        System.out.println("1. Add New Container");
        System.out.println("2. View All Containers");
        System.out.println("3. Remove Container By ID");
        System.out.println("4. Calculate Weight Of Each Container Type");
        System.out.println("0. Exit Container Menu");
    }

    private void printVehicleMenu() {
        System.out.println("1. Add New Vehicle");
        System.out.println("2. View All Vehicle");
        System.out.println("3. Remove Vehicle By ID");
        System.out.println("4. Load Container To Vehicle");
        System.out.println("5. Unload Container From Vehicle");
        System.out.println("6. Move To Port");
        System.out.println("7. Refuel Vehicle");
        System.out.println("0. Exit Vehicle Menu");
    }

    private void printPortMenu() {
        System.out.println("1. Add New Port");
        System.out.println("2. View All Ports");
        System.out.println("3. Remove Port By ID");
        System.out.println("4. List All Ships In A Port");
        System.out.println("5. List All Trucks In A Port");
        System.out.println("0. Exit Vehicle Menu");
    }

    private void printTripMenu() {
        System.out.println("1. Add New Trip");
        System.out.println("2. View All Trips");
        System.out.println("3. View Trips By Given Day");
        System.out.println("4. View Trips By From Day A to Day B");
        System.out.println("5. Update Trip Information");
        System.out.println("6. Calculate Fuel Consumption In A Day");
        System.out.println("0. Exit Vehicle Menu");
    }

    public void showManagerTasks(String managerPortID, ArrayList<Port> ports, ArrayList<Container> containers, ArrayList<Trip> trips, ArrayList<Vehicle> vehicles) {
        Scanner scan = new Scanner(System.in);
        Port port = findPortByID(managerPortID, ports);

        if (port == null) {
            System.out.println("Port not found for ID: " + managerPortID);
            return;
        }

        boolean flag = true;
        do {
            printManagerMenu();
            System.out.println("Please select the following option >>>>>>>");
            int selected = Integer.parseInt(scan.nextLine());
            switch (selected) {
                case 1 -> showContainerMenu(containers);
                case 2 -> {
                    for (Vehicle vehicle : port.getVehicles()) {
                        System.out.println(vehicle.toString());
                    }
                }
                case 3 -> {
                    for (Vehicle vehicle : port.getVehicles()) {
                        if (vehicle instanceof Ship) {
                            System.out.println(vehicle.toString());
                        } else {
                            System.out.println("There is NO SHIP in this Port!!!");
                        }
                    }
                }
                case 4 -> {
                    ArrayList<Trip> tripsForDay = getTripsForDay(port.getTrips());
                    for (Trip trip : tripsForDay) {
                        System.out.println(trip.toString());
                    }
                }
                case 5 -> {
                    ArrayList<Trip> tripsBetweenDays = getTripsBetweenDates(port.getTrips());
                    for (Trip trip : tripsBetweenDays) {
                        System.out.println(trip.toString());
                    }
                }
                case 6 -> {
                    loadContainerToVehicle(port.getVehicles(), containers);
                    updateFileVehicle(vehicles);
                }
                case 7 -> {
                    unloadContainerFromVehicle(port.getVehicles());
                    updateFileVehicle(vehicles);
                }
                case 8 -> {
                    canMoveToPort(port.getVehicles(), ports, trips);
                    updateFilePort(ports);
                    updateFileVehicle(vehicles);
                    updateFileTrip(trips);
                }
                case 0 -> flag = false;
                default -> System.out.println("Please enter the valid option!!!");
            }
        } while (flag);
    }

    public void showAdminTasks(ArrayList<Trip> trips, ArrayList<Port> ports, ArrayList<Vehicle> vehicles, ArrayList<Container> containers) {
        Scanner scan = new Scanner(System.in);

        boolean flag = true;
        do {
            printAdminMenu();
            System.out.println("Please select the following option >>>>>>>");
            int selected = Integer.parseInt(scan.nextLine());
            switch (selected) {
                case 1 -> addPortManager(ports);
                case 2 -> showManagerList(ports);
                case 3 -> removePortManager();
                case 4 -> showContainerMenu(containers);
                case 5 -> showVehicleMenu(vehicles, containers, ports, trips);
                case 6 -> showPortMenu(ports);
                case 7 -> showTripMenu(trips, ports, vehicles);
                case 0 -> flag = false;
                default -> System.out.println("Please enter the valid option!!!");
            }
        } while (flag);
    }

    private void showVehicleMenu(ArrayList<Vehicle> vehicles, ArrayList<Container> containers, ArrayList<Port> ports, ArrayList<Trip> trips) {
        Scanner scan = new Scanner(System.in);
        boolean flag = true;
        do {
            printVehicleMenu();
            System.out.println("Please select the following option >>>>>>>");
            int selected = Integer.parseInt(scan.nextLine());
            switch (selected) {
                case 1 -> {
                    Vehicle vehicle = Vehicle.createVehicle(vehicles);
                }
                case 2 -> {
                    for (Vehicle vehicle : vehicles) {
                        System.out.println(vehicle.toString());
                    }
                }
                case 3 -> {
                    Vehicle vehicle = Vehicle.removeVehicle(vehicles);
                }
                case 4 -> {
                    loadContainerToVehicle(vehicles, containers);
                    updateFileVehicle(vehicles);
                }
                case 5 -> {
                    unloadContainerFromVehicle(vehicles);
                    updateFileVehicle(vehicles);
                }
                case 6 -> {
                    if (canMoveToPort(vehicles, ports, trips)) {
                        System.out.println("Move to Port Successfully");
                        updateFileVehicle(vehicles);
                        updateFilePort(ports);
                        updateFileTrip(trips);
                    } else {
                        System.out.println("Cannot Move to Port. Please check your input again!!!");
                    }
                }
                case 7 -> {
                    refuelVehicle(vehicles);
                    updateFileVehicle(vehicles);
                }
                case 0 -> flag = false;
                default -> System.out.println("Please enter the valid option!!!");
            }
        } while (flag);
    }

    private void showPortMenu(ArrayList<Port> ports) {
        Scanner scan = new Scanner(System.in);
        boolean flag = true;
        do {
            printPortMenu();
            System.out.println("Please select the following option >>>>>>>");
            int selected = Integer.parseInt(scan.nextLine());
            switch (selected) {
                case 1 -> {
                    Port port = Port.createPort(ports);
                    ports.add(port);
                    writeToFilePort(port);
                }
                case 2 -> {
                    for (Port port : ports) {
                        System.out.println(port.toString());
                    }
                }
                case 3 -> {
                    Port port = Port.removePort(ports);
                }
                case 4 -> {
                    listShipsInAPort(ports);
                }
                case 5 -> {
                    listTrucksInAPort(ports);
                }
                case 0 -> flag = false;
                default -> System.out.println("Please enter the valid option!!!");
            }
        } while (flag);
    }

    private void showTripMenu(ArrayList<Trip> trips, ArrayList<Port> ports, ArrayList<Vehicle> vehicles) {
        Scanner scan = new Scanner(System.in);
        boolean flag = true;
        do {
            printTripMenu();
            System.out.println("Please select the following option >>>>>>>");
            int selected = Integer.parseInt(scan.nextLine());
            switch (selected) {
                case 1 -> {
                    Trip trip = Trip.createTrip(ports, vehicles);
                    trips.add(trip);
                    writeToFileTrip(trip);
                }
                case 2 -> {
                    for (Trip trip : trips) {
                        System.out.println(trip.toString());
                    }
                }
                case 3 -> {
                    ArrayList<Trip> tripsInGivenDay = getTripsForDay(trips);
                    for (Trip trip : tripsInGivenDay) {
                        System.out.println(trip.toString());
                    }
                }
                case 4 -> {
                    ArrayList<Trip> tripsBetweenDates = getTripsBetweenDates(trips);
                    for (Trip trip : tripsBetweenDates) {
                        System.out.println(trip.toString());
                    }
                }
                case 5 -> {
                    Trip trip = Trip.updateTripInformation(trips, vehicles, ports);
                    updateFileTrip(trips);
                    updateFileVehicle(vehicles);
                    updateFilePort(ports);
                }
                case 6 -> {
                    calculateTotalFuelConsumptionInADay(trips);
                }
                case 0 -> flag = false;
                default -> System.out.println("Please enter the valid option!!!");
            }
        } while (flag);
    }

    private void showContainerMenu(ArrayList<Container> containers) {
        Scanner scan = new Scanner(System.in);
        boolean flag = true;
        do {
            printContainerMenu();
            System.out.println("Please select the following option >>>>>>>");
            int selected = Integer.parseInt(scan.nextLine());
            switch (selected) {
                case 1 -> {
                    Container container = Container.createContainer(containers);
                    containers.add(container);
                }
                case 2 -> {
                    for (Container container : containers) {
                        System.out.println(container.toString());
                    }
                }
                case 3 -> {
                    Container container = Container.removeContainer(containers);
                }
                case 4 -> {
                    calculateWeightEachContainerType(containers);
                }
                case 0 -> flag = false;
                default -> System.out.println("Please enter the valid option!!!");
            }
        } while (flag);
    }

    public ArrayList<Trip> getTripsBetweenDates(ArrayList<Trip> trips) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please Input dayA in format (dd-mm-year): ");
        String dayA = scan.nextLine();
        System.out.println("Please Input dayB in format (dd-mm-year): ");
        String dayB = scan.nextLine();
        ArrayList<Trip> tripsBetweenDates = new ArrayList<>();

        for (Trip trip : trips) {
            String departureDate = trip.getDepartureDate();
            String arrivalDate = trip.getArrivalDate();

            if (isDateInRange(departureDate, dayA, dayB) || isDateInRange(arrivalDate, dayA, dayB)) {
                tripsBetweenDates.add(trip);
            }
        }
        return tripsBetweenDates;
    }

    public boolean isDateInRange(String dateString, String rangeStart, String rangeEnd) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

            Date date = dateFormat.parse(dateString);
            Date startDate = dateFormat.parse(rangeStart);
            Date endDate = dateFormat.parse(rangeEnd);

            return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void calculateWeightEachContainerType(ArrayList<Container> containers) {
        double dryStorageContainer = 0.0;
        double openSideContainer = 0.0;
        double openTopContainer = 0.0;
        double liquidContainer = 0.0;
        double refrigeratedContainer = 0.0;
        for (Container container : containers) {
            if (container instanceof DryStorageContainer) {
                dryStorageContainer += container.getWeight();
            } else if (container instanceof OpenSideContainer) {
                openSideContainer += container.getWeight();
            } else if (container instanceof OpenTopContainer) {
                openTopContainer += container.getWeight();
            } else if (container instanceof LiquidContainer) {
                liquidContainer += container.getWeight();
            } else {
                refrigeratedContainer += container.getWeight();
            }
        }
        System.out.println("Total Weight of Dry Storage Containers: " + dryStorageContainer);
        System.out.println("Total Weight of Open Side Containers: " + openSideContainer);
        System.out.println("Total Weight of Open Top Containers: " + openTopContainer);
        System.out.println("Total Weight of Liquid Containers: " + liquidContainer);
        System.out.println("Total Weight of Refrigerated Containers: " + refrigeratedContainer);
    }

    public void listTrucksInAPort(ArrayList<Port> ports) {
        Scanner scan = new Scanner(System.in);
        String portID;
        Port port = null;

        do {
            System.out.println("Please input Port ID (must start with 'P'): ");
            portID = scan.nextLine();

            if (!portID.toLowerCase().startsWith("p")) {
                System.out.println("Invalid port ID format. Must start with 'P'!!!");
            } else {
                port = findPortByID(portID, ports);

                if (port == null) {
                    System.out.println("Port not found. Please enter a valid ID.");
                }
            }
        } while (port == null);
        if (!port.isLandingAbility()) {
            System.out.println("This Port Name " + port.getName() + " (ID: " + port.getPortID() + ") DOES NOT ALLOW ANY TRUCK");
        } else {
            System.out.println("Truck(s) in " + port.getName() + " (ID: " + port.getPortID() + "):");
            for (Vehicle vehicle : port.getVehicles()) {
                if (vehicle instanceof Truck) {
                    System.out.println(vehicle.toString());
                }
            }
        }
    }

    public void listShipsInAPort(ArrayList<Port> ports) {
        Scanner scan = new Scanner(System.in);
        String portID;
        Port port = null;

        do {
            System.out.println("Please input Port ID (must start with 'P'): ");
            portID = scan.nextLine();

            if (!portID.toLowerCase().startsWith("p")) {
                System.out.println("Invalid port ID format. Must start with 'P'!!!");
            } else {
                port = findPortByID(portID, ports);

                if (port == null) {
                    System.out.println("Port not found. Please enter a valid ID.");
                }
            }
        } while (port == null);

        System.out.println("Ships in " + port.getName() + " (ID: " + port.getPortID() + "):");
        for (Vehicle vehicle : port.getVehicles()) {
            if (vehicle instanceof Ship) {
                System.out.println(vehicle.toString());
            }
        }
    }

    public void calculateTotalFuelConsumptionInADay(ArrayList<Trip> trips) {
        ArrayList<Trip> tripsForDay = getTripsForDay(trips);
        double totalFuelConsumption = 0.0;

        for (Trip trip : tripsForDay) {
            Vehicle vehicle = trip.getVehicle();
            for (Container container : vehicle.getContainers()) {
                double containerFuelConsumption = 0.0;

                if (vehicle instanceof Ship) {
                    containerFuelConsumption = container.calculateShipFuelConsumption();
                } else if (vehicle instanceof Truck) {
                    containerFuelConsumption = container.calculateTruckFuelConsumption();
                }
                totalFuelConsumption += containerFuelConsumption;
            }
        }

        System.out.println("Total Fuel Consumption: " + totalFuelConsumption);
    }

    public ArrayList<Trip> getTripsForDay(ArrayList<Trip> trips) {
        ArrayList<Trip> tripsForDay = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String dateInput;
        boolean validDate = false;
        do {
            System.out.print("Enter a date (dd-MM-yyyy): ");
            dateInput = scanner.nextLine();
            for (Trip trip : trips) {
                if (trip.getDepartureDate().equals(dateInput)) {
                    tripsForDay.add(trip);
                    validDate = true;
                }
            }

            if (!validDate) {
                System.out.println("No trips found for the entered date.");
            }

        } while (!validDate);

        return tripsForDay;
    }

    public void refuelVehicle(ArrayList<Vehicle> vehicles) {
        Scanner scan = new Scanner(System.in);
        System.out.println("----------REFUEL VEHICLE----------");

        String vehicleID;
        Vehicle vehicle = null;
        do {
            System.out.println("Please input Vehicle ID (must start with 'sh' or 'tr'): ");
            vehicleID = scan.nextLine();
            if (!vehicleID.toLowerCase().startsWith("sh") && !vehicleID.toLowerCase().startsWith("tr")) {
                System.out.println("Invalid vehicle ID format. Must start with 'sh' or 'tr'!!!");
            } else {
                vehicle = findVehicleByID(vehicleID, vehicles);
                if (vehicle == null) {
                    System.out.println("Vehicle not found. Please enter a valid ID.");
                }
            }
        } while (vehicle == null);
        vehicle.setFuelAmount(vehicle.getFuelCapability());
        System.out.println("Vehicle is successfully refuel TO FULL.");
    }

    public boolean canMoveToPort(ArrayList<Vehicle> vehicles, ArrayList<Port> ports, ArrayList<Trip> trips) {
        Scanner scan = new Scanner(System.in);
        System.out.println("----------MOVE TO THE PORT----------");

        while (true) {
            String vehicleID;
            Vehicle vehicle = null;
            do {
                System.out.println("Please input Vehicle ID (must start with 'sh' or 'tr'), or type 'exit' to quit: ");
                vehicleID = scan.nextLine();

                if (vehicleID.equalsIgnoreCase("exit") || vehicleID.equalsIgnoreCase("quit")) {
                    System.out.println("Exiting the function.");
                    return false;
                }

                if (!vehicleID.toLowerCase().startsWith("sh") && !vehicleID.toLowerCase().startsWith("tr")) {
                    System.out.println("Invalid vehicle ID format. Must start with 'sh' or 'tr'!!!");
                } else {
                    vehicle = findVehicleByID(vehicleID, vehicles);
                    if (vehicle == null) {
                        System.out.println("Vehicle not found. Please enter a valid ID.");
                    }
                }
            } while (vehicle == null);

            String portID;
            Port destinationPort = null;
            do {
                System.out.println("Please input Port ID (must start with 'P'), or type 'exit' to quit: ");
                portID = scan.nextLine();

                if (portID.equalsIgnoreCase("exit") || portID.equalsIgnoreCase("quit")) {
                    System.out.println("Exiting the function.");
                    return false;
                }

                if (!portID.toLowerCase().startsWith("p")) {
                    System.out.println("Invalid port ID format. Must start with 'p'!!!");
                } else {
                    destinationPort = findPortByID(portID, ports);
                    if (destinationPort == null) {
                        System.out.println("Port not found. Please enter a valid ID.");
                    }
                }
            } while (destinationPort == null);

            if (!destinationPort.isLandingAbility()) {
                System.out.println("Error: The destination port does not have landing ability.");
                return false;
            }

            ArrayList<Container> loadedContainers = vehicle.getContainers();
            Port currentPort = vehicle.getCurrentPort();
            double currentLatitude = (currentPort == null) ? 0.0 : currentPort.getLatitude();
            double currentLongitude = (currentPort == null) ? 0.0 : currentPort.getLongitude();


            double totalWeight = loadedContainers.stream()
                    .mapToDouble(Container::getWeight)
                    .sum();

            if (totalWeight > vehicle.getCarryingCapability()) {
                System.out.println("Error: The total weight of loaded containers exceeds the vehicle's carrying capacity.");
                return false;
            }

            double estimatedFuelConsumption = calculateEstimatedFuelConsumption(destinationPort, currentLatitude, currentLongitude, vehicle);
            if (vehicle.getFuelCapability() < estimatedFuelConsumption || vehicle.getFuelAmount() < estimatedFuelConsumption) {
                System.out.println("Error: Insufficient fuel capacity Or amount for the trip.");
                return false;
            }
            System.out.println(estimatedFuelConsumption);

            if (currentPort != null) {
                currentPort.setLatitude(destinationPort.getLatitude());
                currentPort.setLongitude(destinationPort.getLongitude());
            }
            vehicle.setFuelAmount(vehicle.getFuelAmount() - estimatedFuelConsumption);
            vehicle.setCurrentPort(destinationPort);
            destinationPort.getVehicles().add(vehicle);
            Trip associatedTrip = findTripByVehicle(vehicle, trips);
            if (associatedTrip != null) {
                associatedTrip.setStatus("Completed");
            }
            System.out.print("Do you want to continue? (yes/no): ");
            String continueOption = scan.nextLine();
            if (!continueOption.equalsIgnoreCase("yes")) {
                System.out.println("Exiting the function.");
                return false;
            }
        }
    }

    private Trip findTripByVehicle(Vehicle vehicle, ArrayList<Trip> trips) {
        for (Trip trip : trips) {
            if (trip.getVehicle().equals(vehicle)) {
                return trip;
            }
        }
        return null;
    }

    private double calculateEstimatedFuelConsumption(Port destinationPort, double currentLatitude, double currentLongitude, Vehicle vehicle) {
        ArrayList<Container> loadedContainers = vehicle.getContainers();
        double totalWeight = loadedContainers.stream()
                .mapToDouble(Container::getWeight)
                .sum();
        System.out.println("Total weight: " + totalWeight);

        double fuelConsumptionRate = 0.0;
        for (Container container : loadedContainers) {
            if (vehicle instanceof Ship) {
                fuelConsumptionRate += container.getShipFuelConsumptionPerKm();
            } else {
                fuelConsumptionRate += container.getTruckFuelConsumptionPerKm();
            }
        }
        if (!loadedContainers.isEmpty()) {
            fuelConsumptionRate /= loadedContainers.size();
        } else {
            fuelConsumptionRate = 0.0;
        }

        System.out.println("fuel consumption rate: " + fuelConsumptionRate);

        double distanceToDestination = calculateDistanceToPort(currentLatitude, currentLongitude, destinationPort);
        System.out.println(distanceToDestination);
        return fuelConsumptionRate * totalWeight * distanceToDestination;
    }

    private double calculateDistanceToPort(double currentLatitude, double currentLongitude, Port destinationPort) {

        double lat1 = currentLatitude;
        System.out.println("lat1: " + lat1);
        double lon1 = currentLongitude;
        System.out.println("lon1: " + lon1);

        double lat2 = destinationPort.getLatitude();
        System.out.println("lat2: " + lat2);
        double lon2 = destinationPort.getLongitude();
        System.out.println("lon2: " + lon2);


        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;
        double dPort = Math.sqrt(Math.pow(dlat, 2) + Math.pow(dlon, 2));


        return dPort;
    }

    public void loadContainerToVehicle(ArrayList<Vehicle> vehicles, ArrayList<Container> allContainers) {
        Scanner scan = new Scanner(System.in);
        System.out.println("----------ADD CONTAINER TO VEHICLE----------");

        String vehicleID;
        Vehicle vehicle = null;

        do {
            System.out.println("Please input Vehicle ID (must start with 'sh' or 'tr'): ");
            vehicleID = scan.nextLine();
            if (!vehicleID.toLowerCase().startsWith("sh") && !vehicleID.toLowerCase().startsWith("tr")) {
                System.out.println("Invalid vehicle ID format. Must start with 'sh' or 'tr'!!!");
            } else {
                vehicle = findVehicleByID(vehicleID, vehicles);
                if (vehicle == null) {
                    System.out.println("Vehicle not found. Please enter a valid ID.");
                }
            }
        } while (vehicle == null);

        String addContainers;
        do {
            System.out.println("Please enter container ID: ");
            String containerID = scan.nextLine();
            boolean containerFound = false;
            boolean containerLoaded = isContainerLoaded(containerID, vehicles);

            if (containerLoaded) {
                System.out.println("Container with ID " + containerID + " is already loaded on another vehicle.");
            } else {
                for (Container con : allContainers) {
                    if (con.getId().equalsIgnoreCase(containerID)) {
                        if (vehicle instanceof Ship ||
                                (vehicle instanceof BasicTruck && con.getType().equalsIgnoreCase("dryStorage")) ||
                                (vehicle instanceof BasicTruck && con.getType().equalsIgnoreCase("openTop")) ||
                                (vehicle instanceof BasicTruck && con.getType().equalsIgnoreCase("openSide")) ||
                                (vehicle instanceof ReeferTruck && con.getType().equalsIgnoreCase("refrigerated")) ||
                                (vehicle instanceof TankerTruck && con.getType().equalsIgnoreCase("liquid"))) {
                            vehicle.getContainers().add(con);
                            containerFound = true;
                            System.out.println("Container with ID " + containerID + " added to the vehicle.");
                            break;
                        } else {
                            System.out.println("Container with ID " + containerID + " is NOT suitable for this vehicle.");
                            break;
                        }
                    }
                }
                if (!containerFound) {
                    System.out.println("Container with ID " + containerID + " NOT found or NOT suitable for this vehicle.");
                }
            }

            System.out.println("Do you want to add more containers? (yes/no): ");
            addContainers = scan.nextLine();

        } while (addContainers.equalsIgnoreCase("yes"));
    }

    public void unloadContainerFromVehicle(ArrayList<Vehicle> vehicles) {
        Scanner scan = new Scanner(System.in);
        System.out.println("----------UNLOAD CONTAINER FROM VEHICLE----------");

        String vehicleID;
        Vehicle vehicle = null;

        do {
            System.out.println("Please input Vehicle ID (must start with 'sh' or 'tr'): ");
            vehicleID = scan.nextLine();
            if (!vehicleID.toLowerCase().startsWith("sh") && !vehicleID.toLowerCase().startsWith("tr")) {
                System.out.println("Invalid vehicle ID format. Must start with 'sh' or 'tr'!!!");
            } else {
                vehicle = findVehicleByID(vehicleID, vehicles);
                if (vehicle == null) {
                    System.out.println("Vehicle not found. Please enter a valid ID.");
                }
            }
        } while (vehicle == null);

        String removeContainers;
        do {
            System.out.println("Please enter container ID to unload: ");
            String containerID = scan.nextLine();
            boolean containerFound = false;

            for (Container con : vehicle.getContainers()) {
                if (con.getId().equalsIgnoreCase(containerID)) {
                    vehicle.getContainers().remove(con);
                    containerFound = true;
                    System.out.println("Container with ID " + containerID + " unloaded from the vehicle.");
                    break;
                }
            }

            if (!containerFound) {
                System.out.println("Container with ID " + containerID + " not found on this vehicle.");
            }

            System.out.println("Do you want to unload more containers? (yes/no): ");
            removeContainers = scan.nextLine();

        } while (removeContainers.equalsIgnoreCase("yes"));
    }

    private boolean isContainerLoaded(String containerID, ArrayList<Vehicle> vehicles) {
        for (Vehicle vehicle : vehicles) {
            for (Container container : vehicle.getContainers()) {
                if (container.getId().equalsIgnoreCase(containerID)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removePortManager() {
        Scanner scan = new Scanner(System.in);
        System.out.println("------------- Remove Port Manager ----------------");
        System.out.println("Please enter Username of Manager: ");
        String username = scan.nextLine();
        PortManager manager = findManagerByUsername(username);
        if (manager != null) {
            this.listOfManagers.remove(manager);
            writeToFile();
            System.out.println("The Manager is removed successfully");
        } else {
            System.out.println("The Username not found");
        }
    }

    public void writeToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("managers.txt"))) {
            for (PortManager manager : this.listOfManagers) {
                bufferedWriter.write(manager.getUsername() + " " + manager.getPassword() + " " + manager.getRole() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PortManager findManagerByUsername(String username) {
        for (User user : this.listOfManagers) {
            if (user instanceof PortManager manager && manager.getUsername().equalsIgnoreCase(username)) {
                return manager;
            }
        }
        return null;
    }

    public void addPortManager(ArrayList<Port> ports) {
        PortManager portManager = PortManager.createPortManager(listOfManagers, ports);
        this.listOfManagers.add(portManager);
        try {
            FileWriter fileWriter = new FileWriter("managers.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            assert portManager != null;
            printWriter.println(portManager.getUsername() + " " + portManager.getPassword() + " " + portManager.getRole() + " " + portManager.getPort().getPortID());
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showManagerList(ArrayList<Port> ports) {
        clearPortManagers();
        readPortManagersFromFile("managers.txt", ports);
        for (User user : this.listOfManagers) {
            user.output();
        }
    }

    public void clearPortManagers() {
        this.listOfManagers.removeIf(user -> user != null);
    }

    public User authenticate(String username, String password, ArrayList<Port> ports) {
        readAdminFromFile("admin.txt");
        readPortManagersFromFile("managers.txt", ports);
        SystemAdmin admin = SystemAdmin.getInstance();

        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
            if (isMoreThan7DaysSinceLastLogin("last_login.txt")) {
                clearTripsFile();
            }

            updateLastLoginDate("last_login.txt");

            return admin;
        } else {
            for (PortManager manager : this.listOfManagers) {
                if (manager.getUsername().equals(username) && manager.getPassword().equals(password)) {
                    if (isMoreThan7DaysSinceLastLogin("last_login.txt")) {
                        clearTripsFile();
                    }
                    updateLastLoginDate("last_login.txt");
                    return manager;
                }
            }
            System.out.println("Authentication failed. Invalid credentials.");
            return null;
        }
    }

    public boolean isMoreThan7DaysSinceLastLogin(String lastLoginFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(lastLoginFile))) {
            String lastLoginDateString = reader.readLine();
            if (lastLoginDateString == null) {
                return false;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date lastLoginDate = dateFormat.parse(lastLoginDateString);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -7);
            Date sevenDaysAgo = calendar.getTime();

            return lastLoginDate.before(sevenDaysAgo);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateLastLoginDate(String lastLoginFile) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDate = dateFormat.format(new Date());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(lastLoginFile))) {
            writer.write(currentDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearTripsFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("trips.txt", false))) {
            writer.print("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readPortManagersFromFile(String filename, ArrayList<Port> ports) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 4) {
                    String username = parts[0];
                    String password = parts[1];
                    String role = parts[2];
                    String portID = parts[3];

                    Port port = findPortByID(portID, ports);
                    PortManager portManager = new PortManager(username, password, port);
                    this.listOfManagers.add(portManager);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readAdminFromFile(String filename) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            if ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    User admin = SystemAdmin.getInstance();
                    admin.setUsername(username);
                    admin.setPassword(password);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}