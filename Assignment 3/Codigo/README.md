# IS Assignment 3 - Message Oriented Middleware (MOM) and Kafka Streams
 
<br>

## Docker

```shell
docker-compose -f docker-compose-standalone.yml up
```

<br>

## Config 

### Kafka

#### Source

```shell
curl --location --request POST 'http://localhost:8083/connectors/' --header 'Accept: application/json' --header 'Content-Type: application/json' --data-raw '{ "name": "jdbc-source", "config": {"connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector", "connection.url": "jdbc:postgresql://database:5432/project3", "connection.user": "postgres", "connection.password": "My01pass", "dialect.name": "PostgreSqlDatabaseDialect", "mode": "bulk", "query": "SELECT * FROM location;", "poll.interval.ms": "60000", "topic.prefix": "DBInfo"}}'
```

http://localhost:8083/connectors/jdbc-source/status

<br>

#### Sink

```shell
curl --location --request POST 'http://localhost:8083/connectors/' --header 'Accept: application/json' --header 'Content-Type: application/json' --data-raw '{ "name": "jdbc-sink", "config": {"tasks.max":"1", "connector.class":"io.confluent.connect.jdbc.JdbcSinkConnector", "connection.url":"jdbc:postgresql://database:5432/project3", "connection.user":"postgres", "connection.password":"My01pass", "dialect.name":"PostgreSqlDatabaseDialect", "topics": "req1,req2,req3,req4,req5,req6,req7,req8,req9,req10,req11", "auto.create":"true", "key.converter":"org.apache.kafka.connect.storage.StringConverter", "value.converter":"org.apache.kafka.connect.json.JsonConverter", "key.converter.schemas.enable":"true", "value.converter.schemas.enable":"true"}}'
```

http://localhost:8083/connectors/jdbc-sink/status

<br>

### Optional

```shell
curl --location --request DELETE 'http://localhost:8083/connectors/jdbc-sink'
```

```shell
docker exec -it broker1 /bin/kafka-console-consumer --bootstrap-server broker1:9092 --topic DBInfo --from-beginning
```

```sql
INSERT INTO location (weather_station, location) VALUES ('Coimbra', 'Universidade');
```

<br>

## TAREFAS

Legenda: ❌ - Por Fazer | ⏳ - A Fazer | ✅ - Feito | ❓ - Não Sei | 🗑️ - Eliminado

<br>

### Requisitos:

#### - 1 ⏳ Count temperature readings of standard weather events per weather station (5,0%)

#### - 2 ❌ Count temperature readings of standard weather events per location (5,0%)

#### - 3 ❌ Get minimum and maximum temperature per weather station (5,0%)

#### - 4 ❌ Get minimum and maximum temperature per location (Students should compute these values in Fahrenheit) (5,0%)

#### - 5 ❌ Count the total number of alerts per weather station (5,0%)

#### - 6 ❌ Count the total alerts per type (5,0%)

#### - 7 ❌ Get minimum temperature of weather stations with red alert events (7,5%)

#### - 8 ❌ Get maximum temperature of each location of alert events for the last hour (students are allowed to define a different value for the time window) (7,5%)

#### - 9 ❌ Get minimum temperature per weather station in red alert zones (7,5%)

#### - 10 ❌ Get the average temperature per weather station (7,5%)

#### - 11 ❌ Get the average temperature of weather stations with red alert events for the last hour (students are allowed to define a different value for the time window) (7,5%)

<br>

### Configurações:

#### - ❓ Configure Kafka with multiple brokers to enable Fault-Tolerance (7,5%)

<br>

### Aplicação:

#### - ✅ Source connections to read from the database (5,0%)

#### - ✅ Sink connections to send results to the database (5,0%)

#### - 🗑️ Appropriate CLI (0,0%)

#### - ❓ Enabling simple verification of results (5,0%)

<br>

### Outros:

#### - ❓ Attention to Details (7,5%)

<br>

<br>

### Notas:

#### - ❌ A server-side application would read data from the database and expose the data via REST. This could be accessed, e.g., by a command line application to let the administrator write and read data through the REST services.

#### - ❌ Students need to develop the Weather Stations (publishers) and the Kafka Streams applications form Figure 1. These are stand-alone applications. Students must also configure Kafka connectors to automatically extract and write data from/to the database. They may interact directly with the database, i.e., they do not need to implement the client and the REST server.

#### - ❌ The precise definition of the communication format is left for the students to determine. Clean solutions that serialize complex data structures as JSON strings are encouraged.

#### - ❌ To simplify the configuration work, the professors will provide a folder with YAML and Docker files with the configuration of multiple services. Students must later configure the lib directory mentioned in the YAML file and optionally the config directory as well.

#### - ❌ Solutions should maximize the computations that students do with Kafka streams and should not depend on complex database queries to extract results;

#### - ❌ Students should include means in their application to enable a fast verification of the results they are storing.

#### - ❌ Therefore, students should be able to stop one of the brokers without losing information or halting the entire system.


<br>

<br>

## PROBLEMAS & DÚVIDAS

- ...


<br>
