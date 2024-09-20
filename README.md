# Modsen Test Project

## Общая информация
Данный проект был разработан в качестве тестового задания для стажировки в компании Modsen.

## Используемые технологии
Проект написан с использованием JDK 21 и фреймворка Spring (Spring Boot, Spring DATA, Spring Security, Spring Cloud, Spring MVC). Проект представляет собой REST-API WEB-приложение, построенное по принципу MVC-модели. В качестве репозиториев использованы унаследованные от JPARepository классы. В проект добавлены объекты пересылки данных (DTO) и мапперы на основе MapStruct. Приложение включает валидацию данных, определенные исключения и их обработчик. Написаны тесты для методов мапперов, сервисов. Документация создана с помощью Swagger и Spring Open-API. Функционал API защищен Bearer-токенами с помощью Spring Security. Приложение использует PostgreSQL в качестве реляционной БД, Dockerfile и docker-compose для контейнеризации и развертывания.

## Структура приложения
Приложение имеет микросервисную архитектуру, включающую:

- **book-service**: предоставляет API для взаимодействия с объектами предметной области - книгами. Доступны следующие операции:
    - Получение списка всех книг
    - Добавление новой книги
    - Поиск книги по идентификатору
    - Поиск книги по ISBN
    - Обновление данных о книге
    - Удаление книги

- **library-service**: предоставляет API для учета книг в библиотеке. Доступны следующие операции:
    - Добавление книги в учет
    - Обновление данных об учете книги
    - Получение списка всех доступных книг

- **api-gateway**
- **eureka-server**
- **PostgreSQL сервер** с базами данных `books` и `library`

## Запуск приложения
1. Создайте дирректорию в удобном Вам месте на Вашем компьютере.
2. Скопируйте данный репозиторий:
   ```sh
   git clone https://github.com/danilalisichkin/modsen-test-project
3. Перейдите внутрь директории Modsen:
   ```sh
   cd Modsen
   ```
4. Запустите файл docker-compose.yml:
   ```sh
   docker-compose up
   ```
5. Откройте удобный Вам браузер и перейдите по следующему URL:

http://localhost:8080/webjars/swagger-ui/index.html

Важно: для запуска проекта порты 8080, 5432, 8761 должны быть свободны.

6. В загрузившемся окне Вы можете найти документацию по разработанному API, а также протестировать работу приложения с помощью интерактивных методов.

Важно: весь функционал API защищен с помощью Bearer-токенов, поэтому при обращении к book-service используйте токен
   ```
   eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJib29rLXNlcnZpY2UiLCJuYW1lIjoiYW5vbnltb3VzIiwicm9sZSI6Imd1ZXN0IiwiaWF0IjoxNjk1MTM5MjAwLCJhdWQiOiJib29rLXNlcnZpY2UifQ.xIhXTAn0gu_fW7bGmzP2RbGaTPfQaZZ4o3JxXY-FRVE
   ```
а к library-service
   ```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJsaWJyYXJ5LXNlcnZpY2UiLCJuYW1lIjoiYWRtaW4iLCJyb2xlIjoiYWRtaW4iLCJpYXQiOjE2OTUxMzkyMDAsImV4cCI6MTY5NTE0MjgwMCwiYXVkIjoibGlicmFyeS1zZXJ2aWNlIn0.XcMx0SXBv6Kw8r1wqeJZpPVHgITovPv7mlymg6F-1mc
   ```
7. Все готово!
