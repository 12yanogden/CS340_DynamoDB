import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;

import java.util.Collection;

public class Putter {
    public void putByFollower(User follower, Collection<User> followees, Table table) {
        for(User followee: followees) {
            System.out.println("Adding a new item: { follower: " + follower.getName() + ", followee: " + followee.getName() + "}");

            PutItemOutcome outcome = table.putItem(
                    new Item()
                            .withPrimaryKey(
                                    "follower_handle",
                                    follower.getHandle(),
                            "followee_handle",
                                    followee.getHandle())
                            .withString(
                                    "follower_name",
                                    follower.getName())
                            .withString(
                                    "followee_name",
                                    followee.getName())
            );

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
        }
    }

    public void putByFollowee(Collection<User> followers, User followee, Table table) {
        for(User follower: followers) {
            System.out.println("Adding a new item: { follower: " + follower.getName() + ", followee: " + followee.getName() + " }");

            PutItemOutcome outcome = table.putItem(
                    new Item()
                            .withPrimaryKey(
                                    "follower_handle",
                                    follower.getHandle(),
                                    "followee_handle",
                                    followee.getHandle())
                            .withString(
                                    "follower_name",
                                    follower.getName())
                            .withString(
                                    "followee_name",
                                    followee.getName())
            );

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
        }
    }
}
