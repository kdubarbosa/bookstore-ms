# Bookstore Microservice (bookstore-ms)

Este repositório contém o microserviço **bookstore-ms**, desenvolvido com **Java Spring Boot**.
O projeto utiliza um banco de dados **PostgreSQL**, que pode ser iniciado via **Docker Compose**.

## 🚀 Como Rodar o Projeto Localmente

### 1️⃣ **Pré-requisitos**
Certifique-se de ter instalado:
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
- Java 21+
- Maven 3.8+

### 2️⃣ **Subindo os Containers**
O Docker Compose está localizado em `docker-local/docker-compose.yml`. Para subir os serviços, execute:

```sh
cd docker-local
docker-compose up -d
```

Isso iniciará o banco de dados PostgreSQL e outros serviços necessários.

### 4️⃣ **Rodando a Aplicação**
Após subir os containers, inicie o serviço Java com:

```sh
mvn spring-boot:run
```

### 5️⃣ **Acessando os endpoints da Aplicação**
- API: `http://localhost:8081/api/v1/swagger-ui/index.html#/`

## 🛑 Parando os Containers
Para parar e remover os containers, execute:

```sh
cd docker-local
docker-compose down
```

---

📌 **Observação**: Certifique-se de que nenhuma outra aplicação está utilizando a porta `5432` para evitar conflitos.

Caso tenha dúvidas, abra uma issue no repositório. 🚀

