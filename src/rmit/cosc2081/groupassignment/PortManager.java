package rmit.cosc2081.groupassignment;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static rmit.cosc2081.groupassignment.Port.findPortByID;

public class PortManager extends User{
    static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
    //1.attribute
    private Port port;
    //2.constructor

    public PortManager(String username, String password, Port port) {
        super(username, password);
        this.setRole("manager");
        this.port = port;
    }

    //3.get,set method

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }


    //4. input, output
    public static PortManager createPortManager(ArrayList<PortManager> managers, ArrayList<Port> ports) {
        Scanner scan = new Scanner(System.in);
        System.out.println("==================== ADD PORT MANAGER =====================");
        String username;
        String password;

        PortManager portManager = null;

        while (true) {
            System.out.println("Please input the Username: ");
            username = scan.nextLine().toLowerCase();
            if (!usernameExists(managers, username)) {
                break;
            } else {
                System.out.println("Username already exists. Please choose a different username.");
            }
        }

        while (true) {
            System.out.println("Please input Password: ");
            password = scan.nextLine();
            if (isValidPassword(password)) {
                break;
            } else {
                System.out.println("Invalid password format.\nPassword must contain at least one uppercase letter, one lowercase letter, one digit, one special character, and be between 8 and 20 characters long.");
            }
        }

        while (true) {
            System.out.println("Please input the Port ID: ");
            String portID = scan.nextLine().toUpperCase();

            boolean portIDBelongsToOtherManager = managers.stream()
                    .anyMatch(manager -> manager.getPort() != null && manager.getPort().getPortID().equalsIgnoreCase(portID));

            if (portIDBelongsToOtherManager) {
                System.out.println("This port already has a manager. Please choose another port.");
            } else {
                Port assignedPort = findPortByID(portID, ports);

                if (assignedPort != null) {
                    portManager = new PortManager(username, password, assignedPort);
                    break;
                } else {
                    System.out.println("Port not found. Please enter a valid Port ID.");
                }
            }
        }

        System.out.println("==================== END =====================");
        return portManager;
    }

    public static boolean usernameExists(ArrayList<PortManager> managers, String username) {
        String lowercaseUsername = username.toLowerCase();
        return managers.stream().anyMatch(user -> user.getUsername().toLowerCase().equals(lowercaseUsername));
    }
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hashString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hashString.append('0');
                }
                hashString.append(hex);
            }
            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    @Override
    public void output() {
        System.out.print("Username: "+ this.getUsername());
        System.out.print("\tPassword: "+ hashPassword(this.getPassword()));
        System.out.print("\tRole: "+ this.getRole());
        System.out.print("\tPort: "+ this.getPort().getPortID());
        System.out.println();
    }
}

interface ManagerModifiable {
    void removePortManager();
    PortManager findManagerByUsername(String username);
    void addPortManager(ArrayList<Port> ports);
    void showManagerList(ArrayList<Port> ports);
}

