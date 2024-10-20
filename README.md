# Library

### 1. Clonar o repositorio
```bash
git clone https://github.com/mattmiriani/library.git
```

### 2. Executar com Docker

Certifique-se de que o `docker` esteja instalado em sua máquina.
```bash
docker --version
```

Execute o seguinte comando para criar e iniciar os containers definidos 
no arquivo `compose.yml` em segundo plano, deixando o terminal livre:
```bash
docker-compose up -d
```

Caso queira apenas parar a aplicação, basta executar o seguinte comando:
```bash
docker-compose stop
```

Para excluir os containers:
```bash
docker-compose down
```

### 3. Configuração do banco de dados
Caso não queira usar o Docker para executar a aplicação, aqui estão as 
configurações do banco de dados:
```
versao: postgres:16
usuario:
    nome: library
    password: library
database:
    nome: library
```
Com o banco de dados configurado, execute os seguintes comandos:
```bash
mvn clean package
```
```bash
java -jar target/library-0.0.1-SNAPSHOT.jar
```