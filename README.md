# Ferretería Los Maestros - Backend

## Objetivo

Backend para el sistema de Ferretería Los Maestros. Proveerá APIs REST en JSON para el catálogo e inventario, usuarios y pedidos.

## Tecnologías y decisiones

- Java 21
- Spring Boot
- API REST con respuestas JSON
- Base de datos seleccionada por el equipo: MySQL en Amazon RDS
- Docker y AWS: requeridos por el caso; despliegue pendiente

La conexión entre el backend ejecutado en EC2 y la base de datos MySQL en Amazon RDS está pendiente de configuración.

## Arquitectura propuesta

Cada servicio será una aplicación Spring Boot independiente, con su propia configuración y API REST.

- **catalogo-service:** productos, categorías y stock. En construcción.
- **usuarios-service:** usuarios, roles y autenticación JWT. Planificado.
- **pedidos-service:** pedidos, reservas e historial de compras. Planificado.

La estructura final se ajustará al modelo de datos y a las reglas del caso.

## Equipo y responsabilidades

- **Sebastián Maquera:** catálogo e inventario.
- **Camilo Alarcón:** usuarios, roles y autenticación.
- **Jonathan Ulloa:** pedidos, reservas e integración general.

## Estado del avance

### Implementado

- Repositorio backend creado.
- Proyecto base Spring Boot con Maven y Java 21.
- Nombre de aplicación configurado como `catalogo-service`.

### Pendiente

- Definir el modelo de datos y el diagrama entidad-relación.
- Configurar y comprobar la conexión a Oracle OCI.
- Implementar endpoints y reglas de negocio.
- Implementar autenticación JWT y permisos por rol.
- Definir la conexión entre AWS y OCI.
- Incorporar Docker y documentar el despliegue.
- Integrar el frontend con las APIs.

## Repositorios

- Frontend: https://github.com/SebastianMaquera/Ferreteria-Los-Maestros
- Backend: https://github.com/SebastianMaquera/Ferreteria-Los-Maestros-BACKEND

## Ejecución local

Requisito: Java 21.

```bash
./mvnw spring-boot:run
