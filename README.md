### job4j_cars

#### Описание

Веб-приложение, реализующее функционал по продаже автомобилей. Основные возможности:
- Публикация объявлений (описание, марка, тип кузова, фото)
- Просмотр списка объявлений с возможностью отбора (все / только новые / за последний день)
- Закрытие объявлений

## Используемые технологии
- Java 17, Spring Boot 2.7.18 (Web, Thymeleaf, Test)
-  PostgreSQL 16.2, Liquibase 4.15.0, Hibernate 5.6.11

## Требования к окружению
- Java 17
- Maven 3.9.6
- PostgreSQL 16.2

## Запуск проекта
1. Создать новую базу данных **cars**.
2. Перейти в папку с проектом.
3. В файлах **db/liquibase.properties**, **src/main/resources/hibernate.cfg.xml** скорректировать настройки подключения к БД **todo**.
4. Создать схему базы данных:

   `mvn -P production liquibase:update`

5. Создать jar:

   `mvn package`

6. Запустить программу:

   `java -jar target/job4j_cars-1.0-SNAPSHOT.jar`

7. Перейти в браузере по адресу: http://localhost:8080/

## Примеры страниц
### Список объявлений:
![list](https://github.com/bsedykh/job4j_cars/assets/84812761/a80a749c-c087-4cd0-a12b-4887691d8afe)

### Создание объявления:
![create](https://github.com/bsedykh/job4j_cars/assets/84812761/720aca0a-6069-46f1-b96d-ea1ee3b8355b)

### Просмотр объявления:
![one](https://github.com/bsedykh/job4j_cars/assets/84812761/bbb5dfc4-84c1-418c-898d-6532aafbc04c)
