package br.com.fiap.EnergyMonitor.services;

import br.com.fiap.EnergyMonitor.model.SectorModel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static io.restassured.RestAssured.given;

@Service
public class CreateSectorService {

    final SectorModel sectorModel = new SectorModel();
    public final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    public Response response;
    String baseUrl = "http://localhost:8080";

    String schemasPath = "src/test/resources/schemas/";
    JSONObject jsonSchema;
    private final ObjectMapper mapper = new ObjectMapper();

    public void setFieldsDelivery(String field, String value) {
        switch (field) {
            case "id" -> sectorModel.setId(Integer.parseInt(value));
            case "name" -> sectorModel.setName(value);
            case "consumptionLimit" -> sectorModel.setConsumptionLimit(Double.valueOf(value));
            //case "user" -> SectorMapper.MapToEntity(dto, value);
            default -> throw new IllegalStateException("Unexpected feld" + field);
        }
    }

    public void createSector(String endPoint, String bearerToken) {
        System.out.println("DEBUG: TOKEN RECEBIDO NO SERVICE: " + bearerToken);
        String url = getUrl(endPoint);
        String bodyToSend = gson.toJson(sectorModel);
        response = given()
                .header("Authorization", "Bearer " + bearerToken)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(bodyToSend)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }
    /*public void createSector(String endPoint, String bearerToken) {
        System.out.println("DEBUG: TOKEN RECEBIDO NO SERVICE: " + bearerToken);
        String url = getUrl(endPoint);
        String bodyToSend = gson.toJson(sectorModel);

        System.out.println("DEBUG: Sector Creation URL: " + url);
        System.out.println("DEBUG: Sector Creation Body: " + bodyToSend);
        System.out.println("DEBUG: Authorization Header: Bearer " + bearerToken);

        response = given()
                .header("Authorization", "Bearer " + bearerToken)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(bodyToSend)
                .when()
                .post(url)
                .then()
                .log().all() // Log complete request and response
                .extract()
                .response();

        System.out.println("DEBUG: Sector Creation Response Status: " + response.statusCode());
        System.out.println("DEBUG: Sector Creation Response Body: " + response.getBody().asString());
    }*/

    public void setContract(String contract) throws IOException {
        switch (contract) {
            case "Cadastro bem-sucedido de setor" -> jsonSchema = loadJsonFromFile(schemasPath + "cadastro-bem-sucedido-de-setor.json");
            default -> throw new IllegalStateException("Unexpected contract" + contract);
        }
    }

    public Set<ValidationMessage> validateResponseAgainstSchema() throws IOException, JSONException {
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema schema = schemaFactory.getSchema(jsonSchema.toString());
        JsonNode jsonResponseNode = mapper.readTree(jsonResponse.toString());
        Set<ValidationMessage> schemaValidationErrors = schema.validate(jsonResponseNode);
        return schemaValidationErrors;
    }

    private JSONObject loadJsonFromFile(String filePath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            // Read the entire file content as a string
            String content = new String(inputStream.readAllBytes());
            JSONTokener tokener = new JSONTokener(content);
            return new JSONObject(tokener);
        } catch (JSONException e) {
            throw new RuntimeException("Failed to parse JSON from file: " + filePath, e);
        }
    }

    private String getUrl(String endPoint){
        return baseUrl + endPoint;
    }
}
