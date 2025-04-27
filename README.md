# Sistema de Gestión de Biblioteca

## Información general
- **Nombre del proyecto:** Sistema de Gestión de Biblioteca
- **Lenguaje:** Java
- **Paquetes:**
  - `biblioteca.app` (Aplicación principal)
  - `biblioteca.model` (Modelos y entidades)
  - `biblioteca.enums` (Enumeraciones)
  - `biblioteca.interfaces` (Interfaces)

## Descripción
Sistema que permite gestionar de manera sencilla una biblioteca mediante la consola, ofreciendo funcionalidades como:
- Registro de usuarios.
- Gestión de libros, revistas y DVDs.
- Préstamo y devolución de recursos.
- Visualización de recursos, usuarios e historial de préstamos.
- Persistencia de datos mediante archivos de texto.

## Estructura del proyecto

### Paquete `biblioteca.app`
- **Main.java**  
  Contiene el menú principal y la lógica de interacción con el usuario.

### Paquete `biblioteca.model`
- **RecursoBiblioteca** (abstracta): Clase base de los recursos, contiene `id`, `título` y `estado`.
- **Libro**: Extiende `RecursoBiblioteca`, añade el autor del libro.
- **Revista**: Extiende `RecursoBiblioteca`, añade el número de edición.
- **DVD**: Extiende `RecursoBiblioteca`, añade duración en minutos.
- **Usuario**: Representa un usuario de la biblioteca.
- **Prestamo**: Representa el registro de un préstamo.
- **BibliotecaManager**: Gestiona usuarios, recursos y operaciones de préstamo/devolución.

### Paquete `biblioteca.enums`
- **EstadoRecurso**: Define los estados posibles de los recursos (`DISPONIBLE`, `PRESTADO`, `RESERVADO`).

### Paquete `biblioteca.interfaces`
- **Prestamista**: Interfaz que define el comportamiento de prestar y devolver recursos.

## Funcionalidades principales
- **Agregar usuarios:** Registro de usuarios únicos por ID.
- **Agregar recursos:** Libros, revistas o DVDs con atributos específicos.
- **Prestar recursos:** Cambio de estado a `PRESTADO` y registro en historial.
- **Devolver recursos:** Cambio de estado a `DISPONIBLE`.
- **Listar recursos:** Visualización detallada de todos los recursos.
- **Listar usuarios:** Visualización de usuarios registrados.
- **Ver historial de préstamos:** Muestra los movimientos de préstamos realizados.

## Persistencia de datos
- Usuarios almacenados en `usuarios.txt`.
- Recursos almacenados en `recursos.txt`.
- Al iniciar la aplicación, se cargan automáticamente.

## Consideraciones técnicas
- **Validaciones:** Control de entradas incorrectas en consola.
- **Principio de responsabilidad única:** Cada clase tiene una función bien definida.
- **Extensible:** Permite agregar nuevos tipos de recursos fácilmente.

## Requisitos
- Java 8 o superior.
- IDE recomendado: Eclipse

---
