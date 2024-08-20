# Система управления задачами

Это простая система управления задачами, построенная с использованием Spring Boot, Spring Security и JWT (JSON Web Token) для аутентификации.

## Функциональность

* **Регистрация и вход пользователей:**  Безопасная регистрация и вход пользователей.
* **Создание и управление задачами:**  Создание, редактирование, удаление и просмотр задач.
* **Назначение задач:** Назначение задач конкретным пользователям.
* **Статус и приоритет задач:**  Установка статуса (например, "В процессе", "Завершено") и приоритета (например, "Высокий", "Средний", "Низкий") для задач.
* **Комментарии:**  Добавление комментариев к задачам для совместной работы и обновлений.
* **Аутентификация и авторизация:**  Безопасный доступ к ресурсам на основе ролей пользователей.

## Используемые технологии

* **Spring Boot:**  Фреймворк для создания готовых к производству Spring-приложений.
* **Spring Security:**  Фреймворк для обеспечения аутентификации и авторизации.
* **JWT (JSON Web Token):**  Стандартный в отрасли метод для безопасной аутентификации.
* **Spring Data JPA:**  Упрощает взаимодействие с базой данных.
* **Postgress Database:**  Встроенная база данных для разработки и тестирования.
* **Swagger UI:**  Инструмент для документирования и тестирования API.

## Начало работы

1. **Необходимые компоненты:**
   * Java Development Kit (JDK) 8 или более поздней версии
   * Maven
   * IDE (например, IntelliJ IDEA, Eclipse)

2. **Клонирование репозитория:**
   ```bash
   git clone -b tz https://github.com/SeeXWH/task-management-system.git

3. **Сборка и запуск:**
  ```bash
cd task-management-system
mvn spring-boot:run


## Доступ к Swagger UI:
После запуска приложения откройте Swagger UI по адресу http://localhost:8080/swagger-ui/index.html, чтобы изучить и протестировать API-конечные точки.
Для работы вам потребуется залогиниться и после аунтифицироваться(смотрите конечные точки)
## API-конечные точки

**Конечные точки пользователей:**

* POST /user/register: Регистрация нового пользователя.
* POST /user/login: Вход существующего пользователя.

**Конечные точки задач:**

* POST /task/create: Создание новой задачи.
* PUT /task/edit?id={id}: Редактирование существующей задачи.
* DELETE /task/delete?id={id}: Удаление задачи.
* GET /task/getTask?id={id}: Получение задачи по ID.
* GET /task/myTask: Получение всех задач, назначенных вошедшему в систему пользователю.
* GET /task/userTask?user={username}: Получение всех задач, назначенных определенному пользователю.
* PUT /task/setStatus?id={id}&status={status}: Обновление статуса задачи.

**Конечные точки комментариев:**

* POST /comment/create: Создание нового комментария к задаче.
* GET /comment/getComments?taskId={taskId}: Получение всех комментариев к задаче.
## Аутентификация
* API использует JWT (JSON Web Token) для аутентификации. После успешного входа возвращается токен. Включите этот токен в заголовок Authorization последующих запросов как токен Bearer (например, Authorization: Bearer <token>).
