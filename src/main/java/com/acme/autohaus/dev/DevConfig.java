package com.acme.autohaus.dev;

import org.springframework.context.annotation.Profile;
import static com.acme.autohaus.dev.DevConfig.DEV;

/**
 * Konfigurationsklasse für die Anwendung bzw. den Microservice, falls das Profile _dev_ aktiviert ist.
 */
@Profile(DEV)
@SuppressWarnings({"ClassNamePrefixedWithPackageName", "HideUtilityClassConstructor"})
public class DevConfig implements com.acme.autohaus.dev.LogRequestHeaders, com.acme.autohaus.dev.K8s {
    /**
     * Konstante für das Spring-Profile "dev".
     */
    public static final String DEV = "dev";

    DevConfig() {
    }
}
