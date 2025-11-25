**Propósito**
- **Resumo:**: Este repositório é uma aplicação Spring Boot demonstrativa chamada `spring-login-demo` que expõe um formulário de login (`src/main/resources/templates/login.html`) e um endpoint `POST /login` implementado em `src/main/java/com/example/spring_login_demo/controller/LoginController.java`.

**Visão Geral / Arquitetura**
- **Frameworks:**: Spring Boot 4, Spring Security, Spring Data JPA, Thymeleaf.
- **Camadas:**: `controller` (endpoints HTTP), `model` (entidades JPA — ver `User.java`), `templates` (Thymeleaf HTML). Não há repositório explícito no momento; a persistência usa H2 em arquivo (`data/demo-db`).
- **Fluxo principal:**: Usuário submete formulário em `templates/login.html` → `POST /login` em `LoginController` processa senha usando `BCryptPasswordEncoder` e retorna um hash (implementação de demonstração, não autenticação completa).

**Arquivos-chave (referência rápida)**
- **`pom.xml`**: dependências e Java 17.
- **`src/main/java/com/example/spring_login_demo/SpringLoginDemoApplication.java`**: ponto de entrada da aplicação.
- **`src/main/java/com/example/spring_login_demo/controller/LoginController.java`**: endpoint `POST /login` (hash de senha de demonstração).
- **`src/main/java/com/example/spring_login_demo/model/User.java`**: entidade JPA (id, username, passwordHash).
- **`src/main/resources/application.properties`**: configurações — servidor na porta `3335`, H2 em arquivo (`jdbc:h2:file:./data/demo-db`) e console H2 em `/h2-console`.

**Como construir/rodar/testar (comandos concretos)**
- **Build:**: `./mvnw clean package` (usa o wrapper existente). Alternativa: `mvn -U clean package` se preferir Maven instalado globalmente.
- **Testes:**: `./mvnw test`  (testes JUnit existentes em `test/java/...`).
- **Executar jar:**: `java -jar target/spring-login-demo-0.0.1-SNAPSHOT.jar` — a aplicação usará `server.port=3335` por padrão.
- **Acesso H2:**: após iniciar, abra `http://localhost:3335/h2-console` e use `jdbc:h2:file:./data/demo-db`, usuário `sa`, senha vazia conforme `application.properties`.

**Padrões e convenções do projeto**
- **Java 17**: código compatível com Java 17 conforme `pom.xml`.
- **Pacote base:**: `com.example.spring_login_demo` — novos componentes devem seguir este namespace.
- **Arquivos de template:**: colocados em `src/main/resources/templates` (Thymeleaf). Os formulários postam para endpoints em `controller`.
- **Persistência:**: usa Spring Data JPA + H2 runtime; dados locais são mantidos em `data/` (não comitar DB binário).

**Integrações e dependências externas**
- **H2 (local):** arquivo em `./data/demo-db` — útil para dev; o projeto não configura bancos externos por padrão.
- **Spring Security:** está presente como dependência, mas a autenticação real não está implementada — `LoginController` apenas demonstra hashing de senha.

**Orientações específicas para agentes de codificação**
- **Modificar endpoints:**: atualize `controller/LoginController.java` e adicione testes em `test/java/com/example/spring_login_demo/` para cobrir novos comportamentos.
- **Alterar modelo/persistência:**: adicionar interfaces `Repository` sob um pacote `repository` se for necessária persistência real (ex.: `UserRepository extends JpaRepository<User,Long>`). Ver `model/User.java` para a entidade.
- **CUIDADO — senhas:**: nunca logue ou persista senhas em texto claro. Use `BCryptPasswordEncoder` como demonstrado e armazene apenas hashes em `User.passwordHash`.
- **Porta e ambiente:**: a aplicação roda por padrão em `3335`. Ao testar localmente, garanta porta livre ou ajuste `server.port` em `application.properties`.

**Exemplos rápidos (úteis para pull requests)**
- **Rodar a app localmente antes de PR:**
  - `./mvnw clean package`
  - `java -jar target/spring-login-demo-0.0.1-SNAPSHOT.jar`
  - Abra `http://localhost:3335` para ver o formulário e `http://localhost:3335/h2-console` para DB.
- **Rodar apenas testes:**: `./mvnw test`

**Onde procurar quando algo falha**
- **Build/test falhando:**: `pom.xml` e dependências; logs de erro do Maven mostram versões. Use `./mvnw -e test` para stack traces completos.
- **Problemas de DB H2:**: verificar `data/` (arquivo DB) e `application.properties` para a URL do datasource.

**Solicitação de feedback**
- Se algo importante do fluxo de desenvolvimento estiver faltando (ex.: CI, regras de lint, convenções de commit), diga quais áreas quer detalhar e atualizo este arquivo.
