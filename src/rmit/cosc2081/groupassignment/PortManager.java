package rmit.cosc2081.groupassignment;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortManager extends User{
    static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
    //1.attribute
    private Port port;
    //2.constructor

    public PortManager(String username, String password, String role, Port port) {
        super(username, password);
        this.role = "manager";
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
    public static PortManager createPortManager(ArrayList<PortManager> managers) {
        Scanner scan = new Scanner(System.in);
        System.out.println("==================== ADD PORT MANAGER =====================");
        String username;
        while (true) {
            System.out.println("Please input the Username: ");
            username = scan.nextLine().toLowerCase();
            if (!usernameExists(managers, username)) {
                break;
            } else {
                System.out.println("Username already exists. Please choose a different username.");
            }
        }
        String password;
        while (true) {
            System.out.println("Please input Password: ");
            password = scan.nextLine();
            if (isValidPassword(password)) {
                break;
            } else {
                System.out.println("Invalid password format.\nPassword must contain at least one uppercase letter, one lowercase letter, one digit, one special character, and be between 8 and 20 characters long.");
            }
        }
        System.out.println("==================== END =====================");
        return new PortManager(username, password, "manager", null);
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
        System.out.println("\tRole: "+ this.getRole()+"\n");
    }
    //5. main tasks
}
