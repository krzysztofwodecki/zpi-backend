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

Przy nowych zmianach na backendzie (właśnych lub aktualizacja repozytorium) należy zbudować obraz od początku. Inaczej docker będzie cachować starę pliki i na ich podstawie budować projekt. <br>
Można zmusić dockera do zbudowania nowego kontenera usuwając stary kontener w GUI lub użyc komendy:<br>
```bash
docker-compose down && docker-compose build --no-cache
```

# Działające usługi

- PostgreSQL
- Spring
- PgAdmin

# Link do testów w Postmanie
https://www.postman.com/satellite-cosmonaut-19244/workspace/my-workspace/collection/26206439-b5d63f8f-938c-45b1-abd9-522683ea75d5

# Endpointy
## Logowanie
- **Endpoint:** `/auth/login`
- **Metoda HTTP:** `POST`
- **Payload:**
  - ```json 
    {
      "email": "user1@exampl.com",
      "password": "passwd"
    }
    ```
- **Opis:** Loguję użytkownika i zwraca klucz jwt.
- **Response:**
  - 200
    ```
    eyJhbGciOiJ...lInk87GfI
    ```
## Rejestracja
- **Endpoint:** `/auth/register`
- **Metoda HTTP:** `POST`
- **Payload:**
  - ```json 
    {
      "email": "user1@exampl.com",
      "password": "passwd"
    }
    ```
- **Opis:** Rejestruje użytkownika i zwraca jego dane.
- **Response:**
  - 200
    ```json 
    {
      "id": 2,
      "email": "test12@mail.com",
      "roles": "ROLE_USER",
      "points": 0
    }
    ```
## Do reszty endpointów trzeba dołączyć header z kluczem jwt
```json 
{"Authorization": "Bearer $jwtkey"}
```

## Wylogowanie
- **Endpoint:** `/auth/logout`
- **Metoda HTTP:** `POST`
- **Opis:** Wylogowuje użytkownika (przesyłany token staje się nieaktualny)
- **Response:**
  - 200
    ```
    Logout successful
    ```

## Użytkownik
- **Endpoint:** `/auth/user`
- **Metoda HTTP:** `GET`
- **Opis:** zwraca jego dane.
- **Response:**
  - 200
    ```json
    {
      "id": 2,
      "email": "test12@mail.com",
      "roles": "ROLE_USER",
      "points": 0
    }
    ```    

## Pobierz Wszystkie Wydarzenia
- **Endpoint:** `/events?sortBy={time/name}`
- **Metoda HTTP:** `GET`
- **Opis:** Pobiera wszystkie wydarzenia z opcjonalnym parametrem sortowania. Sortuje wydarzenia według daty lub alfabetycznie według nazwy.
- **Response:**
  - 200
    ```json
    [
      {
        "id": 1,
        "creatorId": 1,
        "eventName": "Event 1",
        "eventDateTime": "2023-12-28T21:55:56.815799",
        "location": "Place 1"
      },
      {
        "id": 3,
        "creatorId": 1,
        "eventName": "New event",
        "eventDateTime": "2023-12-28T18:57:06.314245",
        "location": "New place"
      },
      {
        "id": 2,
        "creatorId": 2,
        "eventName": "xsssss",
        "eventDateTime": "2023-12-28T18:57:06.314245",
        "location": "Place 2"
      }
    ]
    ```
    
## Pobierz Wydarzenia Utworzone Przez Użytkownika
- **Endpoint:** `/events/created/{creatorId}`
- **Metoda HTTP:** `GET`
- **Opis:** Pobiera listę wydarzeń utworzonych przez użytkownika o określonym `creatorId`.
- **Response:**
  - 200
    ```json
    [
      {
        "id": 2,
        "creatorId": 2,
        "eventName": "updated",
        "eventDateTime": "2023-12-28T18:57:06.314245",
        "location": "Place 2"
      }
    ]
    ```

## Dodaj Nowe Wydarzenie
- **Endpoint:** `/events`
- **Metoda HTTP:** `POST`
- **Opis:** Tworzy nowe wydarzenie.
- **Przykładowe body** 
    ```json
    {
      "creatorId": 1,
      "eventName": "New event",
      "eventDateTime": "2023-12-28T18:57:06.314245",
      "location": "New place"
    }
    ```
- **Response:**
  - 201
    ```json
    {
      "id": 4,
      "creatorId": 1,
      "eventName": "New event",
      "eventDateTime": "2023-12-28T18:57:06.314245",
      "location": "New place"
    }
    ```
  - 400 - w przypadku wystąpienia null w którymś polu, niepoprawnie zbudowanego JSONa

## Aktualizuj Wydarzenie
- **Endpoint:** `/events/{id}`
- **Metoda HTTP:** `PUT`
- **Opis:** Aktualizuje istniejące wydarzenie. Jeśli dane pole nie zostanie podane, zachowana zostanie oryginalna wartość.
- **Przykładowe body** 
    ```json
    {
      "eventName": "updated"
    }
    ```
- **Response:**
  - 200
    ```json
    {
      "id": 2,
      "creatorId": 2,
      "eventName": "updated",
      "eventDateTime": "2023-12-28T18:57:06.314245",
      "location": "Place 2"
    }
    ```
  - 404 - nie znaleziono wydarzenia z podanym id
  - 403 - użytkownik nie jest właścicielem wydarzenia

## Usuń Wydarzenie
- **Endpoint:** `/events/{id}`
- **Metoda HTTP:** `DELETE`
- **Opis:** Usuwa wydarzenie o określonym `id`. Powiązane wpisy w tabeli `attendance_entity` związane kluczem obcym również są usuwane.
- **Response:**
  - 204
  - 404 - nie znaleziono wydarzenia z podanym id
  - 403 - użytkownik nie jest właścicielem wydarzenia


