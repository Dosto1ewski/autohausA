package com.acme.autohaus.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;

@Getter
public final class NotFoundException extends RuntimeException {
    /**
     * Nicht-vorhandene ID.
     */
    private final UUID id;

    /**
     * Suchkriterien, zu denen nichts gefunden wurde.
     */
    private final Map<String, List<String>> suchkriterien;

    NotFoundException(final UUID id) {
        super(STR."Kein Autohaus mit der ID \{id} gefunden.");
        this.id = id;
        suchkriterien = null;
    }

    NotFoundException(final Map<String, List<String>> suchkriterien) {
        super(STR."Keine Autohaeuser gefunden: suchkriterien=\{suchkriterien}");
        this.id = null;
        this.suchkriterien = suchkriterien;
    }
}
