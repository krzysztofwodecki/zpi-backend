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

# Endpointy

## Pobierz Wszystkie Wydarzenia
- **Endpoint:** `/events?sortBy={time/name}`
- **Metoda HTTP:** `GET`
- **Opis:** Pobiera wszystkie wydarzenia z opcjonalnym parametrem sortowania. Sortuje wydarzenia według daty lub alfabetycznie według nazwy.
- **Response:**
  - 200
    ```json
    [{"id":1,"creatorId":1,"eventName":"Event 1","eventDateTime":"2023-12-28T21:55:56.815799","location":"Place 1"},{"id":3,"creatorId":1,"eventName":"New event","eventDateTime":"2023-12-28T18:57:06.314245","location":"New place"},{"id":2,"creatorId":2,"eventName":"xsssss","eventDateTime":"2023-12-28T18:57:06.314245","location":"Place 2"}]
    ```
    
## Pobierz Wydarzenia Utworzone Przez Użytkownika
- **Endpoint:** `/events/created/{creatorId}`
- **Metoda HTTP:** `GET`
- **Opis:** Pobiera listę wydarzeń utworzonych przez użytkownika o określonym `creatorId`.
- **Response:**
  - 200
    ```json
    [{"id":2,"creatorId":2,"eventName":"updated","eventDateTime":"2023-12-28T18:57:06.314245","location":"Place 2"}]  
    ```

## Dodaj Nowe Wydarzenie
- **Endpoint:** `/events`
- **Metoda HTTP:** `POST`
- **Opis:** Tworzy nowe wydarzenie.
- **Przykładowe body** 
    ```json
    {"id":4,"creatorId":1,"eventName":"New event","eventDateTime":"2023-12-28T18:57:06.314245","location":"New place"}
    ```
- **Response:**
  - 201
    ```json
    {"id":4,"creatorId":1,"eventName":"New event","eventDateTime":"2023-12-28T18:57:06.314245","location":"New place"}
    ```
  - 400 - w przypadku wystąpienia null w którymś polu, niepoprawnie zbudowanego JSONa

## Aktualizuj Wydarzenie
- **Endpoint:** `/events/{id}`
- **Metoda HTTP:** `PUT`
- **Opis:** Aktualizuje istniejące wydarzenie. Jeśli dane pole nie zostanie podane, zachowana zostanie oryginalna wartość.
- **Przykładowe body** 
    ```json
    {"eventName":"updated"}
    ```
- **Response:**
  - 200
    ```json
    {"id":2,"creatorId":2,"eventName":"updated","eventDateTime":"2023-12-28T18:57:06.314245","location":"Place 2"}
    ```
  - 404 - nie znaleziono wydarzenia z podanym id
  - 403 - użytkownik nie jest właścicielem wydarzenia (TODO)

## Usuń Wydarzenie
- **Endpoint:** `/events/{id}`
- **Metoda HTTP:** `DELETE`
- **Opis:** Usuwa wydarzenie o określonym `id`. Powiązane wpisy w tabeli `attendance_entity` związane kluczem obcym również są usuwane.
- **Response:**
  - 204
  - 404 - nie znaleziono wydarzenia z podanym id
  - 403 - użytkownik nie jest właścicielem wydarzenia (TODO)


## Pobierz Wydarzenie według Id
- **Endpoint:** `/events/{id}`
- **Metoda HTTP:** `GET`
- **Opis:** Pobiera wydarzenie o określonym `id`.
- **Response:**
  - 200
    ```json
    {"id":3,"creatorId":1,"eventName":"New event","eventDateTime":"2023-12-28T18:57:06.314245","location":"New place"} 
    ```
  - 404 - nie znaleziono



