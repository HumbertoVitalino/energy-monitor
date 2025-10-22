package br.com.fiap.EnergyMonitor.steps;

import br.com.fiap.EnergyMonitor.services.CreateUserService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class CreateUserSteps {
    CreateUserService createUserService = new CreateUserService();

    @Dado("que eu tenha os seguintes dados da entrega:")
    public void queEuTenhaOsSeguintesDadosDaEntrega(List<Map<String, String>> rows) {
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
}
