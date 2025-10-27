# language: pt
Funcionalidade: Cadastro de setor
  Como usuário da API
  Quero conseguir cadastrar um novo setor
  Para que o registro seja salvo corretamente no sistema

  Cenário: Cadastro bem-sucedido de setor
    Dado que eu tenha um usuário autenticado
    E que eu tenha os seguintes dados de setor:
      | campo           | valor                  |
      | name            | tv                     |
      | consumptionLimit| 50.0                   |
    Quando eu enviar a requisição para o endpoint "/api/sector" de cadastro de setor
    Então o status code da resposta de setor deve ser 201
    E que o arquivo de contrato esperado é o "Cadastro bem-sucedido de setor"
    Entao a resposta da requisição deve estar em conformidade com o contrato selecionado