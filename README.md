Este repositorio contiene el backend desarrollado en Spring Boot para el caso “Letras y Papeles”. El proyecto gestiona clientes, pedidos, productos, reservas, inventario, notificaciones y roles, integrando seguridad mediante JWT (JSON Web Token).

Cobertura de código alcanzada: 100 % (Jacoco)
Pruebas unitarias e integración implementadas.
Desarrollado con el contenido correspondiente a las semanas 4 y 5.

Tecnologías utilizadas: Java 17, Spring Boot 3, Spring Security, JWT (JSON Web Token), Maven, Jacoco, JUnit 5, Mockito, Lombok, MySQL (driver incluido).

Cómo ejecutar el proyecto
1. Clonar el repositorio:
git clone https://github.com/mcorteze/PBY2202_Exp2_S6.git
cd PBY2202_Exp1_S6_Caso_actividad_Letras_y_papeles (forma A)

2. Configurar base de datos
Editar el archivo application.properties o application.yml con los datos de conexión a MySQL:
spring.datasource.url=jdbc:mysql://localhost:3306/letrasypapeles
spring.datasource.username=root
spring.datasource.password=tu_password

3. Cobertura de pruebas
Se implementaron pruebas unitarias y de integración para cubrir: Entities, Services, Controllers, Configuración de seguridad y filtros JWT y Clase principal BackendApplication.

4. Para alcanzar el 100 % de cobertura se aplicaron prácticas como:
Inyección de dependencias por constructor.
Uso de @RequiredArgsConstructor de Lombok.
Mocks de dependencias con Mockito.
Definición de fechas fijas en entidades para evitar inconsistencias con LocalDateTime.

5. Generar informe de cobertura
mvn clean test
mvn clean verify

El informe se encontrará en:
target/site/jacoco/index.html

6. Seguridad
Autenticación y autorización con JWT.
Protección de endpoints según roles:
ADMIN
GERENTE
EMPLEADO
CLIENTE
Pruebas de seguridad implementadas con Spring Security Test y MockMvc.

7. Estructura del proyecto
src/main/java/com/letrasypapeles/backend
src/test/java/com/letrasypapeles/backend



