public class User {
    private final String handle;
    private final String name;

    public User(String handle, String name) {
        this.handle = handle;
        this.name = name;
    }

    public String getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }
}
