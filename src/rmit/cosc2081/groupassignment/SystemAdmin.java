package rmit.cosc2081.groupassignment;

public class SystemAdmin extends User{
    //1. attributes
    //2. constructor
    public SystemAdmin(String username, String password) {
        super(username, password);
        this.role = "admin";
    }
    //3. get, set methods
    //4. input, output
    @Override
    public void output() {
        System.out.print("Username: "+ this.getUsername());
        System.out.println("\tRole: "+ this.getRole()+"\n");    }
    //5. main tasks
}
