# 📚 Book Store Application

## 🧾 Opis projektu

Aplikacja Book Store to system webowy oparty o Spring Boot, który umożliwia zarządzanie książkami, użytkownikami oraz zamówieniami za pomocą REST API. Została zbudowana z wykorzystaniem wzorców projektowych, autoryzacji JWT oraz testów jednostkowych i integracyjnych. Dane przechowywane są w bazie PostgreSQL (lub H2 w trybie testowym), a schemat inicjalizowany za pomocą Flyway.

---

## 🚀 Technologie

- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- Hibernate (JPA)
- PostgreSQL / H2 (testy)
- Flyway
- Swagger (OpenAPI)
- Maven
- JUnit 5 + Mockito

---

## 🗂️ Struktura projektu

```
store/
├── App.java
├── configuration/
│   ├── AppConfiguration.java
│   └── SwaggerConfig.java
├── security/
│   ├── JWTUtil.java
│   ├── JwtFilter.java
│   └── SecurityConfig.java
├── controllers/
│   └── rest/
│       ├── RestApiBookController.java
│       └── RestApiUserController.java
├── model/
│   ├── Book.java
│   ├── User.java
│   ├── Order.java
│   ├── OrderPosition.java
│   └── dto/
├── services/
│   ├── impl/
│   └── interfejsy serwisów
├── database/
│   ├── IBookDAO, IOrderDAO, IUserDAO
│   └── hibernate/ (implementacje DAO)
├── validators/
├── exceptions/
├── test/
│   └── controllersTest/
│       └── RestApiBookControllerTest.java
└── resources/
    ├── application.properties
    ├── db/migration/
    │   ├── V1__init_schema.sql
    │   ├── V2__add_indexes.sql
    │   ├── V4__insert_books.sql
    │   └── V5__insert_users.sql
```

---

## ⚙️ Konfiguracja aplikacji (`application.properties`)

```properties
server.port=8085

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

spring.datasource.url=jdbc:postgresql://db:5432/bookstore17
spring.datasource.username=michalkruczala
spring.datasource.password=admin
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
```

---

## 🔄 Uruchomienie aplikacji

1. Sklonuj repozytorium:

```bash
git clone https://github.com/MichalKruczala/STORE.git
cd STORE
```

2. Uruchom aplikację:

```bash
mvn spring-boot:run
```

3. Otwórz dokumentację API:

📄 Swagger UI: [http://localhost:8085/swagger-ui/index.html](http://localhost:8085/swagger-ui/index.html)

---

## 🔐 Endpointy REST API – Opis

### ✅ Autoryzacja

- `POST /api/v1/login`  
  Autoryzacja użytkownika. Wymaga loginu i hasła w body. Zwraca JWT, który należy dodać do nagłówka `Authorization: Bearer <token>` w kolejnych żądaniach.

---

### 📘 Książki

#### `GET /api/v1/book`  
Zwraca listę wszystkich książek. Publiczny endpoint.

#### `GET /api/v1/book/{id}`  
Zwraca szczegóły książki o danym ID. Jeśli książka nie istnieje – zwraca 404.

#### `POST /api/v1/book`  
Dodaje nową książkę. Wymaga autoryzacji jako **admin**.  
Body zawiera JSON z tytułem, autorem, ceną, itd.

#### `PUT /api/v1/book/{id}`  
Aktualizuje istniejącą książkę. Można zmienić tytuł, autora, cenę itp.

#### `DELETE /api/v1/book/{id}`  
Usuwa książkę o podanym ID. Wymaga roli `ADMIN`.

---

### 📦 Zamówienia

#### `POST /api/v1/order`  
Składa zamówienie. Wymaga tokenu użytkownika i przesłania danych zamówienia w body (lista pozycji, ilość, itp.)

#### `GET /api/v1/order/user/{id}`  
Zwraca wszystkie zamówienia złożone przez użytkownika o danym ID.

---

## ✅ Pokrycie testami

- ✅ `RestApiBookControllerTest` – testy integracyjne kontrolera REST
- ✅ Testy jednostkowe z użyciem `Mockito` dla DAO i Service
- ✅ `@MockBean` + `MockMvc` dla testów webowych

---

## 💡 Wzorce projektowe użyte w projekcie

| Wzorzec             | Opis |
|---------------------|------|
| DAO (Data Access)   | Interfejsy `IBookDAO`, `IUserDAO`, `IOrderDAO` oraz implementacje w `hibernate/` |
| Service Layer       | Klasy `BookService`, `UserService` zawierają logikę biznesową |
| DTO                 | Oddzielne obiekty danych w `model.dto` przesyłane między warstwami |
| Dependency Injection| Spring zarządza beanami dzięki `@Service`, `@Repository`, `@Autowired` |
| Strategy (Validation)| Możliwość rozszerzenia walidacji z wykorzystaniem customowych validatorów |
| Builder (opcjonalnie)| W przypadku mapowania DTO do encji (jeśli zaimplementowane) |

---

## 🧩 Klasy modelowe

| Klasa            | Przeznaczenie |
|------------------|---------------|
| `Book`           | Informacje o książce (tytuł, autor, ISBN, cena, stan) |
| `User`           | Dane użytkownika: login, hasło, rola (`USER`/`ADMIN`) |
| `Order`          | Reprezentuje jedno zamówienie użytkownika |
| `OrderPosition`  | Pozycja pojedynczej książki w zamówieniu |
| `BookDTO`, `UserDTO` | Przekazywanie danych w bezpieczny sposób do/z REST API |

---


---

## 👤 Autor

**Michał Kruczała**  
Grupa: GK03  
Nr indeksu: 152957
