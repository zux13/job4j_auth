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

### Регистрация
```bash
curl -X POST http://localhost:8080/person/sign-up \
     -H "Content-Type: application/json" \
     -d '{"login":"ivan", "password":"123"}'
```

### Логин в базе
```bash
curl -X POST http://localhost:8080/login \
     -H "Content-Type: application/json" \
     -d '{"login":"ivan", "password":"123"}' \
     -i
```
#### Пример ответа
```bash
HTTP/1.1 200 
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ6dXgiLCJleHAiOjE3NTE3OTc1Njl9.sP7Hn3vH_03zTH75-YHxPcXGwqtdRKmCVjMfapKPzM_yDSjhdniOvs7xr3-zjZUYJlLPGwcFOjM4x2ISTy0Mbg
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Length: 0
Date: Thu, 26 Jun 2025 10:26:09 GMT
```
---

> **Внимание!** Дальнейшие запросы требуют передачи `<JWT_TOKEN>` в заголовке. Получить его можно при логине, см. "Пример ответа".

---
### Получить всех пользователей
```bash
curl -X GET http://localhost:8080/person/ \
     -H "Authorization: Bearer <JWT_TOKEN>"
```
### Получить пользователя по ID
```bash
curl -X GET http://localhost:8080/person/1 \
     -H "Authorization: Bearer <JWT_TOKEN>"
```
### Обновить пользователя
```bash
curl -X PUT http://localhost:8080/person/ \
     -H "Authorization: Bearer <JWT_TOKEN>" \
     -H "Content-Type: application/json" \
     -d '{"id":1, "login":"updatedUser", "password":"updatedPass"}'
```
### Удалить пользователя
```bash
curl -X DELETE http://localhost:8080/person/1 \
     -H "Authorization: Bearer <JWT_TOKEN>"
```