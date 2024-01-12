package com.acme.autohaus.graphql;

/**
 * Adressdaten.
 *
 * @param maxKapazitaet Die maximale Anzahl an vorhandenen Parkplätzen.
 * @param kapazitaet Die Anzahl an verfügbaren Parkplätzen.
 */
@SuppressWarnings("RecordComponentNumber")
public record ParkplatzInput(
    int maxKapazitaet,
    int kapazitaet
) {
}
