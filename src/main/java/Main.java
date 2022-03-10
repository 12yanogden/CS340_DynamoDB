public class Main {
    static String hashKeyName = "follower_handle";
    static String rangeKeyName = "followee_handle";

    public static void main(String[] args) {
        User defaultFollower = new User("@FredFlintstone", "Fred Flintstone");
        User defaultFollowee = new User("@ClintEastwood", "Clint Eastwood");

        String tableName = "follows";
        String region = "us-west-2";
        int pageSize = 10;

        DynamoDBFacade facade = new DynamoDBFacade(region);

        try {
            facade.getFolloweesByFollower(tableName, defaultFollower);
            facade.getFollowersByFollowee(tableName, defaultFollowee);
            facade.getPaginizedFolloweesByFollower(tableName, defaultFollower, pageSize);
            facade.getPaginizedFollowersByFollowee(tableName, defaultFollowee, pageSize);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
