package com.acme.autohaus.dev;

import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import static org.springframework.boot.cloud.CloudPlatform.KUBERNETES;

/**
 * Protokoll-Ausgabe, wenn Kubernetes erkannt wird.
 */
interface K8s {
    /**
     * Protokoll-Ausgabe, wenn Kubernetes erkannt wird.
     *
     * @return Listener zur Ausgabe, ob Kubernetes erkannt wird.
     */
    @Bean
    @ConditionalOnCloudPlatform(KUBERNETES)
    default ApplicationListener<ApplicationReadyEvent> detectK8s() {
        return event -> LoggerFactory.getLogger(K8s.class).debug("Plattform \"Kubernetes\"");
    }
}
