# Relatos Catalogue Service

Microservicio Spring Boot para gestionar el catalogo de libros de Relatos de Papel.

## Stack

- Java 17
- Maven
- Spring Boot
- Spring Web
- Spring Data JPA
- Jakarta Validation
- H2 Database
- Eureka Discovery Client
- Actuator

## Configuracion

- Puerto: `8081`
- Aplicacion: `catalogue-service`
- Eureka: `http://localhost:8761/eureka`
- H2 Console: `http://localhost:8081/h2-console`
- JDBC URL H2: `jdbc:h2:mem:cataloguedb`
- Usuario H2: `sa`
- Password H2: vacio

## Ejecutar

```bash
mvn spring-boot:run
```

Compilar:

```bash
mvn clean package
```

## Endpoints

Base URL: `http://localhost:8081/api/books`

| Metodo | Ruta | Descripcion |
| --- | --- | --- |
| GET | `/api/books` | Lista libros y permite busqueda por filtros combinables |
| GET | `/api/books/{id}` | Obtiene un libro por id |
| POST | `/api/books` | Crea un libro |
| PUT | `/api/books/{id}` | Actualiza completamente un libro |
| PATCH | `/api/books/{id}` | Actualiza parcialmente un libro |
| DELETE | `/api/books/{id}` | Elimina un libro |

## Filtros de busqueda

`GET /api/books` acepta estos parametros opcionales, combinables entre si:

- `title`
- `author`
- `publicationDate` con formato `YYYY-MM-DD`
- `category`
- `isbn`
- `rating`
- `visible`

Ejemplos:

```bash
curl "http://localhost:8081/api/books?author=borges&category=Cuento"
curl "http://localhost:8081/api/books?visible=true&rating=5"
curl "http://localhost:8081/api/books?publicationDate=1967-05-30"
```

## Crear libro

```bash
curl -X POST "http://localhost:8081/api/books" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "La tregua",
    "author": "Mario Benedetti",
    "publicationDate": "1960-01-01",
    "category": "Narrativa",
    "isbn": "9788490628720",
    "rating": 4,
    "visible": true,
    "stock": 10,
    "price": 12.90
  }'
```

## Actualizacion parcial

```bash
curl -X PATCH "http://localhost:8081/api/books/1" \
  -H "Content-Type: application/json" \
  -d '{
    "stock": 4,
    "visible": false
  }'
```

## Validaciones

- `title` obligatorio en `POST` y `PUT`
- `author` obligatorio en `POST` y `PUT`
- `rating` entre `1` y `5`
- `stock` minimo `0`
- `price` minimo `0`
- `isbn` unico

## Codigos HTTP

- `200 OK` para lecturas y actualizaciones correctas.
- `201 Created` para creacion.
- `204 No Content` para borrado.
- `400 Bad Request` para errores de validacion, tipos invalidos o valores unicos duplicados.
- `404 Not Found` cuando el libro no existe.
