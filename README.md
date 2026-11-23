# Ferretería Los Maestros — Backend

API REST para el proyecto Ferretería Los Maestros.

## Tecnologías

- Java 21
- Spring Boot 4.1.2
- Spring Security, Spring Data JPA y JWT
- MySQL 8.4 en Amazon RDS
- Amazon EC2
- Swagger / OpenAPI

La aplicación se conecta a la base de datos `ferreteria` en Amazon RDS. Los usuarios y demás datos se guardan en MySQL.

## Documentación de la API

- Swagger UI: http://32.202.108.232:8080/swagger-ui/index.html
- OpenAPI JSON: http://32.202.108.232:8080/v3/api-docs

La IP pública corresponde a la instancia EC2 actual y puede cambiar.

## Endpoints disponibles

| Método | Ruta | Acceso |
|---|---|---|
| POST | `/api/auth/register` | Público |
| POST | `/api/auth/login` | Público |
| GET | `/api/admin/usuarios` | ADMIN |
| GET | `/api/admin/usuarios/{id}` | ADMIN |
| PUT | `/api/admin/usuarios/{id}` | ADMIN |
| DELETE | `/api/admin/usuarios/{id}` | ADMIN |
| GET | `/api/productos` | Usuario autenticado |
| GET | `/api/productos/{codigo}` | Usuario autenticado |

El registro asigna el rol `CLIENTE` desde el servidor. Las contraseñas se procesan con un codificador antes de guardarse. El endpoint DELETE desactiva al usuario; no elimina físicamente su registro.

## Estado del avance de usuarios — Semana 15

| Requisito | Diseñado | Implementado | Probado | Evidencia o pendiente |
|---|---|---|---|---|
| Tabla de usuarios y persistencia en MySQL RDS | Sí | Sí | Sí | Se consultaron usuarios guardados en RDS. |
| Registrar usuario | Sí | Sí | Sí | Respondió HTTP 201 y el servidor asignó el rol `CLIENTE`. |
| Iniciar sesión y emitir JWT | Sí | Sí | Sí | Respondió HTTP 200; el token Bearer tiene vigencia de 3600 segundos. |
| Listar usuarios | Sí | Sí | Sí | `GET /api/admin/usuarios` respondió HTTP 200 con token ADMIN. |
| Consultar usuario por ID | Sí | Sí | Sí | `GET /api/admin/usuarios/{id}` respondió HTTP 200 con token ADMIN. |
| Modificar usuario | Sí | Sí | Sí | `PUT /api/admin/usuarios/{id}` respondió HTTP 200. |
| Desactivar usuario | Sí | Sí | Sí | DELETE respondió HTTP 204 y el usuario quedó con `activo: false`. |
| Protección por JWT y rol | Sí | Sí | Sí | Sin token se obtuvo HTTP 401; con rol insuficiente, HTTP 403. |
| Validación de datos de entrada | Sí | Sí | Sí | Correo inválido en POST /api/auth/register respondió HTTP 400. |al actualizar respondió HTTP 400. Falta probar datos inválidos en el registro. |
| Acceso de cada usuario solo a sus propios datos | Sí | Pendiente de verificar | Pendiente | Falta registrar evidencia de esta prueba. |
| Formularios de inicio de sesión y registro del frontend conectados a la API | Sí | Pendiente de verificar | Pendiente | Confirmar que muestran estados de carga, éxito y error. |

## Ejecución

Requisitos: Java 21 y acceso a la base de datos MySQL en RDS.

Configura las variables en la terminal antes de iniciar la aplicación. Repite este paso en cada terminal nueva:

```bash
export DB_URL='jdbc:mysql://ferreteria-mysql.codbl4fv0amk.us-east-1.rds.amazonaws.com:3306/ferreteria'
export DB_USERNAME='admin'
read -rsp 'Contraseña de RDS: ' DB_PASSWORD
printf '\n'
export DB_PASSWORD
export JWT_SECRET="$(openssl rand -hex 32)"
./mvnw spring-boot:run
```

No subas contraseñas, secretos ni archivos `.env` al repositorio.

## Repositorios

- Frontend: https://github.com/SebastianMaquera/Ferreteria-Los-Maestros.git
- Backend: https://github.com/SebastianMaquera/Ferreteria-Los-Maestros-BACKEND.git

## Equipo y responsabilidades

- Sebastián Maquera: catálogo e inventario.
- Camilo Alarcón: usuarios, roles y autenticación.
- Jonathan Ulloa: Swagger y DTO de administración de usuarios.
