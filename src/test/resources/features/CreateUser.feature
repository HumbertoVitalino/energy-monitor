
# language: pt
Funcionalidade: Cadastro de novo usuário
  Como usuário da API
  Quero conseguir cadastrar um novo usuário
  Para que o registro seja salvo corretamente no sistema
  Cenário: Cadastro bem-sucedido de usuário
    Dado que eu tenha os seguintes dados de usuário:
      | campo          | valor                  |
      | name           | Rodrigo Alves          |
      | email          | rodrigo.alves@gmail.com|
      | password       | 123456                 |
      | role           | ADMIN                  |
    Quando eu enviar a requisição para o endpoint "/auth/register" de cadastro de usuário
    Então o status code da resposta deve ser 201

  Cenário: Cadastro de usuário sem sucesso ao passar o campo role inválido
    Dado que eu tenha os seguintes dados de usuário:
      | campo          | valor                  |
      | name           | Rodrigo Alves          |
      | email          | rodrigo.alves.com|
      | password       | 123456                 |
      | role           | ADMIN                  |
    Quando eu enviar a requisição para o endpoint "/auth/register" de cadastro de usuário
    Então o status code da resposta deve ser 400
    E o corpo de resposta de erro da api deve retornar a mensagem "Dados fornecidos estão em formato inválido"