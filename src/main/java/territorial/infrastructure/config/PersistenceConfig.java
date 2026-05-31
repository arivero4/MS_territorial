package territorial.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/** Configuracion de JPA y DataSource para Oracle XE 10g en ms-territorial. */

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "territorial.infrastructure.adapter.out.persistence.entity")
@EnableJpaRepositories(basePackages = "territorial.infrastructure.adapter.out.persistence.repository")
public class PersistenceConfig {

}
