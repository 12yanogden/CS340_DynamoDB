import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;

public class Deleter {
    public void delete(Table table, String followerHandle, String followeeHandle) {
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("follower_handle", followerHandle, "followee_handle", followeeHandle));

        System.out.println("Attempting a delete...");

        table.deleteItem(deleteItemSpec);

        System.out.println("DeleteItem succeeded");
    }
}
