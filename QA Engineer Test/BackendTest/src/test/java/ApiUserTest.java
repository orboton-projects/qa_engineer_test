import models.CreateUserModel;
import models.UpdateUserModel;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import io.restassured.response.Response;
import services.GoRestService;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;


public class ApiUserTest {

  private static int userId = 0;

  // Positive Test Cases

  @Test
  public void Users_CreateUsers_Success() {
    final CreateUserModel createUserModel = new CreateUserModel(
        "Raj", "Male", "Raj@test.com", "Active");

    Response response = GoRestService.createUser(createUserModel)
        .then()
        .statusCode(SC_CREATED)
        .body("id", notNullValue())
        .body("name", equalTo(createUserModel.getName()))
        .body("email", equalTo(createUserModel.getEmail()))
        .body("gender", equalToIgnoringCase(createUserModel.getGender()))
        .body("status", equalToIgnoringCase(createUserModel.getStatus()))
        .body(matchesJsonSchemaInClasspath("resources/test_schema.json"))
        .extract().response();

    userId = response.then().extract().path("id");
    System.out.println("CREATED User: " + userId);
  }

  @Test
  public void Users_GetUsersList_Success() {
    GoRestService.getUsers()
        .then()
        .statusCode(SC_OK)
        .body("size()", greaterThan(0)) // Verify at least one user exists
        .body("[0].id", notNullValue()); // Verify presence of ID in first user object
  }

  @Test
  public void Users_GetUserById_Success() {
    // Assuming the user is created in a previous test (e.g., Users_CreateUsers_Success)

    GoRestService.getUser(userId)
        .then()
        .statusCode(SC_OK)
        .body("id", equalTo(userId))
        .extract().response();

    System.out.println("RETRIEVED User: " + userId);
  }

  @Test
  public void Users_UpdateUsers_Success() {
    final UpdateUserModel updateUserModel = new UpdateUserModel(
        "Raj Updated", "raj_updated@test.com", "Active");

    GoRestService.updateUser(updateUserModel, userId)
        .then()
        .statusCode(SC_OK)
        .body("id", notNullValue())
        .body("name", equalTo(updateUserModel.getName()))
        .body("email", equalTo(updateUserModel.getEmail()))
        .body("status", equalToIgnoringCase(updateUserModel.getStatus()))
        .body(matchesJsonSchemaInClasspath("resources/test_schema.json"));

    System.out.println("UPDATED User: " + userId);
  }

  // Negative Test Cases

  @Test
  public void Users_CreateUsers_InvalidData() {
    final CreateUserModel invalidUserModel = new CreateUserModel("", "", "", ""); // Empty fields

    GoRestService.createUser(invalidUserModel)
        .then()
        .statusCode(SC_UNPROCESSABLE_ENTITY); // Verfiy SC 422 for invalid data
  }

  @Test
  public void Users_GetUserById_NonExistentUser() {
    GoRestService.getUser(123456) // Verify SC 404 for non-existent user ID
        .then()
        .statusCode(SC_NOT_FOUND);
  }

  @Test
  public void Users_UpdateUser_InvalidData() {
    final UpdateUserModel invalidUpdateUserModel = new UpdateUserModel("", "", ""); // Empty fields

    GoRestService.updateUser(invalidUpdateUserModel, userId) // Assuming user created and exists
        .then()
        .statusCode(SC_UNPROCESSABLE_ENTITY); // Verfiy SC 422 for invalid data
  }


  // Test data clean up using Delete Endpoints
  
  @AfterAll
  public static void Users_DeleteUsers_Success() {
    // Assuming the user is created in a previous test (e.g., Users_CreateUsers_Success)

    GoRestService.deleteUser(userId)
        .then()
        .statusCode(SC_NO_CONTENT);

    System.out.println("DELETED User: " + userId);
  } 

}


