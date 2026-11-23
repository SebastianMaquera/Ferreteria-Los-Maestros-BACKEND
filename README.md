# Ferretería Los Maestros — Backend

API REST del proyecto Ferretería Los Maestros.

## Tecnologías

- Java 21 y Spring Boot 4.1.2
- Spring Security, Spring Data JPA y JWT
- MySQL 8.4 en Amazon RDS
- Amazon EC2
- Swagger / OpenAPI

La base de datos utilizada es `ferreteria`. La aplicación Spring Boot se conecta a RDS mediante variables de entorno.

## Documentación de la API

- Swagger UI: http://32.202.108.232:8080/swagger-ui/index.html
- OpenAPI JSON: http://32.202.108.232:8080/v3/api-docs

La dirección pública corresponde a la instancia EC2 actual y puede cambiar si cambia su IP pública.

## Endpoints disponibles

| Método | Ruta | Acceso |
|---|---|---|
| POST | `/api/auth/register` | Público |
| POST | `/api/auth/login` | Público |
| GET | `/api/admin/usuarios` | ADMIN |
| GET | `/api/admin/usuarios/{id}` | ADMIN |
| PUT | `/api/admin/usuarios/{id}` | ADMIN |
| DELETE | `/api/admin/usuarios/{id}` | ADMIN |
| GET | `/api/productos` | Según configuración de seguridad |
| GET | `/api/productos/{codigo}` | Según configuración de seguridad |

El endpoint DELETE desactiva al usuario; no elimina físicamente su registro.

## Estado del avance de usuarios — Semana 15

| Requisito | Diseñado | Implementado | Probado | Evidencia / pendiente |
|---|---:|---:|---:|---|
| Tabla de usuarios y persistencia en MySQL RDS | Sí | Sí | Sí | Se consultaron usuarios guardados en RDS. |
| Registro de usuarios | Sí | Sí | Sí | Respondió HTTP 201 y asignó el rol inicial CLIENTE. |
| Inicio de sesión y JWT con vigencia | Sí | Sí | Sí | El login respondió HTTP 200 y emitió un token Bearer con vigencia de 3600 segundos. |
| Listar usuarios | Sí | Sí | Sí | `GET /api/admin/usuarios` respondió HTTP 200. |
| Consultar usuario por ID | Sí | Sí | Sí | `GET /api/admin/usuarios/{id}` respondió HTTP 200 con token ADMIN. |
| Actualizar usuario | Sí | Sí | Sí | `PUT /api/admin/usuarios/{id}` respondió HTTP 200. |
| Validar datos de actualización | Sí | Sí | Sí | Un correo inválido en el PUT respondió HTTP 400. |
| Desactivar usuario | Sí | Sí | Sí | DELETE respondió HTTP 204; luego el usuario apareció con `activo: false`. |
| Protección de rutas y roles | Sí | Sí | Sí | Sin token se obtuvo HTTP 401; con rol insuficiente, HTTP 403. |
| Validaciones de registro e inicio de sesión | Sí | Parcial | Pendiente | Aplicar y probar validaciones en `AuthDtos.java`. |
| Integración de los formularios del frontend | Sí | Pendiente de verificar | Pendiente | Confirmar llamadas a la API y estados de carga, éxito y error. |

El registro asigna el rol CLIENTE desde el servidor. La contraseña se procesa mediante `PasswordEncoder` antes de guardar el usuario. Los roles disponibles son ADMIN, VENDEDOR y CLIENTE.

## Ejecución

Requisitos: Java 21 y acceso a la base de datos MySQL en RDS.

Configurar estas variables en la terminal antes de iniciar la aplicación:

- `DB_URL`: URL JDBC de la base de datos `ferreteria`.
- `DB_USERNAME`: usuario de la base de datos.
- `DB_PASSWORD`: contraseña de RDS.
- `JWT_SECRET`: secreto utilizado para firmar los tokens.

Luego ejecutar:

```bash
./mvnw spring-boot:run

```
