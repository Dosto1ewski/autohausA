package com.acme.autohaus;

import com.acme.autohaus.dev.DevConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import static com.acme.autohaus.Banner.TEXT;

/**
 * Klasse mit der main-Methode für die Anwendung auf Basis von Spring Boot.
 */
@SpringBootApplication(proxyBeanMethods = false)
@Import({com.acme.autohaus.ApplicationConfig.class, DevConfig.class})
@ImportRuntimeHints(com.acme.autohaus.ApplicationConfig.CertificateResourcesRegistrar.class)
@EnableWebSecurity
@EnableMethodSecurity
@SuppressWarnings({"ImplicitSubclassInspection", "ClassUnconnectedToPackage"})
public final class Application {
    private Application() {
    }

    /**
     * Hauptprogramm, um den Microservice zu starten.
     *
     * @param args Evtl. zusätzliche Argumente für den Start des Microservice
     */
    public static void main(final String... args) {
        final var app = new SpringApplication(Application.class);
        app.setBanner((environment, sourceClass, out) -> out.println(TEXT));
        app.run(args);
    }
}
