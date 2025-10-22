package br.com.fiap.EnergyMonitor.steps;

import br.com.fiap.EnergyMonitor.model.UserErrorMessageModel;
import br.com.fiap.EnergyMonitor.services.CreateUserService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class CreateUserSteps {
    CreateUserService createUserService = new CreateUserService();

    @Dado("que eu tenha os seguintes dados de usuário:")
    public void queEuTenhaOsSeguintesDadosDeUsuário(List<Map<String, String>> rows) {
        for(Map<String, String> columns : rows){
            createUserService.setFieldsDelivery(columns.get("campo"), columns.get("valor"));
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de usuário")
    public void euEnviarARequisiçãoParaOEndpointDeCadastroDeUsuário(String endpoint) {
        createUserService.createUser(endpoint);
    }

    @Então("o status code da resposta deve ser {int}")
    public void oStatusCodeDaRespostaDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, createUserService.response.statusCode());
    }

    @E("o corpo de resposta de erro da api deve retornar a mensagem {string}")
    public void oCorpoDeRespostaDeErroDaApiDeveRetornarAMensagem(String message) {
        UserErrorMessageModel userErrorMessageModel = createUserService.gson.fromJson(
          createUserService.response.jsonPath().prettify(), UserErrorMessageModel.class);
        Assert.assertEquals(message, userErrorMessageModel.getMessage());
    }

    @Quando("eu enviar a requisição para o endpoint {string} de autenticação")
    public void euEnviarARequisiçãoParaOEndpointDeAutenticação(String endpoint) {
        createUserService.acessUser(endpoint);
    }

    @E("o token retornado deve ser válido e não expirado")
    public void oTokenRetornadoDeveSerValidoENaoExpirado() {
        createUserService.verifyToken();
    }
}
