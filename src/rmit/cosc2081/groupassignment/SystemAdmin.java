package rmit.cosc2081.groupassignment;

public class SystemAdmin extends User{
    //1. attributes
    private static SystemAdmin single_instance = null;
    //2. constructor
    private SystemAdmin() {
        String s = "Hello I am a string part of Singleton class";
        this.role = "admin";
    };


    public static synchronized SystemAdmin getInstance()
    {
        if (single_instance == null)
            single_instance = new SystemAdmin();

        return single_instance;
    }
    //4. input, output
    @Override
    public void output() {
        System.out.print("Username: "+ this.getUsername());
        System.out.println("\tRole: "+ this.getRole()+"\n");
    }
}
