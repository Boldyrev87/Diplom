## Диплом "Тестирование ПО"
Автоматизация тестирования веб-сервиса "Путешествие дня"

**Инструкция для запуска приложений и тестов**

Запуск вспомогательных сервисов - команда в терминале `docker-compose
up`

*MySQL*
- Запуск тестируемой системы - команда в терминале `java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`
- Запуск тестов - команда в терминале `./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"`

*PostgreSQL*
- Запуск тестируемой системы - команда в терминале `java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`
- Запуск тестов - команда в терминале `./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`

Отчет в репорт-системе Allure - команда в терминале `./gradlew   
allureserve`
