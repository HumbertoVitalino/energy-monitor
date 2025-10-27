package br.com.fiap.EnergyMonitor.services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class EquipmentService {

    // Getters e Setters
    @Setter
    private String nome;
    @Setter
    private Double consumoPorHora;
    @Setter
    private Long sectorId;
    @Setter
    private Boolean ativo;
    @Setter
    private Integer maxActiveHours;
    private String token;
    private Response response;
    private String baseUrl = "http://localhost:8080";

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getConsumoPorHora() {
        return consumoPorHora;
    }

    public void setConsumoPorHora(Double consumoPorHora) {
        this.consumoPorHora = consumoPorHora;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getMaxActiveHours() {
        return maxActiveHours;
    }

    public void setMaxActiveHours(Integer maxActiveHours) {
        this.maxActiveHours = maxActiveHours;
    }

    public String getToken() {
        return token;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public Response getResponse() {
        return response;
    }

    public int getStatusCode() {
        return response != null ? response.getStatusCode() : -1;
    }


    public String getMensagemErro() {
        if (response == null) return "";

        try {
            // Para status 403 (Forbidden)
            if (response.getStatusCode() == 403) {
                return "Acesso negado";
            }

            // Para erros de validação (400)
            String responseBody = response.getBody().asString();
            if (responseBody.contains("maxActiveHours")) {
                return "Max active hours is required";
            }
            if (responseBody.contains("name") && responseBody.contains("required")) {
                return "Name is required";
            }
            if (responseBody.contains("sectorId") && responseBody.contains("required")) {
                return "Sector ID is required";
            }
            if (responseBody.contains("consumptionPerHour") && responseBody.contains("negative")) {
                return "Consumo não pode ser negativo";
            }

            // Tentar extrair mensagem genérica
            return response.jsonPath().getString("message");
        } catch (Exception e) {
            return response.getBody().asString();
        }
    }

    public String getResponseBody() {
        return response != null ? response.getBody().asString() : "";
    }

    public void setField(String campo, String valor) {
        switch (campo) {
            case "nome" -> setNome(valor);
            case "consumoPorHora" -> setConsumoPorHora(Double.valueOf(valor));
            case "setorId" -> setSectorId(Long.valueOf(valor));
            case "ativo" -> setAtivo(Boolean.valueOf(valor));
            case "maxActiveHours" -> setMaxActiveHours(Integer.valueOf(valor));
            default -> throw new IllegalArgumentException("Campo não reconhecido: " + campo);
        }
    }

    public void criarEquipamento(String endpoint) throws JSONException {
        String url = baseUrl + endpoint;

        JSONObject requestBody = new JSONObject();

        if (nome != null) {
            requestBody.put("name", nome);
        }
        if (consumoPorHora != null) {
            requestBody.put("consumptionPerHour", consumoPorHora);
        }
        if (sectorId != null) {
            requestBody.put("sectorId", sectorId);
        }
        if (ativo != null) {
            requestBody.put("active", ativo);
        } else {
            requestBody.put("active", true); // Valor padrão
        }

        // ⭐ ADICIONAR maxActiveHours COM VALOR PADRÃO SE NÃO FOR ESPECIFICADO
        if (maxActiveHours != null) {
            requestBody.put("maxActiveHours", maxActiveHours);
        } else {
            requestBody.put("maxActiveHours", 8);
        }

        System.out.println("=== DEBUG EQUIPMENT CREATION ===");
        System.out.println("Nome: " + nome);
        System.out.println("Consumo: " + consumoPorHora);
        System.out.println("Ativo: " + ativo);
        System.out.println("MaxHours: " + maxActiveHours);
        System.out.println("SectorId: " + sectorId);
        System.out.println("Request Body: " + requestBody.toString(2));
        System.out.println("Token: " + (token != null ? "PRESENTE" : "AUSENTE"));
        System.out.println("================================");

        if (token != null) {
            response = given()
                    .header("Authorization", "Bearer " + token)
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(requestBody.toString())
                    .when()
                    .post(url)
                    .then()
                    .extract()
                    .response();
        } else {
            response = given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(requestBody.toString())
                    .when()
                    .post(url)
                    .then()
                    .extract()
                    .response();
        }

        System.out.println("DEBUG: Response Status: " + response.getStatusCode());
        System.out.println("DEBUG: Response Body: " + response.getBody().asString());
    }

    public boolean validarSchemaResposta() {
        try {
            if (response == null || response.getStatusCode() != 201) {
                return false;
            }

            String responseBody = response.getBody().asString();


            boolean schemaValido = responseBody != null &&
                    responseBody.contains("\"id\"") &&
                    responseBody.contains("\"name\"") &&
                    responseBody.contains("\"consumptionPerHour\"") &&
                    responseBody.contains("\"active\"") &&
                    responseBody.contains("\"maxActiveHours\"");

            System.out.println("DEBUG: Schema validation result: " + schemaValido);
            return schemaValido;

        } catch (Exception e) {
            System.err.println("Erro ao validar schema: " + e.getMessage());
            return false;
        }
    }

    // Método adicional para buscar equipamento por ID
    public void buscarEquipamentoPorId(Long equipamentoId) {
        String url = baseUrl + "/api/equipment/" + equipamentoId;

        if (token != null) {
            response = given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .get(url)
                    .then()
                    .extract()
                    .response();
        } else {
            response = given()
                    .when()
                    .get(url)
                    .then()
                    .extract()
                    .response();
        }
    }

    // Método para listar todos equipamentos
    public void listarEquipamentos() {
        String url = baseUrl + "/api/equipment";

        if (token != null) {
            response = given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .get(url)
                    .then()
                    .extract()
                    .response();
        } else {
            response = given()
                    .when()
                    .get(url)
                    .then()
                    .extract()
                    .response();
        }
    }

    // Método para atualizar equipamento
    public void atualizarEquipamento(Long equipamentoId, JSONObject dadosAtualizacao) {
        String url = baseUrl + "/api/equipment/" + equipamentoId;

        if (token != null) {
            response = given()
                    .header("Authorization", "Bearer " + token)
                    .contentType(ContentType.JSON)
                    .body(dadosAtualizacao.toString())
                    .when()
                    .put(url)
                    .then()
                    .extract()
                    .response();
        }
    }
}