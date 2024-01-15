package com.acme.autohaus.graphql;

/**
 * Adressdaten.
 *
 * @param name Der Name des Parkplatzes.
 * @param kapazitaet Die Anzahl an verfügbaren Parkplätzen.
 */
@SuppressWarnings("RecordComponentNumber")
public record ParkplatzInput(
    String name,
    int kapazitaet
) {
}
