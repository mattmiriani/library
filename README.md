# Library

### 1. Clonar o repositorio
```bash
git clone https://github.com/mattmiriani/library.git
```

### 2. Executar com Docker

Certifique de que o `docker` esteja instalado em sua maquina.
```bash
docker --version
```

Execute o seguinte comando para cria e inicia os contêineres definidos 
no arquivo `compose.yml` em segundo plano deixando o terminal livre.
```bash
docker-compose up -d
```

Caso queria somente parar a aplicacao, basta execultar o seguinte comando:
```bash
docker-compose stop
```

Para excluir os contêineres, execulte o seguinte comando:
```bash
docker-compose down
```

### 3. Configuracao do banco de dados
Caso nao queira usar o docker para execultar a aplicacao, aqui vai as configuracoes 
do banco de dados.
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