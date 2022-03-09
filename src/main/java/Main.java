public class Main {
    public static void main(String[] args) {
        String followerHandle = "@FredFlintstone";
        String followeeHandle = "@ClintEastwood";
        String followerName = "Fred Flintstone";
        String followeeName = "Clint Eastwood";

        int uniqueCount = 25;
        UniqueUsers uniqueFollowers = new UniqueUsers(followerHandle, followerName, uniqueCount);
        UniqueUsers uniqueFollowees = new UniqueUsers(followeeHandle, followeeName, uniqueCount);

        String tableName = "follows";
        String region = "us-west-2";

        DynamoDBFacade facade = new DynamoDBFacade(region);

        try {
            facade.putByFollower(new User(followerHandle, followerName), uniqueFollowees.getUsers(), tableName);
            facade.putByFollowee(uniqueFollowers.getUsers(), new User(followeeHandle, followeeName), tableName);

            facade.get(tableName, "@FredFlintstone", "@ClintEastwood17");

            facade.update(tableName, "@FredFlintstone", "@ClintEastwood5", "updated1", "updated2");

            facade.delete(tableName, "@FredFlintstone", "@ClintEastwood17");
        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + followeeHandle + " follows " + followeeHandle);
            System.err.println(e.getMessage());
        }
    }
}
