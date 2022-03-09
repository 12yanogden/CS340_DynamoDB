import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.Collection;

public class DynamoDBFacade {
    private final DynamoDB dynamoDB;
    private final Putter putter;
    private final Getter getter;
    private final Updater updater;
    private final Deleter deleter;

    public DynamoDBFacade(String region) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(region)
                .build();

        dynamoDB = new DynamoDB(client);
        putter = new Putter();
        getter = new Getter();
        updater = new Updater();
        deleter = new Deleter();
    }

    public Table getTableByName(String name) {
        return dynamoDB.getTable(name);
    }

    public void putByFollower(User follower, Collection<User> followees, String tableName) {
        putter.putByFollower(follower, followees, getTableByName(tableName));
    }

    public void putByFollowee(Collection<User> followers, User followee, String tableName) {
        putter.putByFollowee(followers, followee, getTableByName(tableName));
    }

    public void get(String tableName, String followerHandle, String followeeHandle) {
        getter.get(getTableByName(tableName), followerHandle, followeeHandle);
    }

    public void update(String tableName, String followerHandle, String followeeHandle, String followerName, String followeeName) {
        updater.update(getTableByName(tableName), followerHandle, followeeHandle, followerName, followeeName);
    }

    public void delete(String tableName, String followerHandle, String followeeHandle) {
        deleter.delete(getTableByName(tableName), followerHandle, followeeHandle);
    }
}


