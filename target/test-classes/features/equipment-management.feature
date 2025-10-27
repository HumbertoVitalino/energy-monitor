

# language: pt

Funcionalidade: Gerenciamento de equipamentos energéticos
  Como administrador do sistema
  Eu quero gerenciar equipamentos energéticos
  Para controlar o consumo de energia por setor

  Cenário: Cadastrar equipamento com sucesso
    Dado que eu tenha um usuário autenticado como "ADMIN"
    E exista um setor "Produção" cadastrado
    Quando eu cadastrar um equipamento com:
      | campo           | valor            |
      | nome            | Ar Condicionado  |
      | consumoPorHora  | 2.5              |
      | ativo           | true             |
      | maxActiveHours  | 8                |
    Então o equipamento deve ser criado com status 201
    E debug detalhado da resposta
    E o equipamento deve estar "ATIVO"
    E o equipamento deve ter consumo de 2.5 kWh por hora
    E o corpo da resposta deve conter o ID do equipamento
    E a resposta deve estar em conformidade com o schema de equipamento

  Cenário: Cadastrar equipamento com consumo zero
    Dado que eu tenha um usuário autenticado como "ADMIN"
    E exista um setor "Administrativo" cadastrado
    Quando eu cadastrar um equipamento com:
      | campo           | valor            |
      | nome            | Lâmpada LED      |
      | consumoPorHora  | 0.0              |
      | ativo           | true             |
      | maxActiveHours  | 24               |
    Então o equipamento deve ser criado com status 201
    E o equipamento deve ter consumo de 0.0 kWh por hora

  Cenário: Tentar cadastrar equipamento sem autenticação
    Dado que eu não esteja autenticado
    Quando eu tentar cadastrar um equipamento
    Então o status code da resposta de equipamento deve ser 403
    E a mensagem de erro de equipamento deve ser "Acesso negado"

  Cenário: Tentar cadastrar equipamento com consumo negativo
    Dado que eu tenha um usuário autenticado como "ADMIN"
    E que eu tente cadastrar um equipamento com consumo negativo
    Quando eu cadastrar um equipamento com:
      | campo           | valor            |
      | nome            | Ventilador       |
      | consumoPorHora  | -1.5             |
      | maxActiveHours  | 8                |
    Então debug detalhado da resposta
    E o status code da resposta de equipamento deve ser 400
    E a mensagem de erro de equipamento deve ser "Consumo não pode ser negativo"
    E a API deve rejeitar o consumo negativo

  Cenário: Tentar cadastrar equipamento sem nome
    Dado que eu tenha um usuário autenticado como "ADMIN"
    E exista um setor "Produção" cadastrado
    Quando eu cadastrar um equipamento com:
      | campo           | valor            |
      | consumoPorHora  | 1.5              |
      | setorId         | 1                |
      | maxActiveHours  | 8                |
    Então o status code da resposta de equipamento deve ser 400
    E a mensagem de erro de equipamento deve ser "Name is required"

  Cenário: Tentar cadastrar equipamento sem setor
    Dado que eu tenha um usuário autenticado como "ADMIN"
    Quando eu tentar cadastrar um equipamento sem setor
    Então o status code da resposta de equipamento deve ser 400
    E a mensagem de erro de equipamento deve ser "Sector ID is required"

  Cenário: Cadastrar equipamento desligado
    Dado que eu tenha um usuário autenticado como "ADMIN"
    E exista um setor "Manutenção" cadastrado
    Quando eu cadastrar um equipamento com:
      | campo           | valor            |
      | nome            | Gerador Reserva  |
      | consumoPorHora  | 5.0              |
      | ativo           | false            |
      | maxActiveHours  | 12               |
    Então o equipamento deve ser criado com status 201
    E o equipamento deve estar "DESLIGADO"

  Cenário: Cadastrar múltiplos equipamentos para o mesmo setor
    Dado que eu tenha um usuário autenticado como "ADMIN"
    E exista um setor "TI" cadastrado
    Quando eu cadastrar um equipamento com:
      | campo           | valor            |
      | nome            | Servidor 01      |
      | consumoPorHora  | 3.0              |
      | maxActiveHours  | 24               |
    E eu cadastrar um equipamento com:
      | campo           | valor            |
      | nome            | Servidor 02      |
      | consumoPorHora  | 2.5              |
      | maxActiveHours  | 24               |
    Então ambos equipamentos devem ser criados com status 201
    E ambos devem pertencer ao setor "TI"