# Usability Validation Tool :: Backend

Projeto de desenvolvimento do módulo _backend_ da aplicação **Usability Validation Tool**  implementado em linguagem de programação Java, com o objetivo de prover as funcionalidades necessárias para o módulo _frontend_ implementado no framework Angular.

```
                    :: Usability Validation Tool ::

┌────────────┐       ┌───┬─────────┐       ┌──────────────┐
│            │       │ R │         │       │              │
│  Frontend  │ <===> │ E │ Backend │ <===> │      BD      │
│  (Angular) │       │ S │ (Java)  │       │ (PostgreSQL) │
│            │       │ T │         │       │              │
└────────────┘       └───┴─────────┘       └──────────────┘
```

Este projeto usa as seguintes tecnologias:

- [Java 11](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
- [Sprint Boot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/)
- [Spring Data](https://spring.io/projects/spring-data)
  ->  [Java Persistence is the API(JPA)](https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html)
  ->  [Hibernate](https://hibernate.org/)
  ->  [PostgreSQL](https://www.postgresql.org/)
- [Lombok](https://projectlombok.org/)
- [Spring Security](https://spring.io/projects/spring-security)
  ->  [JWT](https://jwt.io/)
- [Swagger](https://springfox.github.io/springfox/docs/snapshot/)

## 1- Ferramentas e Configurações

### 1.1- Eclipse

**Instalação**

Baixar e descompactar um dos pacotes (Win ou Linux 64-bit) da versão `Eclipse IDE for Java Developers` que pode ser encontrado [aqui](https://www.eclipse.org/downloads/packages/).

**Importação do Projeto (Git)**

Após abrir o Eclipse, importar projeto do Git:

1. _File_ -> _Import..._ -> _Projects from Git_ -> _Clone URI_
2. URI: `git@github.com:rodrigorjsf/tcc-usability-backend.git` -> _Next_
3. Escolher _branch_ `master`
4. Local destination: _pasta-do-workspace_
5. _Import as general project_ -> _Next_ -> _Finish_

**Configuração do Projeto**

1. Clicar com botão direito no projeto -> _Configure_ -> _Convert to Maven project_
2. Clicar com botão direito no projeto -> _Maven_ -> _Update Project..._
3. OK (incluir opção `Force Update os Snapshots/Releases`)

Incluir `Run Configurations`:

1. _**Maven Build**_ -> New launch configuration -> Name: `tcc-usability-backend [clean package]`, Base directory: `tcc-usability-backend`, Goals: `clean package`
2. _**Java Application**_ -> New launch configuration -> Spring Boot, Project: `tcc-usability-backend`, Main class: `com\unicap\tcc\usability\api\ApiApplication`

### 1.2- pgAdmin

Para baixar a ferramenta pgAdmin, clique [aqui](https://www.pgadmin.org/).

**Banco de Dados(Heroku):**

1. host: `ec2-52-202-22-140.compute-1.amazonaws.com`
2. port: `5432`
3. database: `d7gjnr6jclad2i`

## 2- Arquitetura do Sistema

Este projeto segue um padrão arquitetural em camadas [[1](https://www.oreilly.com/library/view/software-architecture-patterns/9781491971437/ch01.html),[2](https://en.wikipedia.org/wiki/Multitier_architecture)] para fornecer uma API REST [[3](https://dzone.com/articles/intro-rest),[4](https://www.quora.com/What-are-RESTful-APIs-and-how-do-they-work),[5](https://blog.caelum.com.br/rest-principios-e-boas-praticas/)] ao módulo _frontend_ da aplicação. A camada mais externa do sistema (_Resources_) implementa os serviços REST (JAX-RS), tendo esta camada a responsabilidade de validar os dados de entrada, assim como realizar as restrições de segurança necessárias (autenticação/autorização) no acesso aos serviços disponibilizados. A camada imediatamente abaixo (_Service_) é responsável pela execução da lógica de negócio de cada serviço, que incluem validações de negócio e transformação dos dados de entrada antes de atualizá-los. A última camada (_Repository_) do modelo é responsável pelas funcionalidades diretas de acesso/atualização dos dados do sistema no banco de dados.

```
┌───────────────────┐
│  Resources (REST) │
├───────────────────┤
│    Service        │ 
├───────────────────┤
│ Repository  (DAO) │ SPRING DATA, JPA, HIBERANTE
└───────────────────┘
         ||
     ┌────────┐
     │   BD   │
     └────────┘
```

O projeto foi desenvolvido usando a ferramenta [Spring Boot](https://spring.io/projects/spring-boot).

A organização e significado de cada um dos pacotes do projeto segue abaixo:

```
src
├── main
│   ├── java/com/unicap/tcc/usability/api
│   │   ├── config                                      -> classes de configurações
│   │   ├── exception                                   -> classes de exceções
│   │   ├── models                                      -> classes de entidades/pojos/dtos
│   │   ├── properties                                  -> classes de propriedades da aplicação
│   │   ├── repository                                  -> classes da camada de persistência
│   │   ├── resources                                   -> classes da camada de serviço
│   │   ├── security                                    -> classes da camada de segurança (JWT)
│   │   ├── service                                     -> classes da camada de negócio
│   │   ├── utils                                       -> classes utilitárias
│   │   └── validators                                  -> classes validadoras
│   ├── resources
│   │   ├── application.yml                             -> arquivos de propriedades do sistema
└── test
```

## 3- Geração da Build

### 3.1- Gerar a build do sistema

```sh
$ mvn clean package
```
