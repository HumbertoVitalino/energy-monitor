# language: pt
Funcionalidade: Autenticação de usuário
  Como usuário da API
  Quero conseguir um token de acesso
  Para que eu possa acessar as demais funcionalidades do sistema

  Cenário: Cadastro bem-sucedido de usuário
    Dado que eu tenha os seguintes dados de usuário:
      | campo          | valor                  |
      | name           | maria Alves          |
      | email          | roberta.alvesmaria@gmail.com|
      | password       | 12345678              |
      | role           | ADMIN                  |
    Quando eu enviar a requisição para o endpoint "/auth/register" de cadastro de usuário
    Então o status code da resposta deve ser 201

  Cenário: Deve ser possível acessar o sistema
    Dado que eu tenha os seguintes dados de usuário:
      | campo          | valor                  |
      | email          | roberta.alvesmaria@gmail.com|
      | password       | 12345678                 |
    Quando eu enviar a requisição para o endpoint "/auth/login" de autenticação
    Então o status code da resposta deve ser 200
    E o token retornado deve ser válido e não expirado