### Spring Data JPA [PRO] - How to connect Multiple Databases using Spring Boot | Multiple Datasources

link: https://www.youtube.com/watch?v=z65J3JPbs9A <br>
link: https://www.youtube.com/watch?v=iPguOeytlwk&t=384s


### project setup

create .env file with properties:

```
MYSQL_HOSTNAME=
MYSQL_DBNAME=
MYSQL_USER=
MYSQL_PASSWORD=

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