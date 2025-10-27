# language: pt
Funcionalidade: Monitoramento de consumo energético
  Como sistema de monitoramento
  Eu quero controlar e alertar sobre consumo
  Para prevenir gastos excessivos

  Cenário: Sistema deve alertar ao atingir 90% do limite
    Dado que o setor "Produção" tem limite 1000 kWh
    E o consumo atual é 900 kWh
    Quando o sistema verificar os limites
    Então deve gerar alerta de "CONSUMO_ALTO"
    E o status do alerta deve ser "ATIVO"

  Cenário: Sistema deve bloquear equipamentos ao atingir 100% do limite
    Dado que o setor "Produção" tem limite 1000 kWh
    E o consumo atual é 1000 kWh
    E existem 3 equipamentos não essenciais
    Quando o sistema executar desligamento emergencial
    Então 2 equipamentos devem ser desligados
    E 1 equipamento essencial deve permanecer ligado

  Cenário: Consulta de consumo sem autenticação
    Dado que não estou autenticado
    Quando eu consultar o consumo do setor "Produção"
    Então o status code da resposta de consumo deve ser 403