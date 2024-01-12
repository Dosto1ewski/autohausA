package com.acme.autohaus.graphql;

import com.acme.autohaus.entity.Autohaus;
import com.acme.autohaus.service.AutohausReadService;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import static java.util.Collections.emptyMap;

/**
 * Eine Controller-Klasse für das Lesen mit der GraphQL-Schnittstelle und den Typen aus dem GraphQL-Schema.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
class AutohausQueryController {
    private final AutohausReadService service;

    /**
     * Suche anhand der Autohaus-ID.
     *
     * @param id ID des zu suchenden Autohauses
     * @return Das gefundene Autohaus
     */
    @QueryMapping("autohaus")
    Autohaus autohaus(@Argument final UUID id) {
        log.debug("findById: id={}", id);
        final var autohaus = service.findById(id);
        log.debug("findById: {}", autohaus);
        return autohaus;
    }

    /**
     * Suche mit diversen Suchkriterien.
     *
     * @param input Suchkriterien für das Filtern von Autohäusern
     * @return Die gefundenen Autohäuser als Collection
     */
    @QueryMapping("autohaeuser")
    Collection<Autohaus> find(@Argument final Optional<Suchkriterien> input) {
        log.debug("find: suchkriterien={}", input);
        final var suchkriterien = input.map(Suchkriterien::toMap).orElse(emptyMap());
        final var autohaeuser = service.find(suchkriterien);
        log.debug("find: {}", autohaeuser);
        return autohaeuser;
    }
}
