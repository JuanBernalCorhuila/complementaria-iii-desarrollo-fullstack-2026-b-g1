# Semana 7 · Entity y repository (JPA) — Videojuegos

## 1. Entity: `Videojuego`

La clase `Videojuego` se mapea a una tabla con JPA:

- `@Entity` → convierte la clase en una tabla (`videojuego`).
- `@Id` + `@GeneratedValue` → `id` es la clave primaria y se autogenera.
- Cada atributo (`titulo`, `genero`, `plataforma`, `precio`, `anioLanzamiento`) se mapea a una columna de la tabla.

## 2. Repository: `VideojuegoRepository`

Extiende `JpaRepository<Videojuego, Long>`, por lo que obtiene gratis:

- `save(v)` — guarda o actualiza.
- `findAll()` — lista todos.
- `findById(id)` — busca uno por id.
- `deleteById(id)` — borra por id.

Además se declaró una **consulta por método**:

```java
List<Videojuego> findByGenero(String genero);
```

Spring Data interpreta el nombre del método y genera el SQL automáticamente (equivale a `SELECT * FROM videojuego WHERE genero = ?`), sin escribir una sola línea de SQL.

## 3. CRUD — qué uso y para qué

| Operación | Método usado | Para qué sirve |
|---|---|---|
| **Create** | `repo.save(v)` | Registrar un videojuego nuevo cuando el usuario lo agrega al catálogo. |
| **Read** | `repo.findAll()` / `repo.findById(id)` / `repo.findByGenero(genero)` | Listar el catálogo completo, ver el detalle de un juego, o filtrar por género (ej. "Acción"). |
| **Update** | `repo.save(v)` (con un `id` ya existente) | Corregir datos de un videojuego, como el precio o la plataforma. |
| **Delete** | `repo.deleteById(id)` | Quitar un videojuego descontinuado del catálogo. |

`save()` sirve tanto para crear como para actualizar: si el objeto no tiene `id` (o tiene uno que no existe), JPA hace un `INSERT`; si el `id` ya existe en la tabla, hace un `UPDATE`.

## 4. Estructura del proyecto

```
07-week/
├── Videojuego.java           (entity)
├── VideojuegoRepository.java (repository)
├── VideojuegoService.java    (uso del CRUD desde el service)
└── README.md                 (esta explicación)
```


