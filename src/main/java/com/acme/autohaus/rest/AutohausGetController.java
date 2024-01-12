package com.acme.autohaus.rest;

import com.acme.autohaus.service.AutohausReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

/**
 * Controller für die Rückgabe von Autohäusern
 */
@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
@Slf4j
public class AutohausGetController {
    /**
     * Muster für eine UUID. [\dA-Fa-f]{8}{8}-([\dA-Fa-f]{8}{4}-){3}[\dA-Fa-f]{8}{12} enthält eine "capturing group"
     * und ist nicht zulässig.
     */
    public static final String ID_PATTERN =
        "[\\dA-Fa-f]{8}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{12}";

    private final AutohausReadService service;

    /**
     * Sucht anhand der ID des Autohauses als Pfad Parameter
     * @param id Die ID des gesuchten Autohauses
     * @return Autohaus mit passender ID
     */
    @SuppressWarnings("StringTemplateMigration")
    @GetMapping(path = "{id:" + ID_PATTERN + "}", produces = HAL_JSON_VALUE)
    @Operation(summary = "Suche mit der Autohaus-ID", tags = "Suchen")
    @ApiResponse(responseCode = "304", description = "Ressource unverändert")
    @ApiResponse(responseCode = "200", description = "Autohaus gefunden")
    @ApiResponse(responseCode = "404", description = "Autohaus nicht gefunden")
    ResponseEntity<AutohausModel> getById(
        @PathVariable final UUID id,
        @RequestHeader("If-None-Match") final Optional<String> version,
        final HttpServletRequest request
    ) {
        log.debug("getById: id={}", id);
        final var autohaus = service.findById(id);

        final var currentVersion = STR."\"\{autohaus.getVersion()}\"";
        if (Objects.equals(version.orElse(null), currentVersion)) {
            return status(NOT_MODIFIED).build();
        }

        final var model = new AutohausModel(autohaus);
        final var baseUri = request.getRequestURL().toString();
        final var idUri = STR."\{baseUri}/\{autohaus.getId()}";

        final var selfLink = Link.of(idUri);
        final var listLink = Link.of(baseUri, LinkRelation.of("list"));
        final var addLink = Link.of(baseUri, LinkRelation.of("add"));
        final var updateLink = Link.of(idUri, LinkRelation.of("update"));
        final var removeLink = Link.of(idUri, LinkRelation.of("remove"));

        model.add(selfLink, listLink, addLink, updateLink, removeLink);

        log.debug("getById: {}", autohaus);
        return ok().eTag(currentVersion).body(model);
    }

    /**
     * Rückgabe aller verfügbaren Autohäuser in der DB
     * @return Alle Autohäuser in DB
     */
    @GetMapping(produces = HAL_JSON_VALUE)
    @Operation(summary = "Suche mit Suchkriterien", tags = "Suchen")
    @ApiResponse(responseCode = "200", description = "CollectionModel mit dem Autohaus")
    @ApiResponse(responseCode = "404", description = "Keine Autohaeuser gefunden")
    CollectionModel<AutohausModel> get(
        @RequestParam MultiValueMap<String, String> suchkriterien,
        final HttpServletRequest request
    ) {
            log.debug("get: suchkriterien={}", suchkriterien);
            final var autohaeuser = service.find(suchkriterien);

            final var baseUri = request.getRequestURL().toString();
            final var models = autohaeuser.stream()
                .map(autohaus -> {
                    final var model = new AutohausModel(autohaus);
                    model.add(Link.of(STR."\{baseUri}/\{autohaus.getId()}"));
                    return model;
                })
                .toList();

            log.debug("get: {}", autohaeuser);
            return CollectionModel.of(models);
    }
}
