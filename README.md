### Project setup

* Java 21
* Springboot 3.3.2
* Gradle 8.10

### Project configuration

###### Add properties to `.env` file:

RabbitMQ
link: https://www.cloudamqp.com (Message Queues in the Cloud)

```
RABBITMQ_HOST=cow.rmq2.cloudamqp.com
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=......
RABBITMQ_PASSWORD=........
RABBITMQ_VIRTUAL_HOST=......
```

MySQL
link: https://aiven.io (MYSQL cloud database)
MySQL 8.0.30 / Free-1-5gb / 1 CPU / 1 GB RAM / 5 GB storage

```
DB_MYSQL_HOSTNAME=......aivencloud.com
DB_MYSQL_PORT=25371
DB_MYSQL_DBNAME=..
DB_MYSQL_USER=....
DB_MYSQL_PASSWORD=....
DB_MYSQL_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
```

Postgresql
link: https://aiven.io (Postgresql cloud database)
PostgreSQL 16.3 / Free-1-5gb / 1 CPU / 1 GB RAM / 5 GB storage

or

link: https://www.elephantsql.com
Max database size 20 MB

```
DB_POSTGRESQL_HOSTNAME=.....
DB_POSTGRESQL_DBNAME=....
DB_POSTGRESQL_USER=....
DB_POSTGRESQL_PASSWORD=.....
```

Mongo Atlas
link: https://account.mongodb.com

```
DB_MONGODB_URI=.....
DB_MONGODB_DATABASE=.....
```

Redis cloud service
link:  https://redis.io (documentation)
link:  https://app.redislabs.com/ (cloud service)

```
DATA_REDIS_HOST=......redis-cloud.com
DATA_REDIS_PORT=11705
DATA_REDIS_USERNAME=....
DATA_REDIS_PASSWORD=......
```

### Gradle Versions Plugin

Displays a report of the project dependencies that are up-to-date, exceed the latest version found, have upgrades, or
failed to be resolved, info: https://github.com/ben-manes/gradle-versions-plugin

command:

```
gradle dependencyUpdates
```

### Java code style

Java code style refers to the conventions and guidelines that developers follow when writing Java code to ensure
consistency and readability.

project: google-java-format,
link: https://github.com/google/google-java-format/blob/master/README.md#intellij-jre-config

### Github action

issue:  ./gradlew: Permission denied
link: https://stackoverflow.com/questions/17668265/gradlew-permission-denied

You need to update the execution permission for gradlew

1. add action workflow

2. locally pull changes

3. run Git command:

```
git update-index --chmod=+x gradlew
git add .
git commit -m "Changing permission of gradlew"
git push
```

### Gradle wrapper

Gradle Wrapper Reference:
https://docs.gradle.org/current/userguide/gradle_wrapper.html

How to Upgrade Gradle Wrapper:
https://dev.to/pfilaretov42/tiny-how-to-upgrade-gradle-wrapper-3obl

```
./gradlew wrapper --gradle-version latest
```