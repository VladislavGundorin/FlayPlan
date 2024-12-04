## 📋 Описание

**FlayPlan** — это основной микросервис, отвечающий за управление планами полетов, согласованиями и взаимодействием с базой данных.  
Проект реализован на **Java** с использованием **Spring Boot**, **Hibernate**, и подключением к базе данных **PostgreSQL**.  
Для работы всей системы используются дополнительные микросервисы:  
- **FlayPlan-api**: Обеспечивает контрактно-ориентированный API.  
- **FlayPlan-Consumer**: Отвечает за обработку сообщений из очередей RabbitMQ.  

Также проект поддерживает работу с **GraphQL** через GraphiQL интерфейс и интеграцию с **HATEOAS** для предоставления удобной навигации по API.

### Клонируйте реализацию:
    https://github.com/VladislavGundorin/FlayPlan.git

---

## 🐳 Запуск RabbitMQ

1. Для работы микросервиса необходимо запустить RabbitMQ:
    ```bash
    docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
2. Логин и пароль по умолчанию:
- Логин: guest
- Пароль: guest
---
## 📦 Структура проекта
- **service** — Логика обработки данных и бизнес-функционал.
- **serviceImpl** — Реализации сервисов, обеспечивающие связь между слоями.
- **repository** — Репозитории для работы с базой данных через JPA/Hibernate.
- **controllers** — REST-контроллеры для обработки HTTP-запросов и взаимодействия с клиентами.
- **GraphiQL** — Интерфейс для работы с запросами GraphQL.
- **HATEOAS** — Поддержка гипермедиа ссылок для удобной навигации через REST API.
---
## 📚 Дополнительная информация
Для полноценной работы проекта необходимо:  
1. Запустить все связанные микросервисы:  
   - [FlayPlan-api](https://github.com/VladislavGundorin/FlayPlan-api.git)  
   - [FlayPlan-Consumer](https://github.com/VladislavGundorin/FlayPlan-Consumer.git)  
2. Настроить подключение к базе данных PostgreSQL в `application.properties`:  
   ```bash
   spring.datasource.url=jdbc:postgresql://localhost:5432/flayplan
   spring.datasource.username=ваш_пользователь
   spring.datasource.password=ваш_пароль
   spring.jpa.hibernate.ddl-auto=update


## 🚀 Запуск проекта
1. Убедитесь, что PostgreSQL и RabbitMQ работают.
2. Соберите проект с помощью Maven:
- mvn clean install
3. Запустите приложение:
- mvn spring-boot:run


