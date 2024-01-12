package com.acme.autohaus.rest;

import com.acme.autohaus.entity.Mitarbeiter.Position;

public record MitarbeiterDTO(
    String vorname,

    String nachname,

    Position position
) {
}
