package territorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del microservicio ms-territorial.
 *
 * <p>Gestiona la estructura territorial y productiva del sistema TerraIca:</p>
 * <ul>
 *   <li>Departamentos y Municipios de Colombia.</li>
 *   <li>Lugares de Producción (fincas o unidades productivas).</li>
 *   <li>Predios vinculados a municipio y a lugar de producción.</li>
 *   <li>Lotes con cultivo asignado dentro de un lugar de producción.</li>
 *   <li>Catálogo de cultivos hortifrutícolas y plagas fitosanitarias.</li>
 * </ul>
 *
 * <p><strong>Jerarquía de datos:</strong></p>
 * <pre>
 *   Departamento → Municipio → Predio → LugarProduccion → Lote → Cultivo → Plaga
 * </pre>
 *
 * <p>Tecnologías: Spring Boot 2.7, JPA/Hibernate, Oracle XE 10g (puerto 1522).</p>
 * <p>Puerto de escucha por defecto: {@code 8082}. Perfil de desarrollo: {@code dev}.</p>
 */
@SpringBootApplication
public class TerritorialApplication {

    /**
     * Método principal que arranca el contexto de Spring Boot.
     * Inicializa JPA, Spring Security (JWT), Swagger y los repositorios JPA.
     *
     * @param args argumentos de línea de comandos; pueden sobreescribir
     *             propiedades del archivo {@code application.yml}.
     */
    public static void main(String[] args) {
        SpringApplication.run(TerritorialApplication.class, args);
    }
}
