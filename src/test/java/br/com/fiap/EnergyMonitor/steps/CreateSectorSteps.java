package br.com.fiap.EnergyMonitor.steps;

import br.com.fiap.EnergyMonitor.services.CreateSectorService;
import br.com.fiap.EnergyMonitor.services.CreateUserService;
import com.networknt.schema.ValidationMessage;
import io.cucumber.java.pt.*;
import org.json.JSONException;
import org.junit.Assert;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CreateSectorSteps {

    CreateUserService createUserService = new CreateUserService();
    CreateSectorService createSectorService = new CreateSectorService();


    @Dado("que eu tenha os seguintes dados de setor:")
    public void queEuTenhaOsSeguintesDadosDeSetor(List<Map<String, String>> rows) {
        for(Map<String, String> columns : rows){
            createSectorService.setFieldsDelivery(columns.get("campo"), columns.get("valor"));
        }
    }


    @Dado("que eu tenha um usuário autenticado")
    public void queEuTenhaUmUsuárioAutenticado() {
        /*createUserService.setFieldsDelivery("name", "Douglas");
        createUserService.setFieldsDelivery("email", "douglas@gmail.com");
        createUserService.setFieldsDelivery("password", "123456");
        createUserService.setFieldsDelivery("role", "ADMIN");
        createUserService.createUser("/auth/register");*/
        // Then login to get the token
        //Precisa ser os dados de um usuário já cadastrado para funcionar
        createUserService.setFieldsDelivery("email", "douglas@gmail.com");
        createUserService.setFieldsDelivery("password", "123456");
        createUserService.acessUser("/auth/login");

        Assert.assertNotNull("Token não foi gerado", createUserService.getGeneratedToken());
    }

    @Quando("eu enviar a requisição para o endpoint {string} de cadastro de setor")
    public void euEnviarARequisiçãoParaOEndpointDeCadastroDeSetor(String endpoint) {
        String token = createUserService.getGeneratedToken();
        createSectorService.createSector(endpoint, token);
    }

    @E("que o arquivo de contrato esperado é o {string}")
    public void queOArquivoDeContratoEsperadoÉO(String contract) throws IOException {
        createSectorService.setContract(contract);
    }

    @Entao("a resposta da requisição deve estar em conformidade com o contrato selecionado")
    public void aRespostaDaRequisiçãoDeveEstarEmConformidadeComOContratoSelecionado() throws JSONException, IOException {
        Set<ValidationMessage> validateResponse = createSectorService.validateResponseAgainstSchema();
        Assert.assertTrue("O contrato está inválido. Erros encontrados: " + validateResponse, validateResponse.isEmpty());
    }

    @Então("o status code da resposta de setor deve ser {int}")
    public void oStatusCodeDaRespostaDeSetorDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, createSectorService.response.statusCode());
    }
}
