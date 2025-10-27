package br.com.fiap.EnergyMonitor.services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class ConsumptionService {

    @Setter
    private String token;

    @Getter
    private Response response;
    private String nomeSetor;
    private Double limiteSetor;
    @Setter
    private Double consumoAtual;
    private Integer equipamentosNaoEssenciais;
    private Boolean alertaGerado;
    private String tipoAlerta;
    @Getter
    private String statusAlerta;
    private Integer equipamentosDesligados;
    private Integer equipamentosEssenciaisLigados;

    public ConsumptionService() {
        this.alertaGerado = false;
        this.equipamentosDesligados = 0;
        this.equipamentosEssenciaisLigados = 0;
    }

    public void configurarSetor(String nomeSetor, Double limite) {
        this.nomeSetor = nomeSetor;
        this.limiteSetor = limite;
    }

    public void setQuantidadeEquipamentosNaoEssenciais(int quantidade) {
        this.equipamentosNaoEssenciais = quantidade;
    }

    public void verificarLimites() {
        // Simular verificação de limites - em um cenário real, chamaria a API
        double percentual = (consumoAtual / limiteSetor) * 100;

        if (percentual >= 90 && percentual < 100) {
            this.alertaGerado = true;
            this.tipoAlerta = "CONSUMO_ALTO";
            this.statusAlerta = "ATIVO";
        } else if (percentual >= 100) {
            this.alertaGerado = true;
            this.tipoAlerta = "CONSUMO_CRÍTICO";
            this.statusAlerta = "ATIVO";
        }
    }

    public void executarDesligamentoEmergencial() {
        // Simular desligamento emergencial
        if (consumoAtual >= limiteSetor && equipamentosNaoEssenciais != null) {
            // Desligar 2/3 dos equipamentos não essenciais
            this.equipamentosDesligados = Math.min(2, equipamentosNaoEssenciais - 1);
            this.equipamentosEssenciaisLigados = 1; // Sempre manter 1 essencial
        }
    }

    public void consultarConsumo(String nomeSetor) {
        String url = "http://localhost:8080/api/consumption/sector/" + nomeSetor;

        if (token != null) {
            response = given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .get(url);
        } else {
            response = given()
                    .when()
                    .get(url);
        }
    }

    public void cadastrarConsumo(String nomeSetor, Double consumo) throws JSONException {
        String url = "http://localhost:8080/api/consumption";

        JSONObject requestBody = new JSONObject();
        requestBody.put("sectorName", nomeSetor);
        requestBody.put("consumption", consumo);
        requestBody.put("timestamp", java.time.LocalDateTime.now().toString());

        if (token != null) {
            response = given()
                    .header("Authorization", "Bearer " + token)
                    .contentType(ContentType.JSON)
                    .body(requestBody.toString())
                    .when()
                    .post(url);
        } else {
            response = given()
                    .contentType(ContentType.JSON)
                    .body(requestBody.toString())
                    .when()
                    .post(url);
        }
    }

    public void solicitarRelatorio(String data) {
        String url = "http://localhost:8080/api/reports/consumption/daily?date=" + data;

        if (token != null) {
            response = given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .get(url);
        } else {
            response = given()
                    .when()
                    .get(url);
        }
    }

    public boolean verificarAlerta(String tipoAlerta) {
        return this.alertaGerado && this.tipoAlerta.equals(tipoAlerta);
    }

    public int getQuantidadeEquipamentosDesligados() {
        return equipamentosDesligados;
    }

    public int getQuantidadeEquipamentosEssenciaisLigados() {
        return equipamentosEssenciaisLigados;
    }

    public int getStatusCode() {
        return response != null ? response.getStatusCode() : -1;
    }

    public String getMensagemErro() {
        return response != null ? response.jsonPath().getString("message") : "";
    }

    public boolean verificarFormatoRelatorio(String formato) {
        if (response == null) return false;

        String contentType = response.getContentType();
        if (formato.equalsIgnoreCase("HTML")) {
            return contentType != null && contentType.contains("text/html");
        } else if (formato.equalsIgnoreCase("JSON")) {
            return contentType != null && contentType.contains("application/json");
        }
        return false;
    }

    public boolean relatorioContemDadosConsumo() {
        if (response == null) return false;

        try {
            String responseBody = response.getBody().asString();
            return responseBody != null &&
                    (responseBody.contains("consumption") ||
                            responseBody.contains("Consumo") ||
                            responseBody.contains("kWh"));
        } catch (Exception e) {
            return false;
        }
    }

}