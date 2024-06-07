package services;

import io.restassured.response.Response;
import models.CreateUserModel;
import models.UpdateUserModel;


public class GoRestService extends BaseService {


    public static Response getUsers(){
        return defaultRequestSpecification()
                .get(String.format("/public/v2/users"));
    }

    public static Response getUser(int id){
        return defaultRequestSpecification()
                .get(String.format("/public/v2/users/%d", id));
    }

    public static Response createUser(final CreateUserModel createUserModel){
        return defaultRequestSpecification()
                .body(createUserModel)
                .when()
                .post("/public/v2/users");
    }


    public static Response updateUser(final UpdateUserModel updateUserModel, int id){
        return defaultRequestSpecification()
                .body(updateUserModel)
                .when()
                .put(String.format("/public/v2/users/%d", id));
    }

    public static Response deleteUser(int id){
        return defaultRequestSpecification()
                .delete(String.format("/public/v2/users/%d", id));
    }
}
