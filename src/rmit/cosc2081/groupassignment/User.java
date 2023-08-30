package rmit.cosc2081.groupassignment;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    //1.attributes
    private String username;
    private String password;

    String role;
    //2.constructor

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    //3. get, set method

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
    //4. input and output


    public void output() {
        System.out.println("Nothing to Print");
    }

    //5. main tasks

}
