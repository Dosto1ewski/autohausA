package com.acme.autohaus.graphql;

import com.acme.autohaus.entity.Mitarbeiter.Position;

public record MitarbeiterInput(
    String vorname,
    String nachname,
    Position position
) {
}
