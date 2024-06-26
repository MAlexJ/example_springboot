### Spring Data JPA [PRO] - How to connect Multiple Databases using Spring Boot | Multiple Datasources

link: https://www.youtube.com/watch?v=z65J3JPbs9A <br>
link: https://www.youtube.com/watch?v=iPguOeytlwk&t=384s

### project setup

create .env file with properties:

```
DB_MYSQL_HOSTNAME=mysql-***********-e.aivencloud.com
DB_MYSQL_PORT=25371
DB_MYSQL_DBNAME=******_db
DB_MYSQL_USER=*******
DB_MYSQL_PASSWORD=*******
DB_MYSQL_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver

POSTGRESQL_HOSTNAME=***_elephantsql.com
POSTGRESQL_DBNAME=***_elephantdb
POSTGRESQL_USER=*****
POSTGRESQL_PASSWORD=*****
```

### POSTGRESQL

For testing, you can use a free PostgreSQL database - https://www.elephantsql.com/

Service uses **Postgresql** database and **r2dbc** reactive protocol

#### Docker

Info: https://hub.docker.com/_/postgres

**database version**: 16
**command**: docker pull postgres:16

start a postgres instance:

```
    $ docker run -d \
    --name postgres-v16-db \
    -e POSTGRES_PASSWORD=password \
    -e POSTGRES_USER=user \
    -e POSTGRES_DB=db \
    postgres:16
```

one-line command

```
docker run -d -p 5432:5432 --name postgres-v16-db -e POSTGRES_PASSWORD=password -e POSTGRES_USER=user -e POSTGRES_DB=db postgres:16
```

Note:
The default postgres user and database are created in the entrypoint with init db.

### MYSQL

### Spring JPA configuration

#### Show Hibernate/JPA SQL

link: https://www.baeldung.com/sql-logging-spring-boot

The simplest way to dump the queries to standard out is to add the following to application.properties:

```
spring.jpa.show-sql=true
```

To beautify or pretty-print the SQL, we can add:

```
spring.jpa.properties.hibernate.format_sql=true
```

### The OSIV Anti-Pattern

Instead of letting the business layer decide how it’s best to fetch all the associations that are needed by the View
layer, OSIV (Open Session in View) forces the Persistence Context to stay open so that the View layer can trigger the
Proxy initialization, as illustrated by the following diagram.

link: https://stackoverflow.com/questions/30549489/what-is-this-spring-jpa-open-in-view-true-property-in-spring-boot

```
spring.jpa.open-in-view=false
```