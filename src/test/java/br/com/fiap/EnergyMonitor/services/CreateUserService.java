package br.com.fiap.EnergyMonitor.services;

import br.com.fiap.EnergyMonitor.model.UserModel;
import br.com.fiap.EnergyMonitor.model.UserRoles;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreateUserService {
    final UserModel userModel = new UserModel();
    public final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    public Response response;
    String baseUrl = "http://localhost:8080";

    public void setFieldsDelivery(String field, String value) {
        switch (field) {
            case "id" -> userModel.setId(Integer.parseInt(value));
            case "name" -> userModel.setName(value);
            case "email" -> userModel.setEmail(value);
            case "password" -> userModel.setPassword(value);
            case "role" -> userModel.setRole(UserRoles.valueOf(value));
            default -> throw new IllegalStateException("Unexpected feld" + field);
        }
    }

    public void createUser(String endPoint) {
        String url = baseUrl + endPoint;
        String bodyToSend = gson.toJson(userModel);
        response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(bodyToSend)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }
}
