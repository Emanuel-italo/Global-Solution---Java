# 🛰️ OrbitLink

> Plataforma de gestão da **Economia Espacial** — monitoramento de ativos orbitais, telemetria, manutenção e alertas operacionais.

API REST desenvolvida em **Java + Spring Boot** para a **Global Solution 2026/1 (FIAP — 2º ano de Análise e Desenvolvimento de Sistemas)**, conectando a exploração espacial a problemas reais da Terra: monitoramento climático, conectividade em regiões remotas, segurança orbital e suporte à tomada de decisão.

---

## 📑 Sumário

- [Visão geral](#-visão-geral)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Modelo de domínio](#-modelo-de-domínio)
- [Requisitos técnicos atendidos](#-requisitos-técnicos-atendidos)
- [Segurança e autenticação](#-segurança-e-autenticação)
- [Endpoints da API](#-endpoints-da-api)
- [Como executar](#-como-executar)
- [Como testar](#-como-testar)
- [Tratamento de erros](#-tratamento-de-erros)
- [Autores](#-autores)

---

## 🌌 Visão geral

A nova corrida tecnológica é a **economia espacial**. Satélites monitoram o clima, orientam o agronegócio, evitam desastres e conectam regiões remotas. O **OrbitLink** modela uma plataforma que dá suporte à operação desses ativos, oferecendo:

- Cadastro e ciclo de vida de **ativos espaciais** (satélites, sondas, CubeSats, estações-base e módulos orbitais).
- Registro de **telemetria** (clima, sinal e geolocalização) recebida dos ativos.
- Histórico de **manutenções orbitais** com custo estimado.
- Geração de **alertas orbitais** (risco de colisão, falha de comunicação, degradação, anomalia e alerta climático).
- Vínculo entre ativos e **missões espaciais** por meio de uma alocação com chave composta.

### Conexão com os ODS da ONU

| ODS | Como o projeto contribui |
|-----|--------------------------|
| **9** — Indústria, inovação e infraestrutura | Infraestrutura de software para operação de ativos orbitais |
| **11** — Cidades e comunidades sustentáveis | Telemetria e conectividade aplicadas a serviços terrestres |
| **13** — Ação contra a mudança do clima | Alertas climáticos e monitoramento via satélite |

---

## 🧰 Tecnologias

| Categoria | Ferramenta |
|-----------|------------|
| Linguagem | Java 17 |
| Framework | Spring Boot 3.4.5 |
| Web | Spring Web (REST) |
| Persistência | Spring Data JPA + Hibernate |
| Banco de dados | H2 (em memória) |
| Segurança | Spring Security + JWT (`java-jwt` / Auth0) |
| Validação | Bean Validation (Spring Validation) |
| Documentação | Swagger / OpenAPI (springdoc) |
| HATEOAS | Spring HATEOAS |
| Produtividade | Lombok, Spring Boot DevTools |
| Build | Maven |

---

## 🏛 Arquitetura

O projeto segue uma **arquitetura em camadas**, com responsabilidades bem separadas:

```
br.com.orbitlink.space
├── controller     → expõe a API REST (recebe e devolve apenas DTOs)
├── service        → regras de negócio e conversão entidade ↔ DTO
├── repository     → interfaces Spring Data JPA (JpaRepository)
├── entity         → mapeamento JPA do domínio
├── dto            → objetos de entrada (Request) e saída (Response) — Java Records
├── enums          → tipos e classificações do domínio
├── exception      → exceções de negócio + tratamento global
├── security       → autenticação, filtro JWT e configuração do Spring Security
└── config         → CORS e carga inicial de dados
```

### Fluxo de uma requisição

```
Cliente → Controller → Service → Repository (JpaRepository) → Banco H2
                ↑                        │
             DTO (Record)        Entidade JPA
```

A camada de **controller** nunca expõe entidades diretamente: tudo trafega via **DTOs** (`Request`/`Response`), implementados como **Java Records**. A conversão acontece na camada de **service**.

---

## 🗂 Modelo de domínio

### Entidades principais

| Entidade | Descrição |
|----------|-----------|
| `EquipamentoOrbital` | Superclasse abstrata (herança JOINED). Define `id`, `nomeProprietario` e `numeroDeSerie` |
| `AtivoEspacial` | Agregado raiz. Herda de `EquipamentoOrbital`. Representa satélites, sondas, etc. |
| `ManutencaoOrbital` | Histórico de intervenções e ajustes em órbita (N:1 com ativo) |
| `RegistroTelemetria` | Leituras de clima, sinal e geolocalização dos ativos (N:1 com ativo) |
| `AlertaOrbital` | Alertas de risco de colisão, falhas e degradação (N:1 com ativo) |
| `MissaoEspacial` | Missões às quais os ativos podem ser alocados |
| `AlocacaoAtivoMissao` | Associação **N:N** entre ativo e missão, com **chave composta** |
| `Usuario` | Credenciais de acesso à API (implementa `UserDetails`) |

### Componentes de modelagem avançada

- **Herança (JOINED):** `AtivoEspacial extends EquipamentoOrbital`.
- **Embedded:** `CoordenadasEspaciais` (`@Embeddable`) embarcada em `AtivoEspacial`.
- **Chave composta:** `AlocacaoAtivoMissao` usa `@EmbeddedId` (`AlocacaoAtivoMissaoId` com `ativoId` + `missaoId`) e `@MapsId` para derivar a chave das FKs.
- **Múltiplas tabelas e relacionamentos:** relacionamentos 1:N (ativo → manutenções/telemetrias/alertas) e N:N (ativo ↔ missão).

### Diagrama de relacionamentos (visão lógica)

```
         ┌────────────────────┐
         │ EquipamentoOrbital │  (abstrata, JOINED)
         └─────────▲──────────┘
                   │ herança
         ┌─────────┴──────────┐        ┌──────────────────┐
         │   AtivoEspacial    │◄───────┤ CoordenadasEspac. │ (@Embedded)
         └─────────┬──────────┘        └──────────────────┘
                   │ 1:N
   ┌───────────────┼───────────────┐
   ▼               ▼               ▼
┌──────────┐ ┌──────────────┐ ┌──────────────┐
│Manutencao│ │ Telemetria   │ │  Alerta      │
│ Orbital  │ │              │ │  Orbital     │
└──────────┘ └──────────────┘ └──────────────┘

  AtivoEspacial  ◄── N:N ──►  MissaoEspacial
            via AlocacaoAtivoMissao (@EmbeddedId)
```

---

## ✅ Requisitos técnicos atendidos

Mapeamento direto com os critérios da disciplina **Java Advanced** da Global Solution:

| Critério | Status | Onde está implementado |
|----------|:------:|------------------------|
| API REST com Spring Boot, camadas e fundamentos REST | ✔️ | `controller`, `service`, `repository` |
| Verbos HTTP, Request/Response e HTTP Status Code | ✔️ | `*Controller` (uso de `ResponseEntity`, `HttpStatus`) |
| HATEOAS | ✔️ | `AtivoEspacialController` (`EntityModel`, `linkTo`, `methodOn`) |
| Injeção de dependência, Lombok e DevTools | ✔️ | Construtores/`@Autowired`, `@Data`, dependência DevTools |
| Persistência com Spring Data JPA e `JpaRepository` | ✔️ | Todas as interfaces em `repository` |
| CRUD completo | ✔️ | Ativos, manutenções, telemetrias e alertas |
| DTOs e Java Records | ✔️ | Pacote `dto` (todos os DTOs são `record`) |
| Validação de entrada (Spring Validation) | ✔️ | `@Valid` + anotações nos `*Request` |
| Tratamento de exceções padronizado | ✔️ | `GlobalExceptionHandler` |
| Modelagem avançada: herança, chave composta, Embedded, múltiplas tabelas | ✔️ | `EquipamentoOrbital`, `AlocacaoAtivoMissao`, `CoordenadasEspaciais` |
| Spring Security + JWT | ✔️ | Pacote `security` |
| Documentação Swagger/OpenAPI | ✔️ | `@OpenAPIDefinition`, `@Operation`, `@Tag` |
| Configuração de CORS | ✔️ | `CorsConfig` |

> **Nota:** A camada de acesso a dados utiliza exclusivamente **Spring Data JPA** (`JpaRepository`), conforme exigido pela Global Solution. Consultas que exigem `join fetch` para evitar `LazyInitializationException` e o problema N+1 são definidas via `@Query` (JPQL) nas próprias interfaces de repositório.

---

## 🔐 Segurança e autenticação

A API é **stateless** e protegida por **JWT**:

1. O cliente envia `login` e `senha` em `POST /api/auth/login`.
2. O `AuthenticationManager` (via `DaoAuthenticationProvider` + `AutenticacaoService`) valida as credenciais comparando a senha com o hash **BCrypt**.
3. Em caso de sucesso, o `TokenService` gera um **token JWT** (validade de 2 horas).
4. Nas demais requisições, o cliente envia o token no header `Authorization: Bearer <token>`.
5. O `SecurityFilter` (`OncePerRequestFilter`) valida o token e popula o `SecurityContext`.

### Rotas públicas

- `POST /api/auth/login`
- `/swagger-ui/**`, `/swagger-ui.html`, `/v3/api-docs/**`

Todas as demais rotas exigem autenticação.

### Usuário padrão (carga inicial)

Criado automaticamente na inicialização pelo `DadosIniciaisConfig`:

| Campo | Valor |
|-------|-------|
| Login | `admin@orbitlink.com` |
| Senha | `123456` |

---

## 🌐 Endpoints da API

Base URL local: `http://localhost:8080`

### 🔑 Autenticação

| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/api/auth/login` | Autentica o usuário e retorna o token JWT |

### 🛰 Ativos espaciais

| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/api/ativos` | Cadastra um novo ativo |
| `GET` | `/api/ativos/{id}` | Busca um ativo por ID (com HATEOAS) |

### 🔧 Manutenções

| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/api/manutencoes` | Cria uma manutenção |
| `GET` | `/api/manutencoes` | Lista todas as manutenções |
| `GET` | `/api/manutencoes/{id}` | Busca manutenção por ID |
| `GET` | `/api/manutencoes/ativo/{ativoId}` | Lista manutenções de um ativo |
| `PUT` | `/api/manutencoes/{id}` | Atualiza uma manutenção |
| `DELETE` | `/api/manutencoes/{id}` | Remove uma manutenção |

### 📡 Telemetrias

| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/api/telemetrias` | Cria um registro de telemetria |
| `GET` | `/api/telemetrias` | Lista todas as telemetrias |
| `GET` | `/api/telemetrias/{id}` | Busca telemetria por ID |
| `GET` | `/api/telemetrias/ativo/{ativoId}` | Lista telemetrias de um ativo |
| `PUT` | `/api/telemetrias/{id}` | Atualiza uma telemetria |
| `DELETE` | `/api/telemetrias/{id}` | Remove uma telemetria |

### 🚨 Alertas

| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/api/alertas` | Cria um alerta |
| `GET` | `/api/alertas` | Lista todos os alertas |
| `GET` | `/api/alertas/{id}` | Busca alerta por ID |
| `GET` | `/api/alertas/ativo/{ativoId}` | Lista alertas de um ativo |
| `PUT` | `/api/alertas/{id}` | Atualiza um alerta |
| `DELETE` | `/api/alertas/{id}` | Remove um alerta |

### 📖 Documentação interativa

Com a aplicação rodando, acesse o **Swagger UI**:

```
http://localhost:8080/swagger-ui.html
```

---

## ▶️ Como executar

### Pré-requisitos

- **Java 17**
- **Maven 3.9+**

### Passo a passo

```bash
# 1. Clone o repositório
git clone <URL-DO-SEU-REPOSITORIO>
cd orbitlink

# 2. Execute a aplicação
mvn clean spring-boot:run
```

A aplicação sobe em:

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Console H2: `http://localhost:8080/h2-console`

### Credenciais do banco H2

| Campo | Valor |
|-------|-------|
| JDBC URL | `jdbc:h2:mem:orbitlinkdb` |
| Usuário | `sa` |
| Senha | *(vazio)* |

> O banco é recriado a cada inicialização (`ddl-auto=create-drop`) e populado com um usuário admin, um ativo, uma missão e uma alocação de exemplo.

---

## 🧪 Como testar

### 1. Obtenha o token

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{ "login": "admin@orbitlink.com", "senha": "123456" }'
```

Resposta:

```json
{ "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." }
```

### 2. Cadastre um ativo (usando o token)

```bash
curl -X POST http://localhost:8080/api/ativos \
  -H "Authorization: Bearer <SEU_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
        "nome": "Satélite Aurora-2",
        "tipoAtivo": "SATELITE",
        "agenciaResponsavel": "Agência OrbitLink"
      }'
```

### 3. Crie um alerta para o ativo

```bash
curl -X POST http://localhost:8080/api/alertas \
  -H "Authorization: Bearer <SEU_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
        "ativoId": 1,
        "tipoAlerta": "RISCO_COLISAO",
        "criticidade": "ALTA",
        "mensagem": "Objeto em rota de aproximação detectado",
        "dataGeracao": "2026-05-30T15:00:00",
        "resolvido": false
      }'
```

> Pelo **Swagger UI** o fluxo é ainda mais simples: faça login, clique em **Authorize**, cole o token e teste todos os endpoints pela interface.

---

## ⚠️ Tratamento de erros

Todas as exceções retornam um JSON padronizado, montado pelo `GlobalExceptionHandler`:

```json
{
  "timestamp": "2026-05-30T15:49:33.667",
  "status": 404,
  "error": "Not Found",
  "message": "Ativo espacial não encontrado com id 99",
  "path": "/api/ativos/99",
  "details": ["Ativo espacial não encontrado com id 99"]
}
```

| Situação | HTTP Status |
|----------|:-----------:|
| Entidade não encontrada (`EntidadeNaoLocalizadaException`) | `404 Not Found` |
| Violação de regra de negócio (`RegraDeNegocioException`) | `422 Unprocessable Entity` |
| Campos inválidos na requisição | `400 Bad Request` |
| Erro inesperado | `500 Internal Server Error` |

---

## 👥 Autores

- **Emanuel Italo Leal Trindade Soares**
- **Paulo Henrique Alves Estalise**
- **Gabriel Bebe**

---

<div align="center">

**OrbitLink** — Global Solution 2026/1 · FIAP · 2TDS
*"Quando ideias ganham propósito, elas têm o poder de transformar realidades."*

</div>
