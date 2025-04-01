# Spring Microservices Project

## 📌 Описание
Этот проект представляет собой микросервисную архитектуру, реализованную на Java с использованием Spring Boot. Включает API Gateway, сервисы для работы с персональными данными и компаниями, а также PostgreSQL в качестве базы данных.
Добавлен **Eureka Server** для автоматической регистрации и обнаружения микросервисов, что позволяет сервисам взаимодействовать друг с другом без жесткой привязки к конкретным адресам. Eureka Server управляет всей регистрацией сервисов и их состоянием.

## 📂 Структура проекта
- **ApiGateway** — API-шлюз, маршрутизирующий запросы между сервисами.
- **PersonsApiServer** — сервис для управления данными о людях.
- **CompaniesApiServer** — сервис для управления данными о компаниях.
- **PostgreSQL** — база данных для хранения информации.
- **Eureka Service** — сервер для регистрации и обнаружения микросервисов.
- **Docker Compose** — используется для оркестрации сервисов.

## 🚀 Запуск проекта
### 🔧 Клонирование репозитория
```sh
git clone git@github.com:balmakhanoff/spring_and_docker.git
cd spring_and_docker
Собрать все три сервиса в jar файл, перейти в корневые каталоги и ввести команду "mvn clean package" или собрать с intellij MAVEN -> package
```

### 🐳 Запуск в Docker
1. Убедитесь, что установлены **Docker** и **Docker Compose**.
2. Запустите сервисы:
   ```sh
   docker-compose up --build
   ```
3. ApiGateway сервис поднимается на http://localhost:8080/

## 📌 API

### 🔹 Persons Service
| Метод | Эндпоинт | Описание |
|-------|---------|----------|
| `GET` | `/persons` | Получить список людей |
| `POST` | `/persons` | Создать нового человека |
| `GET` | `/persons/{id}` | Получить человека по ID |
| `PATCH` | `/{id}` | Обновить данные человека по ID |
| `DELETE` | `/persons/{id}` | Удалить человека по ID |

#### 📄 Пример запроса получение списка людей `GET /persons`
```
   http://localhost:8080/persons?page=0&size=10
```

#### 📄 Пример запроса на создание человека `POST /persons`
```json
{
   "firstName": "Иван",
   "lastName": "Иванов",
   "phoneNumber": "+79998887766",
   "companyId": 1
}
```

#### 📄 Пример запроса `PATCH /persons/{id}`
```json
{
   "firstName": "Батыр",
   "lastName": "Батыров",
   "phoneNumber": "+89998887766",
   "companyId": 2
}
```

### 🔹 Companies Service
| Метод | Эндпоинт | Описание |
|-------|---------|----------|
| `GET` | `/companies` | Получить список компаний |
| `POST` | `/companies` | Создать новую компанию |
| `GET` | `/companies/{id}` | Получить компанию по ID |
| `PATCH` | `/{id}` | Обновить данные компаний по ID |
| `DELETE` | `/companies/{id}` | Удалить компанию по ID |
| `GET` | `/companies/{companyId}/employees` | Получить все ID сотрудников компании |

#### 📄 Пример запроса получения списка кампании `GET /companies`
```
   http://localhost:8080/companies?page=0&size=10
```

#### 📄 Пример запроса на создание кампании `POST /companies`
```json
{
   "companyName": "Apple",
   "budget": 1000.43
}
```

#### 📄 Пример запроса на изменение кампании `PATCH /companies/{id}`
```json
{
   "companyName": "Microsoft",
   "budget": 1111.00
}
```