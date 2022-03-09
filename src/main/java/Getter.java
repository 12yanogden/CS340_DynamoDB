import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

public class Getter {
    public void get(Table table, String followerHandle, String followeeHandle) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("follower_handle", followerHandle, "followee_handle", followeeHandle);

        System.out.println("Attempting to read the item...");

        Item outcome = table.getItem(spec);

        System.out.println("GetItem succeeded: " + outcome);
    }
}
