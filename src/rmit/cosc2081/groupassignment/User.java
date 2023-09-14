package rmit.cosc2081.groupassignment;

public abstract class User {

    //1.attributes
    private String username;
    private String password;

    String role;
    //2.constructor

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //4. input and output
    public void output() {
        System.out.println("User Class Not Defined");
    }
    //5. main tasks

}
