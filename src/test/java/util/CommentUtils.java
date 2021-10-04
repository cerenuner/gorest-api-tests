package util;

import baseSettings.BaseSettings;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import net.minidev.json.JSONObject;
import org.apache.http.HttpStatus;
import util.enums.MediaType;

import static org.hamcrest.Matchers.lessThan;

public final class CommentUtils extends BaseSettings {

    public static JsonPath getAllComments() {
        return RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .contentType(MediaType.JSON)
                .get(getCommentsPath())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .time(lessThan(getResponseTimeLimit()))
                .extract().jsonPath();
    }

    public static JsonPath getAllCommentsByPostId(int postId) {
        return RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .contentType(MediaType.JSON)
                .get(getPostsPath() + "/" + postId + "/comments")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .time(lessThan(getResponseTimeLimit()))
                .extract().jsonPath();
    }

    public static int createCommentAndReturnCommentId(int postId, String name, String email, String body) {
        JSONObject commentInfo = new JSONObject();
        commentInfo.put("post_id", postId);
        commentInfo.put("name", name);
        commentInfo.put("email", email);
        commentInfo.put("body", body);
        return RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .body(commentInfo.toJSONString())
                .contentType(MediaType.JSON)
                .post(getCommentsPath())
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .time(lessThan(getResponseTimeLimit()))
                .extract().jsonPath().get("data.id");
    }

    public static int createCommentAndReturnStatusCode(JSONObject commentInfo) {
        return RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .body(commentInfo.toJSONString())
                .contentType(MediaType.JSON)
                .post(getCommentsPath())
                .then()
                .time(lessThan(getResponseTimeLimit()))
                .extract().statusCode();
    }

}
