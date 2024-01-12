package com.acme.autohaus;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.Objects;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.security.core.SpringSecurityCoreVersion;

@SuppressWarnings({
    "AccessOfSystemProperties",
    "CallToSystemGetenv",
    "UtilityClassCanBeEnum",
    "UtilityClass",
    "ClassUnconnectedToPackage"
})
@SuppressFBWarnings("NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
final class Banner {

    // http://patorjk.com/software/taag/#p=display&f=Slant&t=kunde%20v1
    private static final String FIGLET = """
                             **            **                              \s
                            /**           /**                              \s
          ******   **   ** ******  ****** /**       ******   **   **  ******
         //////** /**  /**///**/  **////**/******  //////** /**  /** **////\s
          ******* /**  /**  /**  /**   /**/**///**  ******* /**  /**//*****\s
         **////** /**  /**  /**  /**   /**/**  /** **////** /**  /** /////**
        //********//******  //** //****** /**  /**//********//****** ******\s
         ////////  //////    //   //////  //   //  ////////  ////// ////// \s

        """;
    private static final String JAVA = STR."\{Runtime.version()} - \{System.getProperty("java.vendor")}";
    private static final String OS_VERSION = System.getProperty("os.name");
    private static final InetAddress LOCALHOST = getLocalhost();
    private static final long MEGABYTE = 1024L * 1024L;
    private static final Runtime RUNTIME = Runtime.getRuntime();
    private static final String SERVICE_HOST = System.getenv("AUTOHAUS_SERVICE_HOST");
    private static final String SERVICE_PORT = System.getenv("AUTOHAUS_SERVICE_PORT");
    private static final String KUBERNETES = SERVICE_HOST == null
        ? "N/A"
        : STR."AUTOHAUS_SERVICE_HOST=\{SERVICE_HOST}, AUTOHAUS_SERVICE_PORT=\{SERVICE_PORT}";
    private static final String USERNAME = System.getProperty("user.name");

    /**
     * Banner für den Server-Start.
     */
    static final String TEXT = """

        $figlet
        (C) Juergen Zimmermann, Hochschule Karlsruhe
        Version             2023.10.0
        Spring Boot         $springBoot
        Spring Security     $springSecurity
        Spring Framework    $spring
        Java                $java
        Betriebssystem      $os
        Rechnername         $rechnername
        IP-Adresse          $ip
        Heap: Size          $heapSize MiB
        Heap: Free          $heapFree MiB
        Kubernetes          $kubernetes
        Username            $username
        JVM Locale          $locale
        GraphiQL            /graphiql
        OpenAPI             /swagger-ui.html /v3/api-docs.yaml
        H2 Console          /h2-console (JDBC URL: "jdbc:h2:mem:testdb" mit User "sa" und Passwort "")
        """
        .replace("$figlet", FIGLET)
        .replace("$springBoot", SpringBootVersion.getVersion())
        .replace("$springSecurity", SpringSecurityCoreVersion.getVersion())
        .replace("$spring", Objects.requireNonNull(SpringVersion.getVersion()))
        .replace("$java", JAVA)
        .replace("$os", OS_VERSION)
        .replace("$rechnername", LOCALHOST.getHostName())
        .replace("$ip", LOCALHOST.getHostAddress())
        .replace("$heapSize", String.valueOf(RUNTIME.totalMemory() / MEGABYTE))
        .replace("$heapFree", String.valueOf(RUNTIME.freeMemory() / MEGABYTE))
        .replace("$kubernetes", KUBERNETES)
        .replace("$username", USERNAME)
        .replace("$locale", Locale.getDefault().toString());

    @SuppressWarnings("ImplicitCallToSuper")
    private Banner() {
    }

    private static InetAddress getLocalhost() {
        try {
            return InetAddress.getLocalHost();
        } catch (final UnknownHostException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
