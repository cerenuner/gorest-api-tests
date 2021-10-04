package util;

import baseSettings.BaseSettings;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.Reporter;
import util.enums.MediaType;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.lessThan;

public final class UserUtils extends BaseSettings {

    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static JsonPath getAllUsers() {
        return RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .contentType(MediaType.JSON)
                .get(getUsersPath())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .time(lessThan(getResponseTimeLimit()))
                .extract().jsonPath();
    }

    public static JsonPath getUserById(int userId) {
        return RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .contentType(MediaType.JSON)
                .get(getUsersPath() + "/" + userId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .time(lessThan(getResponseTimeLimit()))
                .extract().jsonPath();
    }

    public static int getUserByIdAndReturnStatusCode(int userId) {
        return RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .contentType(MediaType.JSON)
                .get(getUsersPath() + "/" + userId)
                .then()
                .and()
                .time(lessThan(getResponseTimeLimit()))
                .extract().statusCode();
    }

    public static int createUserAndReturnUserId(String name, String gender, String email, String status) {
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", name);
        userInfo.put("gender", gender);
        userInfo.put("email", email);
        userInfo.put("status", status);
        return RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .body(userInfo.toJSONString())
                .contentType(MediaType.JSON)
                .post(getUsersPath())
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .time(lessThan(getResponseTimeLimit()))
                .extract().jsonPath().get("data.id");
    }

    public static int createUserAndReturnStatusCode(JSONObject userInfo) {
        return RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .body(userInfo.toJSONString())
                .contentType(MediaType.JSON)
                .post(getUsersPath())
                .then()
                .time(lessThan(getResponseTimeLimit()))
                .extract().statusCode();
    }

    public static void updateUser(int userId, String name, String gender, String email, String status) {
        JSONObject userInfo = new JSONObject();
        userInfo.put("name", name);
        userInfo.put("gender", gender);
        userInfo.put("email", email);
        userInfo.put("status", status);
        RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .body(userInfo.toJSONString())
                .contentType(MediaType.JSON)
                .patch(getUsersPath() + "/" + userId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .time(lessThan(getResponseTimeLimit()));
    }

    public static int updateUserAndReturnStatusCode(int userId, JSONObject userInfo) {
        return RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .body(userInfo.toJSONString())
                .contentType(MediaType.JSON)
                .patch(getUsersPath() + "/" + userId)
                .then()
                .and()
                .time(lessThan(getResponseTimeLimit()))
                .extract().statusCode();
    }

    public static void deleteUser(int userId) {
        RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .contentType(MediaType.JSON)
                .delete(getUsersPath() + "/" + userId)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT)
                .and()
                .time(lessThan(getResponseTimeLimit()));
    }

    public static int deleteUserAndReturnStatusCode(int userId) {
        return RestAssured
                .given()
                .header("Authorization", getAccessToken())
                .contentType(MediaType.JSON)
                .delete(getUsersPath() + "/" + userId)
                .then()
                .and()
                .time(lessThan(getResponseTimeLimit()))
                .extract()
                .statusCode();
    }

    public static String generateUniqueUserTag() {
        return System.currentTimeMillis() + String.valueOf(atomicInteger.getAndIncrement());
    }

}
