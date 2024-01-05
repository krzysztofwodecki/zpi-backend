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

## Dodaj obecność jeśli nie istnieje
- **Endpoint:** `/events/{id}/checkIn`
- **Metoda HTTP:** `POST`
- **Przykładowe body**
  ```json
  {"id":1}
  ```
- **Opis:** Do wydarzenia określonego przez `id` i użytkownika o `id` podanym w body dodaje obecność jeśli jeszcze jej nie ma. Zwraca istniejące lub nowe wartości encji.
- **Response:**
  - 200
    ```json
    {"id":3,"userId":1,"eventId":2,"checkInTime":"2024-01-03T13:50:04.521352"} 
    ```
  - 404 - nie znaleziono użytkownika lub wydarzenia

## Pobierz listę obecności użytkownika
- **Endpoint:** `/attendance/history?userId={userId}`
- **Metoda HTTP:** `GET`
- **Opis:** Dla użytkownika o `id` podanym w query zostaje pobrana lista obecności posortowana według daty dodania.
- **Response:**
  - 200
    ```json
    // TODO
    ```

## Pobierz listę nagród
- **Endpoint:** `/rewards/`
- **Metoda HTTP:** `GET`
- **Opis:** Zwraca listę dostępnych nagród.
- **Response:**
  - 200
    ```json
    // TODO
    ```

## Pobierz listę nagród wypłaconych przez użytkownika
- **Endpoint:** `/rewards/redeemed?userId={id}`
- **Metoda HTTP:** `GET`
- **Opis:** Zwraca listę nagród wypłaconych przez użytkownika.
- **Response:**
  - 200
    ```json
    // TODO
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
    // TODO
    ```
  - 404 \
  Jeśli nagroda nie istnieje.

## Kup nagrodę
- **Endpoint:** `/rewards/{id}/redeem`
- **Metoda HTTP:** `POST`
- **Body:**
  ```
  {"id":1}
  ```
- **Opis:** Wykupuje nagrodę o `id` podanym w url użytkownikowi o `id`. Odejmuje użytkownikowi punkty w ilości wartości nagrody.
- **Response:**
  - 200
    ```json
    // TODO
    ```
  - 404 \
  Jeśli nagroda lub użytkownik nie istnieje.
  - 409 \
  Jeśli użytkownik nie ma wystarczającej ilości punktów. Ten kod jest aktualnie nieaktywny.
