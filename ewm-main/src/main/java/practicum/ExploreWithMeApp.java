package practicum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Приложение - базовая заготовка для реализации стандартной логики SpringBoot приложений.
 * Использует такие инструменты как:
 * Keycloak, SpringSecurity, Hibernate, BD - postgresql,
 * @author Alexander Shchetinin
 * @version 0.0.1
 * 12.05.2025
 */
@SpringBootApplication /*(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class
})*/
public class ExploreWithMeApp {

    public static void main(String[] args) {
        SpringApplication.run(ExploreWithMeApp.class, args);
    }

}
