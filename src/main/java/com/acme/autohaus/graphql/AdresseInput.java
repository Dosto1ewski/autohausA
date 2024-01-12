package com.acme.autohaus.graphql;

/**
 * Adressdaten.
 *
 * @param plz Die 5-stellige Postleitzahl als unveränderliches Pflichtfeld.
 * @param ort Der Ort als unveränderliches Pflichtfeld.
 */
@SuppressWarnings("RecordComponentNumber")
public record AdresseInput(
    String plz,
    String ort
) {
}
