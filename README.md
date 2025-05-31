Descripción
Aplicación de escritorio para administrar una biblioteca con funcionalidades CRUD completas (Crear, Leer, Actualizar, Eliminar) para libros, DVDs y revistas. Desarrollada en Java con interfaz Swing y conexión a MySQL, incluye búsqueda avanzada y generación automática de la base de datos con datos de ejemplo.

Características Principales
✅ Gestión completa de libros, DVDs y revistas
✅ Búsqueda avanzada por múltiples criterios (título, autor/director, género)
✅ Auto-configuración de la base de datos (crea tablas e inserta 20 registros de ejemplo por categoría)
✅ Interfaz intuitiva con Java Swing
✅ Manejo de transacciones para integridad de datos
Tecnologías
• Java 11 • Swing • MySQL • Maven

Configuración Rápida

Clonar repositorio:
git clone [url-del-repositorio]

Editar src/main/resources/database.properties con tus credenciales MySQL

Ejecutar:
mvn clean install && mvn exec:java -Dexec.mainClass="com.biblioteca.Main"

Estructura del Proyecto

src/
├── main/
│   ├── java/com/biblioteca/
│   │   ├── controller/  # Lógica de control
│   │   ├── dao/         # Conexión a MySQL
│   │   ├── model/       # Clases de datos
│   │   ├── view/        # Interfaces gráficas
│   │   └── Main.java    # Inicio
│   └── resources/       # Configuración
pom.xml                  # Dependencias Maven


Funcionalidades Destacadas
• CRUD completo para los 3 tipos de materiales
• Búsqueda combinada: Ej: libros de "García Márquez" del género "Realismo mágico"
• Validación de datos en formularios
• Mensajes de confirmación para operaciones críticas

Requisitos
• JDK 11+ • MySQL 5.7+ • Maven 3.6+

Solución de Problemas Comunes
⚠️ Error de conexión: Verificar usuario/contraseña en database.properties
⚠️ Tablas no creadas: Ejecutar manualmente el script SQL en ConexionBD.java
⚠️ Datos no visibles: Confirmar que MySQL acepta conexiones locales

Notas
• La aplicación crea automáticamente la base de datos biblioteca al iniciar
• Usa INSERT IGNORE para evitar duplicados al insertar datos de ejemplo

Ejecución Alternativa
Generar JAR ejecutable:
mvn package
java -jar target/biblioteca-swing-1.0-SNAPSHOT.jar

