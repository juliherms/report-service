# Report Service

Serviço de geração de relatórios em CSV desenvolvido com Spring Boot, focado em demonstrar diferentes abordagens de processamento assíncrono: threads tradicionais e virtual threads (Java 21).

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias](#tecnologias)
- [Funcionalidades](#funcionalidades)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e Configuração](#instalação-e-configuração)
- [Uso](#uso)
- [API Endpoints](#api-endpoints)
- [Arquitetura](#arquitetura)
- [Estrutura do Projeto](#estrutura-do-projeto)

## 🎯 Sobre o Projeto

O **Report Service** é uma aplicação Spring Boot que gera relatórios em formato CSV a partir de dados de funcionários armazenados em um banco de dados MySQL. O projeto demonstra três diferentes abordagens de processamento:

1. **Processamento Síncrono**: Execução sequencial das requisições
2. **Thread Pool Tradicional**: Utiliza um pool fixo de threads (5 threads)
3. **Virtual Threads**: Aproveita as virtual threads do Java 21 para alta concorrência

O projeto inclui uma carga inicial de **10.000 funcionários** brasileiros para testes e demonstração de performance.

## 🛠 Tecnologias

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.7** - Framework Java
- **Spring Data JPA** - Persistência de dados
- **MySQL 5.7** - Banco de dados relacional
- **Docker & Docker Compose** - Containerização do banco de dados
- **Lombok** - Redução de boilerplate
- **Apache Commons CSV** - Geração de arquivos CSV
- **Maven** - Gerenciamento de dependências

## ✨ Funcionalidades

- ✅ Geração de relatórios CSV por região
- ✅ Três estratégias de processamento (síncrono, thread pool, virtual threads)
- ✅ API REST para geração de relatórios
- ✅ Banco de dados MySQL containerizado
- ✅ Carga inicial automática de 10.000 funcionários
- ✅ Suporte a múltiplas requisições concorrentes

## 📦 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- **Java 21** ou superior
- **Maven 3.6+**
- **Docker** e **Docker Compose**
- **Git** (opcional)

## 🚀 Instalação e Configuração

### 1. Clone o repositório

```bash
git clone <url-do-repositorio>
cd relatorio-service
```

### 2. Inicie o banco de dados MySQL

```bash
docker-compose up -d
```

Isso irá:
- Criar um container MySQL 5.7
- Criar o banco de dados `reportdb`
- Configurar usuário e senha (veja `docker-compose.yml`)

### 3. Verifique se o MySQL está rodando

```bash
docker-compose ps
```

### 4. Compile e execute a aplicação

```bash
# Compilar o projeto
mvn clean install

# Executar a aplicação
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📖 Uso

### Gerar Relatório Síncrono

```bash
curl -X POST http://localhost:8080/relatorios/BR
```

### Gerar Relatório com Thread Pool

```bash
curl -X POST http://localhost:8080/relatorios/platform/BR
```

### Gerar Relatório com Virtual Threads

```bash
curl -X POST http://localhost:8080/relatorios/virtual/BR
```

Os arquivos CSV serão gerados na pasta `reports/` na raiz do projeto com os seguintes nomes:
- `relatorio_BR_report.csv` (síncrono)
- `platform_BR_report.csv` (thread pool)
- `virtual_thread_BR_report.csv` (virtual threads)

## 🔌 API Endpoints

### POST `/relatorios/{regiao}`

Gera um relatório CSV de forma síncrona para a região especificada.

**Exemplo:**
```bash
POST /relatorios/BR
```

**Resposta:**
```json
{
  "message": "Relatório gerado para a região: BR"
}
```

### POST `/relatorios/platform/{regiao}`

Gera um relatório CSV utilizando um pool fixo de 5 threads.

**Exemplo:**
```bash
POST /relatorios/platform/BR
```

**Resposta:**
```json
{
  "message": "Platform relatório gerado para a região: BR"
}
```

### POST `/relatorios/virtual/{regiao}`

Gera um relatório CSV utilizando virtual threads (Java 21).

**Exemplo:**
```bash
POST /relatorios/virtual/BR
```

**Resposta:**
```json
{
  "message": "Platform relatório gerado para a região: BR"
}
```

## 🏗 Arquitetura

### Modelo de Dados

A entidade `Funcionario` possui os seguintes campos:

- `id` (Long) - Identificador único
- `nome` (String) - Nome completo do funcionário
- `email` (String) - Email do funcionário
- `genero` (String) - Gênero (Masculino/Feminino)
- `regiao` (String) - Região do funcionário (ex: BR)

### Estratégias de Processamento

#### 1. ReportService (Síncrono)
- Processa requisições de forma sequencial
- Bloqueia a thread até a conclusão
- Ideal para baixo volume de requisições

#### 2. PlatformReportService (Thread Pool)
- Utiliza um pool fixo de 5 threads
- Permite processar até 5 requisições simultaneamente
- Requisições adicionais aguardam na fila

#### 3. RelatorioThreadVirtualService (Virtual Threads)
- Utiliza virtual threads do Java 21
- Suporta milhares de requisições concorrentes
- Melhor aproveitamento de recursos do sistema

## 📁 Estrutura do Projeto

```
relatorio-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/github/juliherms/report/
│   │   │       ├── config/
│   │   │       │   └── VirtualThreadExecutorConfig.java
│   │   │       ├── controller/
│   │   │       │   └── RelatorioController.java
│   │   │       ├── model/
│   │   │       │   └── Funcionario.java
│   │   │       ├── repository/
│   │   │       │   └── FuncionarioRepository.java
│   │   │       ├── service/
│   │   │       │   ├── PlatformReportService.java
│   │   │       │   ├── RelatorioThreadVirtualService.java
│   │   │       │   └── ReportService.java
│   │   │       ├── util/
│   │   │       │   └── CsvReportUtil.java
│   │   │       └── ReportServiceApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql
│   └── test/
├── docker-compose.yml
├── pom.xml
└── README.md
```

## ⚙️ Configurações

### application.properties

Principais configurações do projeto:

```properties
# Banco de dados
spring.datasource.url=jdbc:mysql://localhost:3306/reportdb
spring.datasource.username=reportuser
spring.datasource.password=reportpassword

# JPA
spring.jpa.hibernate.ddl-auto=create
spring.jpa.defer-datasource-initialization=true

# Scripts SQL
spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:data.sql
```

### docker-compose.yml

Configurações do MySQL:
- **Porta**: 3306
- **Banco de dados**: reportdb
- **Usuário**: reportuser
- **Senha**: reportpassword
- **Root password**: rootpassword

## 🧪 Testes

Execute os testes com:

```bash
mvn test
```

## 📊 Dados de Teste

O projeto inclui um script SQL (`data.sql`) com **10.000 funcionários** pré-cadastrados, todos da região **BR** (Brasil). Os dados são carregados automaticamente na inicialização da aplicação.

## 🔧 Desenvolvimento

### Compilar o projeto

```bash
mvn clean compile
```

### Executar testes

```bash
mvn test
```

### Gerar JAR executável

```bash
mvn clean package
```

O JAR será gerado em: `target/report-service-0.0.1-SNAPSHOT.jar`

### Executar o JAR

```bash
java -jar target/report-service-0.0.1-SNAPSHOT.jar
```

## 🐳 Docker

### Parar o banco de dados

```bash
docker-compose down
```

### Parar e remover volumes (limpar dados)

```bash
docker-compose down -v
```

### Ver logs do MySQL

```bash
docker-compose logs -f mysql
```

## 📝 Notas

- Os relatórios são gerados na pasta `reports/` na raiz do projeto
- A tabela `funcionario` é recriada a cada inicialização (`ddl-auto=create`)
- Os dados são recarregados automaticamente do `data.sql`
- Virtual threads requerem Java 21 ou superior

## 👤 Autor

**Juliano Herms**

- GitHub: [@juliherms](https://github.com/juliherms)

## 📄 Licença

Este projeto está sob a licença MIT.

---

Desenvolvido com ❤️ usando Spring Boot e Java 21

