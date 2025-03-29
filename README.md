# Spring Microservices Project

## 📌 Описание
Этот проект представляет собой микросервисную архитектуру, реализованную на Java с использованием Spring Boot. Включает API Gateway, сервисы для работы с персональными данными и компаниями, а также PostgreSQL в качестве базы данных.

## 📂 Структура проекта
- **ApiGateway** — API-шлюз, маршрутизирующий запросы между сервисами.
- **PersonsApiServer** — сервис для управления данными о людях.
- **CompaniesApiServer** — сервис для управления данными о компаниях.
- **PostgreSQL** — база данных для хранения информации.
- **Docker Compose** — используется для оркестрации сервисов.

## 🚀 Запуск проекта
### 🔧 Клонирование репозитория
```sh
git clone git@github.com:balmakhanoff/spring_and_docker.git
cd spring_and_docker
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
| `DELETE` | `/persons/{id}` | Удалить человека по ID |
| `GET` | `/persons/by-company/{companyId}` | Получить список людей по компании |

#### 📄 Пример запроса `POST /persons`
```json
{
   "firstName": "Иван",
   "lastName": "Иванов",
   "phoneNumber": "+79998887766",
   "companyId": 1
}
```

### 🔹 Companies Service
| Метод | Эндпоинт | Описание |
|-------|---------|----------|
| `GET` | `/companies` | Получить список компаний |
| `POST` | `/companies` | Создать новую компанию |
| `GET` | `/companies/{id}` | Получить компанию по ID |
| `DELETE` | `/companies/{id}` | Удалить компанию по ID |
| `GET` | `/companies/{id}/employees` | Получить список сотрудников компании |

#### 📄 Пример запроса `POST /companies`
```json
{
   "companyName": "Apple",
   "budget": 1000.43
}
```