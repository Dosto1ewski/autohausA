package com.acme.autohaus.rest;
import java.net.URL;
import java.util.List;

/**
 * ValueObject für das Neuanlegen und Ändern eines neuen Autohauses.
 * @param name gültiger name eines Autohauses.
 * @param homepage gültige homepage des Autohauses
 * @param adresse gültige Adresse eines Autohauses.
 * @param parkplatz gültiger Parkplatz eines Autohauses mit eingeschränkten Werten.
 * @param mitarbeiter gültige Mitarbeiter Liste eines Autohauses
 */
record AutohausDTO (
    String name,

    URL homepage,

    AdresseDTO adresse,

    List<MitarbeiterDTO> mitarbeiter,

    ParkplatzDTO parkplatz
    ) {}