## Pobierz Wydarzenie według Id
- **Endpoint:** `/events/{id}`
- **Metoda HTTP:** `GET`
- **Opis:** Pobiera wydarzenie o określonym `id`.
- **Response:**
  - 200
    ```json
    {
      "id": 3,
      "creatorId": 1,
      "eventName": "New event",
      "eventDateTime": "2023-12-28T18:57:06.314245",
      "location": "New place"
    }
    ```
  - 404 - nie znaleziono

## Dodaj wydarzenie do ulubionych
- **Endpoint:** `/events/{id}/like`
- **Metoda HTTP:** `POST`
- **Opis:** Dodaje wydarzenie do ulubionych
- **Response:**
  - 200 (info o wydarzeniu)
    ```json
    {
      "id": 2,
      "creatorId": 2,
      "eventName": "Event 2",
      "eventDateTime": "2024-01-20T20:45:44.125845",
      "location": "Place 2"
    }
    ```
  - 404 - nie znaleziono
 
## Usuń wydarzenie z ulubionych
- **Endpoint:** `/events/{id}/like`
- **Metoda HTTP:** `DELETE`
- **Opis:** Usuwa wydarzenie z ulubionych
- **Response:**
  - 204 
  - 404 - nie znaleziono
 
## Pobierz ulubione wydarzenia
- **Endpoint:** `/events/liked`
- **Metoda HTTP:** `GET`
- **Opis:** Pobiera listę z wydarzeniami dodanymi do ulubionych
- **Response:**
  - 200 
    ```json
     [
      {
        "id": 3,
        "creatorId": 1,
        "eventName": "updated",
        "eventDateTime": "2023-12-28T18:57:06.314245",
        "location": "New place"
      }
    ]
    ```
  
## Dodaj obecność jeśli nie istnieje
- **Endpoint:** `/events/{id}/checkIn`
- **Metoda HTTP:** `POST`
- **Opis:** Do wydarzenia określonego przez `id` dodaje obecność. Zwraca istniejące lub nowe wartości encji (id użytkownika pobierane jest z tokenu do autoryzacji)
- **Response:**
  - 200
    ```json
    {
      "id": 4,
      "user": {
        "id": 1,
        "email": "user1@exampl.com",
        "roles": "ROLE_USER",
        "points": 200
      },
      "event": {
        "id": 2,
        "creatorId": 2,
        "eventName": "Event 2",
        "eventDateTime": "2024-01-20T20:45:44.125845",
        "location": "Place 2"
      },
      "checkInTime": "2024-01-20T21:14:24.079852995"
    }
    ```
  - 404 - nie znaleziono użytkownika lub wydarzenia

## Pobierz listę obecności użytkownika
- **Endpoint:** `/attendance/history?userId={userId}`
- **Metoda HTTP:** `GET`
- **Opis:** Dla użytkownika o `id` podanym w query zostaje pobrana lista obecności posortowana według daty dodania.
- **Response:**
  - 200
    ```json
    [
      {
        "id": 2,
        "creatorId": 2,
        "eventName": "Event 2",
        "eventDateTime": "2024-01-20T20:45:44.125845",
        "location": "Place 2"
      }
    ]
    ```

## Pobierz listę nagród
- **Endpoint:** `/rewards`
- **Metoda HTTP:** `GET`
- **Opis:** Zwraca listę dostępnych nagród.
- **Response:**
  - 200
    ```json
    [
      {
        "id": 1,
        "name": "reward1",
        "description": "desc",
        "value": 400
      },
      {
        "id": 2,
        "name": "reward2",
        "description": "desc2",
        "value": 900
      }
    ]
    ```

## Pobierz listę nagród wypłaconych przez użytkownika
- **Endpoint:** `/rewards/redeemed?userId={id}`
- **Metoda HTTP:** `GET`
- **Opis:** Zwraca listę nagród wypłaconych przez użytkownika.
- **Response:**
  - 200
    ```json
    [
      {
        "id": 1,
        "name": "reward1",
        "description": "desc",
        "value": 400
      },
      {
        "id": 1,
        "name": "reward1",
        "description": "desc",
        "value": 400
      }
    ]
    ```
  - 404 \
  Jeśli użytkownik nie istnieje.

## Pobierz detale dotyczące nagrody według id
- **Endpoint:** `/rewards/{id}`
- **Metoda HTTP:** `GET`
- **Opis:** Zwraca informację o nagrodzie.
- **Response:**
  - 200
    ```json
    {
      "id": 1,
      "name": "reward1",
      "description": "desc",
      "value": 400
    }
    ```
  - 404 \
  Jeśli nagroda nie istnieje.

## Kup nagrodę
- **Endpoint:** `/rewards/{id}/redeem`
- **Metoda HTTP:** `POST`
- **Opis:** Wykupuje nagrodę o `id` podanym w url (id użytkownika pobierane z tokenu). Odejmuje użytkownikowi punkty w ilości wartości nagrody.
- **Response:**
  - 200
    ```json
    {
      "id": 1,
      "reward": {
        "id": 1,
        "name": "reward1",
        "description": "desc",
        "value": 400
      },
      "user": {
        "id": 1,
        "email": "user1@exampl.com",
        "roles": "ROLE_USER",
        "points": 600
      }
    }
    ```
  - 404 \
  Jeśli nagroda nie istnieje.
  - 409 \
  Jeśli użytkownik nie ma wystarczającej ilości punktów.
