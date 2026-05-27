# OrbitLink

Projeto Spring Boot desenvolvido para o tema **Economia Espacial**, com foco em monitoramento de ativos orbitais, manutenção, telemetria e alertas operacionais.

## Visão geral

A solução modela uma plataforma para gestão de ativos espaciais que conecta exploração espacial a necessidades reais da Terra, como monitoramento climático, conectividade, segurança orbital e suporte à tomada de decisão.

## Arquitetura adotada

O projeto foi estruturado em camadas:

- **controller**: expõe a API REST.
- **service**: concentra regras de negócio e conversão entre entidade e DTO.
- **repository**: contém DAOs manuais com `EntityManager`, `@Repository` e `@Transactional`.
- **entity**: mapeamento JPA das entidades do domínio.
- **dto**: objetos de entrada e saída da API.
- **core.annotations**: anotações customizadas usadas pelo motor de reflexão.
- **util**: utilitários de suporte, incluindo o `MotorReflectionOrbitLink`.
- **exception**: tratamento padronizado de erros.
- **config**: CORS e carga inicial de dados.

## Regras técnicas atendidas

- **Sem Spring Data Repository**: não há `JpaRepository` nem `CrudRepository`.
- **DAO manual com EntityManager**: persistência feita por JPQL e `EntityManager`.
- **DTO obrigatório**: controllers consomem e retornam somente DTOs.
- **Reflection**: as entidades são lidas na inicialização pelo `MotorReflectionOrbitLink`.
- **Soft delete**: `AtivoEspacial` usa descomissionamento lógico via `operacional = false`.
- **Tratamento global de exceções**: `GlobalExceptionHandler` retorna JSON estruturado.
- **H2 em memória**: pronto para execução local imediata.

## Domínio

### AtivoEspacial
Agregado raiz da solução. Representa satélites, sondas e outros ativos orbitais.

### ManutencaoOrbital
Histórico de intervenções, correções e ajustes em órbita.

### RegistroTelemetria
Armazena sinais e leituras recebidas dos ativos.

### AlertaOrbital
Registra alertas de risco de colisão, falhas e degradação sistêmica.

## Endpoints principais

### Ativos
- `POST /api/ativos`
- `GET /api/ativos`
- `GET /api/ativos/{id}`
- `PUT /api/ativos/{id}`
- `PATCH /api/ativos/{id}/descomissionar`

### Manutenções
- `POST /api/manutencoes`
- `GET /api/manutencoes`
- `GET /api/manutencoes/{id}`
- `GET /api/manutencoes/ativo/{ativoId}`
- `PUT /api/manutencoes/{id}`
- `DELETE /api/manutencoes/{id}`

### Telemetrias
- `POST /api/telemetrias`
- `GET /api/telemetrias`
- `GET /api/telemetrias/{id}`
- `GET /api/telemetrias/ativo/{ativoId}`
- `PUT /api/telemetrias/{id}`
- `DELETE /api/telemetrias/{id}`

### Alertas
- `POST /api/alertas`
- `GET /api/alertas`
- `GET /api/alertas/{id}`
- `GET /api/alertas/ativo/{ativoId}`
- `PUT /api/alertas/{id}`
- `DELETE /api/alertas/{id}`

## Como executar

### Pré-requisitos
- Java 17
- Maven 3.9+

### Comandos
```bash
mvn clean spring-boot:run
```

A aplicação sobe em:

- `http://localhost:8080`
- Console H2: `http://localhost:8080/h2-console`

### Credenciais do H2
- JDBC URL: `jdbc:h2:mem:orbitlinkdb`
- User: `sa`
- Password: vazio

## Autores

- Emanuel Italo Leal Trindade Soares
- Paulo Henrique Alves Estalise
- Gabriel Bebe

## Observação técnica

O projeto foi construído com a arquitetura manual solicitada, priorizando DAOs com `EntityManager` e reflexão para leitura das anotações customizadas.
