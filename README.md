# Aplikacja do organizacji eventów - backend
- Spring Boot,
- skrypty migracyjne,
- kod SQL to stworzenia bazki.

# Oprogramowanie

- Java 17 (openjdk version "17.0.4.1")
- Maven (Apache Maven 3.9.5)
- Docker (Docker version 20.10.17)

# Uruchamianie backendu

Spakowanie backendu do pliku .jar <br>
```bash
./mvnw clean package -DskipTests
``` 

Uruchomienie kontenerów <br>
```bash 
docker-compose up
``` 

Usunięcie niepotrzebnych kontenerów <br>
```bash 
docker-compose down
```

# Działające usługi

- PostgreSQL
- Spring
- PgAdmin
