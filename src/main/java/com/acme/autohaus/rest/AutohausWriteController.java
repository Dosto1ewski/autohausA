package com.acme.autohaus.rest;

import com.acme.autohaus.service.AutohausWriteService;
import com.acme.autohaus.service.NameExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;
import static  com.acme.autohaus.rest.AutohausGetController.ID_PATTERN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;
import com.acme.autohaus.service.ConstraintViolationsException;
import com.acme.autohaus.service.VersionOutdatedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;
import static org.springframework.http.HttpStatus.PRECONDITION_REQUIRED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Controller
@RequestMapping("/rest")
@RequiredArgsConstructor
@Slf4j
public class AutohausWriteController {
    private final AutohausWriteService service;
    private final AutohausMapper mapper;

    /**
     * Einen neuen Autohaus-Datensatz anlegen.
     *
     * @param autohausDTO Das Autohausobjekt aus dem eingegangenen Request-Body.
     * @param request     Das Request-Objekt, um `Location` im Response-Header zu erstellen.
     * @return Response mit Statuscode 201 einschließlich Location-Header oder Statuscode 422 falls Constraints verletzt
     * sind oder der Name bereits existiert oder Statuscode 400 falls syntaktische Fehler im Request-Body
     * vorliegen.
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Ein neues Autohaus anlegen", tags = "Neuanlegen")
    @ApiResponse(responseCode = "201", description = "Autohaus neu angelegt")
    @ApiResponse(responseCode = "400", description = "Syntaktische Fehler im Request-Body")
    @ApiResponse(responseCode = "422", description = "Ungültige Werte oder Name vorhanden")
    ResponseEntity<Void> post(@RequestBody final AutohausDTO autohausDTO, final HttpServletRequest request) {
        log.debug("post: {}", autohausDTO);
        final var autohausMap = mapper.toAutohaus(autohausDTO);
        final var autohaus = service.create(autohausMap);
        final var location = URI.create(STR."\{request.getRequestURL()}/\{autohaus.getId()}");
        return created(location).build();
    }

    /**
     * Einen vorhandenen Autohaus-Datensatz überschreiben.
     *
     * @param id          ID des zu aktualisierenden Autohauses.
     * @param autohausDTO Das Autohausobjekt aus dem eingegangenen Request-Body.
     */
    @SuppressWarnings("StringTemplateMigration")
    @PutMapping(path = "{id:" + ID_PATTERN + "}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Vorhandenes Autohaus vollständig aktualisieren", tags = "Aktualisieren")
    @ApiResponse(responseCode = "204", description = "Autohaus aktualisiert")
    @ApiResponse(responseCode = "400", description = "Syntax der Request fehlerhaft")
    @ApiResponse(responseCode = "404", description = "Autohaus nicht gefunden")
    @ApiResponse(responseCode = "412", description = "Versionsnummer falsch")
    @ApiResponse(responseCode = "422", description = "Ungültige Werte oder Kennzeichen vorhanden")
    @ApiResponse(responseCode = "428", description = "Versionsnummer fehlt")
    ResponseEntity<Void> put(
        @PathVariable final UUID id,
        @RequestBody final AutohausDTO autohausDTO,
        @RequestHeader("If-Match") final Optional<String> versionOpt,
        final HttpServletRequest request
    ) {
        log.debug("put: dto={} id={}", autohausDTO, id);
        final int version = getVersion(versionOpt, request);

        final var autohausMap = mapper.toAutohaus(autohausDTO);
        final var autohaus = service.update(autohausMap, id, version);

        log.debug("put: {}", autohaus);
        return noContent().eTag(STR."\"\{autohaus.getVersion()}\"").build();
    }

    /**
     * Ausgelagerte Methode für Versionsprüfung
     * @param versionOpt Versionsnummer
     * @param request Request-Objekt für Problem URI Konstruktion
     * @return Geprüfte Versionsnummer
     */
    private int getVersion(final Optional<String> versionOpt, final HttpServletRequest request) {
        log.trace("getVersion: versionOpt={}", versionOpt);
        if (versionOpt.isEmpty()) {
            throw new VersionInvalidException(
                PRECONDITION_REQUIRED,
                "Versionsnummer fehlt",
                URI.create(request.getRequestURL().toString())
            );
        }

        final var versionStr = versionOpt.get();
        if (versionStr.length() < 3 ||
            versionStr.charAt(0) != '"' ||
            versionStr.charAt(versionStr.length() - 1) != '"'
        ) {
            throw new VersionInvalidException(
                PRECONDITION_FAILED,
                STR."Versionsnummer falsch: \{versionStr}",
                URI.create(request.getRequestURL().toString())
            );
        }

        final int version;
        try {
            version = Integer.parseInt(versionStr.substring(1, versionStr.length() - 1));
        } catch (final NumberFormatException ex) {
            throw new VersionInvalidException(
                PRECONDITION_FAILED,
                STR."Versionsnummer falsch: \{versionStr}",
                URI.create(request.getRequestURL().toString()),
                ex
            );
        }

        log.trace("getVersion: {}", version);
        return version;
    }

    /**
     * Einen vorhandenen Autohaus-Datensatz löschen
     * @param id Autohaus-ID
     */
    @SuppressWarnings("StringTemplateMigration")
    @DeleteMapping(path = "{id:" + ID_PATTERN + "}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Vorhandenes Autohaus löschen", tags = "Löschen")
    @ApiResponse(responseCode = "204", description = "Autohaus gelöscht")
    @ApiResponse(responseCode = "404", description = "Autohaus nicht gefunden")
    void delete(@PathVariable final UUID id) {
        log.debug("delete: id={}", id);
        service.delete(id);
    }

    @ExceptionHandler
    ProblemDetail onNameExists(final NameExistsException ex, final HttpServletRequest request) {
        log.debug("onNameExists: {}", ex.getMessage());
        final var problemDetail = ProblemDetail.forStatusAndDetail(UNPROCESSABLE_ENTITY, ex.getMessage());
        problemDetail.setType(URI.create("/problem/unprocessable"));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));
        return problemDetail;
    }

    @ExceptionHandler
    ProblemDetail onConstraintViolations(final ConstraintViolationsException ex, final HttpServletRequest request) {
        log.debug("onConstraintViolation: {}", ex.getMessage());

        final var violations = ex.getViolations().stream()
            .map(violation -> STR."\{violation} \n")
            .toList();
        log.trace("onConstraintViolation: violations={}", violations);

        final var problemDetail =
            ProblemDetail.forStatusAndDetail(UNPROCESSABLE_ENTITY, violations.toString());
        problemDetail.setType(URI.create("/problem/unprocessable"));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));
        return problemDetail;
    }

    @ExceptionHandler
    ProblemDetail onVersionOutdated(final VersionOutdatedException ex, final HttpServletRequest request) {
        log.debug("onVersionOutdated: {}", ex.getMessage());
        final var problemDetail =
            ProblemDetail.forStatusAndDetail(PRECONDITION_FAILED, ex.getMessage());
        problemDetail.setType(URI.create("/problem/precondition-failed"));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));
        return problemDetail;
    }
}
