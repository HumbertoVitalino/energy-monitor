package br.com.fiap.EnergyMonitor.services;

import br.com.fiap.EnergyMonitor.model.UserModel;
import br.com.fiap.EnergyMonitor.model.UserRoles;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class CreateUserService {
    final UserModel userModel = new UserModel();
    public final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    public Response response;
    String baseUrl = "http://localhost:8080";
    String bearerToken;

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
        String url = getUrl(endPoint);
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

    public void acessUser(String endPoint) {
        String url = getUrl(endPoint);
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
        if (response.statusCode() == 200) {
            bearerToken = response.asString();
        } else {
            bearerToken = null;
        }
    }

    public void verifyToken(){
        Assert.assertNotNull("O Bearer Token não foi retornado na resposta.", bearerToken);
        // 1. Verificação Estrutural Básica (JWT com 3 partes)
        String[] parts = bearerToken.split("\\.");
        Assert.assertEquals("O token não tem o formato JWT (header.payload.signature).", 3, parts.length);
        // 2. Decodificação e Verificação de Conteúdo (usando java-jwt)
        try {
            // Decodifica o token sem verificar a assinatura (para fins de teste rápido)
            DecodedJWT jwt = JWT.decode(bearerToken);
            // Verifica a Expiração (exp)
            Date expiresAt = jwt.getExpiresAt();
            Assert.assertNotNull("O token não contém a data de expiração (exp).", expiresAt);
            // Verifica se a data de expiração é no futuro (ex: com uma margem de segurança de 60 segundos)
            Date now = new Date(System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(60));
            // Asserção: A data de expiração deve ser depois do momento atual
            Assert.assertTrue("O token já expirou.", expiresAt.after(now));
        } catch (Exception e) {
            Assert.fail("Falha ao decodificar ou verificar o JWT: " + e.getMessage());
        }
    }

    private String getUrl(String endPoint){
        return baseUrl + endPoint;
    }

}
