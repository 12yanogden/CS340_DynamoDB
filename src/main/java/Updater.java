import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

public class Updater {
    public void update(Table table, String followerHandle, String followeeHandle, String followerName, String followeeName) {
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("follower_handle", followerHandle, "followee_handle", followeeHandle)
                .withUpdateExpression("set follower_name = :r, followee_name=:p")
                .withValueMap(new ValueMap().withString(":r", followerName).withString(":p", followeeName))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        System.out.println("Updating the item...");

        UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

        System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
    }
}
