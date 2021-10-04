package scenarios.users;

import baseSettings.BaseSettings;
import io.restassured.path.json.JsonPath;
import net.minidev.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.UserUtils;

public class DeleteUsers extends BaseSettings {

    @Test
    public void userShouldBeDeleted() {
        String uniqueUserTag = UserUtils.generateUniqueUserTag();
        int userId = UserUtils.createUserAndReturnUserId(uniqueUserTag, "female", uniqueUserTag + "@gmail.com", "active");

        Assert.assertEquals(UserUtils.getUserByIdAndReturnStatusCode(userId), 200);
        UserUtils.deleteUser(userId);
        Assert.assertEquals(UserUtils.getUserByIdAndReturnStatusCode(userId), 404);
    }

    @Test
    public void nonExistingUserShouldNotBeDeleted() {
        int nonExistingUserId = 999999999;
        if (UserUtils.getUserByIdAndReturnStatusCode(nonExistingUserId) != 404)
            UserUtils.deleteUser(nonExistingUserId);
        Assert.assertEquals(UserUtils.deleteUserAndReturnStatusCode(nonExistingUserId), 404);
    }

}
