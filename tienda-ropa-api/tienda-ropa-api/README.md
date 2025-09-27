# Tienda Ropa API (Spring Boot + SQL Server)

Proyecto listo para importar en IntelliJ IDEA.

## Requisitos
- Java 17
- Maven 3.9+
- SQL Server en localhost con la base `Tienda_Ropa` (usar el script que compartiste)

## Configuración
Edita `src/main/resources/application.properties` si tu usuario/contraseña o puerto son distintos.

## Ejecutar
```bash
mvn spring-boot:run
```
API por defecto en `http://localhost:8080`.

## Endpoints
- `GET /api/productos` (CRUD completo para todas las entidades)
- Crear venta con items: `POST /api/ventas/crear`
```json
{
  "clienteId": 1,
  "items": [
    {"productoId": 1, "cantidad": 2, "precioUnit": 35000},
    {"productoId": 2, "cantidad": 1, "precioUnit": 120000}
  ]
}
```

> Nota: `VentaItems.Subtotal` es columna calculada en BD; el repositorio no la envía en inserts/updates.
