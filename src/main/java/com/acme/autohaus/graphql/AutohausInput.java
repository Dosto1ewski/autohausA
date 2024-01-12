package com.acme.autohaus.graphql;

import java.net.URL;
import java.util.List;

/**
 * Eine Value-Klasse für Eingabedaten passend zu KundeInput aus dem GraphQL-Schema.
 *
 * @param name name
 * @param homepage URL der Homepage
 * @param adresse Adresse
 * @param mitarbeiter Liste der Mitarbeiter
 * @param parkplatz Parkplatz
 */
@SuppressWarnings("RecordComponentNumber")
record AutohausInput(
    String name,
    URL homepage,
    AdresseInput adresse,
    List<MitarbeiterInput> mitarbeiter,
    ParkplatzInput parkplatz
) {
}
