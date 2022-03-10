import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

public class DynamoDBFacade {
    private final DynamoDB dynamoDB;
    private final Getter getter;


    public DynamoDBFacade(String region) {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(region)
                .build();

        dynamoDB = new DynamoDB(client);
        getter = new Getter();
    }

    public Table getTableByName(String name) {
        return dynamoDB.getTable(name);
    }


    public void getFolloweesByFollower(String tableName, User follower) {
        getter.queryFolloweesByFollower(getTableByName(tableName), follower);
    }

    public void getFollowersByFollowee(String tableName, User followee) {
        getter.queryFollowersByFollowee(getTableByName(tableName), followee);
    }

    public void getPaginizedFolloweesByFollower(String tableName, User follower, int pageSize) {
        getter.queryPaginizedFolloweesByFollower(getTableByName(tableName), follower, pageSize);
    }

    public void getPaginizedFollowersByFollowee(String tableName, User follower, int pageSize) {
        getter.queryPaginizedFollowersByFollowee(getTableByName(tableName), follower, pageSize);
    }
}


