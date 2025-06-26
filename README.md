# job4j_auth

Простой REST сервис на Spring Boot для управления пользователями (модель `Person`).  
Проект создан в учебных целях для практики работы с Spring Boot, Spring Data JPA и PostgreSQL.

---

## О проекте

- Язык: Java 21
- Сборка: Maven
- Spring Boot 3.5.3
- PostgreSQL
- CRUD REST API для модели Person (пользователь с логином и паролем)
- Формат данных — JSON

---

## Схема базы данных

```sql
create table person (
    id serial primary key not null,
    login varchar(2000) unique,
    password varchar(2000)
);

insert into person (login, password) values ('parsentev', '123');
insert into person (login, password) values ('ban', '123');
insert into person (login, password) values ('ivan', '123');
```
---

## Запуск приложения
1. Создайте базу данных PostgreSQL с именем fullstack_auth.
2. Выполните SQL скрипт для создания таблицы и вставки начальных данных. 
3. Скачайте или клонируйте этот репозиторий. 
4. Настройте параметры подключения в application.properties при необходимости. 
5. Запустите приложение через IDE или командой:

```bash
./mvnw spring-boot:run
```
---

## REST API

### Получить всех пользователей
```bash
curl -i http://localhost:8080/person/
```
### Получить пользователя по ID
```bash
curl -i http://localhost:8080/person/1
```
### Создать нового пользователя
```bash
curl -H 'Content-Type: application/json' \
     -X POST \
     -d '{"login":"job4j@gmail.com", "password":"123"}' \
     http://localhost:8080/person/
```
### Обновить пользователя
```bash
curl -i -H 'Content-Type: application/json' \
     -X PUT \
     -d '{"id":5,"login":"support@job4j.com","password":"123"}' \
     http://localhost:8080/person/
```
### Удалить пользователя
```bash
curl -i -X DELETE http://localhost:8080/person/5
```