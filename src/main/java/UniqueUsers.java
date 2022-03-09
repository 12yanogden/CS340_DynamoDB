import java.util.ArrayList;
import java.util.List;

public class UniqueUsers {
    private ArrayList<User> users;

    public UniqueUsers(String handle, String name, int count) {
        this.users = new ArrayList<>();

        calcUniqueUsers(handle, name, count);
    }

    private void calcUniqueUsers(String handle, String name, int count) {
        List<String> uniqueHandles = calcUniques(handle, count);
        List<String> uniqueNames = calcUniques(name, count);

        for (int i = 0; i < count; i++) {
            users.add(new User(uniqueHandles.get(i), uniqueNames.get(i)));
        }
    }

    private List<String> calcUniques(String string, int count) {
        ArrayList<String> uniques = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            uniques.add(string + i);
        }

        return uniques;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
