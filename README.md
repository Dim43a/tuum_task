## Applications

To run this project locally you need PostgresSQL, you can download it from official resource: https://www.postgresql.org/download/

I recommend also install pgAdmin: https://www.pgadmin.org/download/

After download, you need to start PostgresSQL server and create db.

You need to build Gradle with following command:

``gradle build``

For macOS users you need to run:

``./gradlew build``

### Account

### Register

URI: ``tuum/account``

Body:

```json
{
    "customerId":"123456",
    "country":"Estonia",
    "currencies":{
      "eur":true
  }
}
```

By default, all currencies are false. To create account with requested currencies we pass ``true`` with request.

### Get account:

URI: ``tuum/getAccount/{accountId}``

### Create transaction

URI: ``tuum/transaction``

Body:

```json
{
    "accountId": 14,
    "amount": 100,
    "currency":"EUR",
    "direction":"IN",
    "description": "Account credit"
}
```

### Get transaction

URI: ``tuum/transactionList/{accountId}``

### Docker

To run project using docker you need to run:
* Postgresql
* Rabbitmq
* Run command ``./gradlew bootJar``
* Then docker-compose up