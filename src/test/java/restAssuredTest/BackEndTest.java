package restAssuredTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import models.UpdateUserModel;
import org.testng.annotations.Test;
import utilites.GenerateFakeMessage;

import java.io.File;

import static org.hamcrest.Matchers.*;

public class BackEndTest {

    @Test
    public void checkResponseCodeTest() {
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log()
                .all()
                .statusCode(404);
    }

    @Test
    public void checkFieldsInResponse() {
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("per_page", equalTo(6))
                .body("total_pages", instanceOf(Integer.class))
                .body("data[0].id", instanceOf(Integer.class));
    }

    @Test
    public void checkBodyTest() {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/user.json"));
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log()
                .all()
                .body("", equalTo(expectedJson.getMap("")));
    }

    @Test
    public void updateUserTest() {
        UpdateUserModel updateUserModel = new UpdateUserModel();
        updateUserModel.setName(GenerateFakeMessage.getFirstName());
        updateUserModel.setJob(GenerateFakeMessage.getJob());
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(updateUserModel)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(200);
    }

    @Test
    public void deleteUserTest() {
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(204);
    }

    @Test
    public void registerUserTest() {
        JsonPath registerForm = new JsonPath(new File("src/test/resources/registerForm.json"));
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(registerForm.getMap(""))
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("id", instanceOf(Integer.class))
                .body("token", instanceOf(String.class));
    }

    @Test
    public void registerUnsuccessfulTest(){
        JsonPath registerUnsuccessfulForm = new JsonPath(new File("src/test/resources/registerUnsuccessfulForm.json"));
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(registerUnsuccessfulForm.getMap(""))
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    public void loginSuccessfulTest(){
        JsonPath loginDataForm = new JsonPath(new File("src/test/resources/loginData.json"));
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(loginDataForm.getMap(""))
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("token", instanceOf(String.class));
    }

    @Test
    public void loginUnsuccessfulTest(){
        JsonPath loginDataForm = new JsonPath(new File("src/test/resources/loginUnsuccessfulData.json"));
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .body(loginDataForm.getMap(""))
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("error", instanceOf(String.class));
    }

    @Test
    public void delayedResponseTest(){
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/users?delay=3)")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("page", equalTo(1))
                .body("per_page", equalTo(6))
                .body("total", instanceOf(Integer.class))
                .body("total_pages", instanceOf(Integer.class))
                .body("data[0].id", instanceOf(Integer.class));
    }
}

