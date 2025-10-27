package br.com.fiap.EnergyMonitor.steps;

import br.com.fiap.EnergyMonitor.services.ConsumptionService;
import br.com.fiap.EnergyMonitor.services.CreateUserService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.json.JSONException;
import org.junit.Assert;

public class ConsumptionSteps {

    private final ConsumptionService consumptionService = new ConsumptionService();
    private final CreateUserService userService = new CreateUserService();

    @Dado("que o setor {string} tem limite {double} kWh")
    public void configurarLimiteSetor(String nomeSetor, Double limite) {
        consumptionService.configurarSetor(nomeSetor, limite);
    }

    @Dado("o consumo atual é {double} kWh")
    public void configurarConsumoAtual(Double consumoAtual) {
        consumptionService.setConsumoAtual(consumoAtual);
    }

    @Dado("existem {int} equipamentos não essenciais")
    public void configurarEquipamentosNaoEssenciais(int quantidade) {
        consumptionService.setQuantidadeEquipamentosNaoEssenciais(quantidade);
    }

    @Dado("que eu tenha um token de administrador válido")
    public void obterTokenAdministrador() {
        userService.setFieldsDelivery("email", "admin@test.com");
        userService.setFieldsDelivery("password", "admin123");
        userService.acessUser("/auth/login");

        Assert.assertNotNull("Token de administrador não gerado", userService.getGeneratedToken());
        consumptionService.setToken(userService.getGeneratedToken());
    }

    @Dado("que não estou autenticado")
    public void semAutenticacao() {
        consumptionService.setToken(null);
    }

    @Quando("o sistema verificar os limites")
    public void verificarLimitesConsumo() {
        consumptionService.verificarLimites();
    }

    @Quando("o sistema executar desligamento emergencial")
    public void executarDesligamentoEmergencial() {
        consumptionService.executarDesligamentoEmergencial();
    }

    @Quando("eu consultar o consumo do setor {string}")
    public void consultarConsumoSetor(String nomeSetor) {
        consumptionService.consultarConsumo(nomeSetor);
    }

    @Quando("eu tentar cadastrar um consumo para o setor {string} com valor {double} kWh")
    public void cadastrarConsumoSetor(String nomeSetor, Double consumo) throws JSONException {
        consumptionService.cadastrarConsumo(nomeSetor, consumo);
    }

    @Quando("eu solicitar o relatório de consumo do dia {string}")
    public void solicitarRelatorioConsumo(String data) {
        consumptionService.solicitarRelatorio(data);
    }

    @Então("deve gerar alerta de {string}")
    public void verificarAlertaGerado(String tipoAlerta) {
        Assert.assertTrue("Alerta " + tipoAlerta + " não foi gerado",
                consumptionService.verificarAlerta(tipoAlerta));
    }

    @Então("o status do alerta deve ser {string}")
    public void verificarStatusAlerta(String status) {
        Assert.assertEquals("Status do alerta incorreto",
                status, consumptionService.getStatusAlerta());
    }

    @Então("{int} equipamentos devem ser desligados")
    public void verificarEquipamentosDesligados(int quantidadeEsperada) {
        int quantidadeDesligada = consumptionService.getQuantidadeEquipamentosDesligados();
        Assert.assertEquals("Quantidade de equipamentos desligados incorreta",
                quantidadeEsperada, quantidadeDesligada);
    }

    @Então("{int} equipamento essencial deve permanecer ligado")
    public void verificarEquipamentoEssencialLigado(int quantidadeEsperada) {
        int quantidadeLigada = consumptionService.getQuantidadeEquipamentosEssenciaisLigados();
        Assert.assertEquals("Quantidade de equipamentos essenciais ligados incorreta",
                quantidadeEsperada, quantidadeLigada);
    }

    @Então("o status code da resposta de consumo deve ser {int}")
    public void oStatusCodeDaRespostaDeConsumoDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, consumptionService.getStatusCode());
    }

    @Então("a mensagem de erro de consumo deve ser {string}")
    public void aMensagemDeErroDeConsumoDeveSer(String mensagemEsperada) {
        String mensagemAtual = consumptionService.getMensagemErro();
        Assert.assertEquals("Mensagem de erro incorreta",
                mensagemEsperada, mensagemAtual);
    }

    @Então("o relatório deve ser gerado em formato {string}")
    public void verificarFormatoRelatorio(String formato) {
        Assert.assertTrue("Relatório não foi gerado no formato " + formato,
                consumptionService.verificarFormatoRelatorio(formato));
    }

    @Então("o relatório deve conter dados de consumo")
    public void verificarDadosRelatorio() {
        Assert.assertTrue("Relatório não contém dados de consumo",
                consumptionService.relatorioContemDadosConsumo());
    }
}