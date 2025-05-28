# 🧠 StockMart - REST API (Backend)

API RESTful para la gestión de un sistema tipo supermercado, desarrollada en **Java con Spring Boot**.  
Permite gestionar productos, vendedores, clientes, categorías y registrar ventas con seguridad integrada.

---

## 🚀 Tecnologías usadas

- **Java 21**
- **Spring Boot**
- **Spring Security + JWT(en un futuro)**
- **Spring Data JPA**
- **MySQL**
- **Maven**
- **Postman (para pruebas de endpoints)**
- **JUnit + Mockito (para pruebas unitarias)**
- **Swagger/OpenAPI (Documentación interactiva de la API)**

---

## 📂 Estructura del proyecto

```bash
src/
├── config/             # Configuraciones generales (CORS, Swagger, etc.)
├── controller/         # Controladores REST para manejar las peticiones HTTP
├── dto/                # Objetos de transferencia de datos (DTOs)
├── entity/             # Entidades JPA que representan las tablas de la base de datos
├── exception/          # Manejo centralizado de errores personalizados
├── repository/         # Interfaces JpaRepository para acceso a datos
├── security/           # Configuración de seguridad, filtros y JWT
├── service/            # Lógica de negocio (implementaciones de servicios)
└── StockMartApplication.java  # Clase principal que inicia la aplicación
```
## ⚙️ Funcionalidades implementadas

- 🔐 Registro y autenticación con JSESSIONID
- 📦 CRUD completo de productos (con soft delete)
- 🧾 Registro de ventas y cálculo de ganancias
- 📊 Filtros por estado, categoría y productos más vendidos
- 👤 Gestión de clientes y vendedores
- 📚 Validaciones personalizadas
- 🧪 Pruebas unitarias de servicios(en construccion aun)

---
## 🔐 Seguridad

- Registro y login de usuarios con JSESSIONID
- Encriptación de contraseñas con `BCryptPasswordEncoder`
- Filtro de autorización por roles y estados

---

## 🎯 Endpoints principales

> ⚠️ Todos los endpoints requieren autenticacion excepto:`/auth/*`

| Método | Endpoint             | Descripción                      |
|--------|----------------------|----------------------------------|
| POST   | `/auth/login`        | Iniciar sesión                   |
| POST   | `/auth/register`     | Registrar nuevo usuario          |
| GET    | `/productos/mostrar` | Listar productos activos         |
| POST   | `/productos`         | Crear nuevo producto             |
| PUT    | `/productos/{id}`    | Actualizar producto              |
| DELETE | `/productos/{id}`    | Eliminar (soft delete) producto  |
| GET    | `/ventas/top`        | Obtener productos más vendidos   |
| ...    |                      | (Más endpoints según entidad)    |

---

## 📖 Documentación de la API

Esta API cuenta con documentación interactiva gracias a Swagger.

- 👉 Puedes acceder a la documentación en:  
  [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Swagger permite probar los endpoints, ver las estructuras de datos y validar los posibles errores desde el navegador sin necesidad de Postman u otra herramienta externa.


## ⚒️ Instalación y ejecución local

1. Clona el repositorio:
```bash
git clone https://github.com/tu-usuario/stockmart-backend.git

Crea una base de datos en MySQL (por ejemplo: stockmart_db)

Configura el archivo application.properties con tus credenciales de MySQL

Ejecuta el proyecto:

./mvnw spring-boot:run

Pruebas:

./mvnw test
```
## 📌 Estado del proyecto
✅ Backend funcional
🚧 Diagrama de arquitectura y frontend en desarrollo
📦 Pronto: despliegue en Azure

## ‍💻 Autor
### Sebastián Lopez
Desarrollador Backend Java | Apasionado por las arquitecturas limpias y robustas