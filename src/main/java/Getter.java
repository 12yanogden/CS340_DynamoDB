import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Getter {
    public void get(Table table, String followerHandle, String followeeHandle) {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("follower_handle", followerHandle, "followee_handle", followeeHandle);

        System.out.println("Attempting to read the item...");

        Item outcome = table.getItem(spec);

        System.out.println("GetItem succeeded: " + outcome);
    }

    public void queryFolloweesByFollower(Table table, User follower) {
        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#follower", Main.hashKeyName);

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":person", follower.getHandle());

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#follower = :person").withNameMap(nameMap)
                .withValueMap(valueMap);
        querySpec.withScanIndexForward(true);

        try {
            items = table.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getString("followee_name"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void queryFollowersByFollowee(Table table, User followee) {
        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#followee", Main.rangeKeyName);

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":person", followee.getHandle());

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#followee = :person").withNameMap(nameMap)
                .withValueMap(valueMap);
        querySpec.withScanIndexForward(false);

        try {
            Index index = table.getIndex("followee_handle-follower_handle-index");
            items = index.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                System.out.println(item.getString("follower_name"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void queryPaginizedFolloweesByFollower(Table table, User follower, int pageSize) {
        ItemCollection<QueryOutcome> items = null;
        Map<String, AttributeValue> lastEvaluatedKey = null;
        Iterator<Item> iterator = null;
        Item item = null;

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#follower", Main.hashKeyName);

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":person", follower.getHandle());

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#follower = :person").withNameMap(nameMap)
                .withValueMap(valueMap);
        querySpec.withScanIndexForward(true);
        querySpec.withMaxResultSize(pageSize);

        while(true) {

            try {
                items = table.query(querySpec);

                iterator = items.iterator();
                while (iterator.hasNext()) {
                    item = iterator.next();
                    System.out.println(item.getString("followee_name"));
                }

                lastEvaluatedKey = items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey();

                if (lastEvaluatedKey == null) {
                    break;
                } else {
                    querySpec.withExclusiveStartKey(calcPrimaryKey(lastEvaluatedKey));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private PrimaryKey calcPrimaryKey(Map<String, AttributeValue> lastEvaluatedKey) {
        return new PrimaryKey(Main.hashKeyName,
                lastEvaluatedKey.get(Main.hashKeyName).getS(),
                Main.rangeKeyName,
                lastEvaluatedKey.get(Main.rangeKeyName).getS());
    }

    public void queryPaginizedFollowersByFollowee(Table table, User follower, int pageSize) {
        ItemCollection<QueryOutcome> items = null;
        Map<String, AttributeValue> lastEvaluatedKey = null;
        Iterator<Item> iterator = null;
        Item item = null;

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#followee", Main.rangeKeyName);

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":person", follower.getHandle());

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#followee = :person").withNameMap(nameMap)
                .withValueMap(valueMap);
        querySpec.withScanIndexForward(false);
        querySpec.withMaxResultSize(pageSize);

        while(true) {

            try {
                Index index = table.getIndex("followee_handle-follower_handle-index");
                items = index.query(querySpec);

                iterator = items.iterator();
                while (iterator.hasNext()) {
                    item = iterator.next();
                    System.out.println(item.getString("follower_name"));
                }

                lastEvaluatedKey = items.getLastLowLevelResult().getQueryResult().getLastEvaluatedKey();

                if (lastEvaluatedKey == null) {
                    break;
                } else {
                    querySpec.withExclusiveStartKey(calcPrimaryKey(lastEvaluatedKey));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
