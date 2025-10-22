
# language: pt
Funcionalidade: Cadastro de novo usuário
  Como usuário da API
  Quero cadastrar um novo usuário
  Para que o registro seja salvo corretamente no sistema
  Cenário: Cadastro bem-sucedido de usuário
    Dado que eu tenha os seguintes dados da entrega:
      | campo          | valor                  |
      | name           | Rodrigo Alves          |
      | email          | rodrigo.alves@gmail.com|
      | password       | 123456                 |
      | role           | ADMIN                  |
    Quando eu enviar a requisição para o endpoint "/auth/register" de cadastro de usuário
    Então o status code da resposta deve ser 201