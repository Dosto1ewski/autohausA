package com.acme.autohaus.repository;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.acme.autohaus.entity.Adresse;
import com.acme.autohaus.entity.Autohaus;
import com.acme.autohaus.entity.Mitarbeiter;
import com.acme.autohaus.entity.Mitarbeiter.Position;
import com.acme.autohaus.entity.Parkplatz;
import java.net.URL;

/**
 * Emulation der Datenbasis für persistente Autohaeuser.
 */
final class DB {
    /**
     * Liste der Autohaeuser zur Emulation der DB.
     */
    @SuppressWarnings("StaticCollection")
    static final List<Autohaus> AUTOHAEUSER;

    static {
        // Helper-Methoden ab Java 9: List.of(), Set.of, Map.of, Stream.of
        // List.of() baut eine unveraenderliche Liste: kein Einfuegen, Aendern, Loeschen
        AUTOHAEUSER = Stream.of(
                Autohaus.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                    .adresse(Adresse.builder()
                        .plz("76131")
                        .ort("Karlsruhe")
                        .build())
                    .name("Yorckhaus")
                    .homepage(buildURL("https://www.Yorckhaus.com"))
                    .parkplatz(Parkplatz.builder().maxKapazitaet(34).build())
                    .mitarbeiter(Stream.of(
                        Mitarbeiter.builder()
                            .vorname("Lukas")
                            .nachname("Scholz")
                            .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                            .position(Position.MANAGER)
                            .build(),
                        Mitarbeiter.builder()
                            .vorname("Paul")
                            .nachname("Clauss")
                            .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                            .position(Position.SEKRETAER)
                            .build(),
                        Mitarbeiter.builder()
                            .vorname("Heinrich")
                            .nachname("Precht")
                            .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                            .position(Position.VERKAEUFER)
                            .build()
                    ).collect(Collectors.toList()))
                    .build(),
                Autohaus.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                    .adresse(Adresse.builder()
                        .plz("70372")
                        .ort("Stuttgart")
                        .build())
                    .name("Benzhaus")
                    .homepage(buildURL("https://www.Benzhaus.com"))
                    .parkplatz(Parkplatz.builder().maxKapazitaet(34).build())
                    .mitarbeiter(Stream.of(
                        Mitarbeiter.builder()
                            .vorname("Jan")
                            .nachname("Böhmermann")
                            .id(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                            .position(Position.MANAGER)
                            .build(),
                        Mitarbeiter.builder()
                            .vorname("Paulina")
                            .nachname("Rehbein")
                            .id(UUID.fromString("00000000-0000-0000-0000-000000000004"))
                            .position(Position.SEKRETAER)
                            .build(),
                        Mitarbeiter.builder()
                            .vorname("Kai")
                            .nachname("Schmidt")
                            .id(UUID.fromString("00000000-0000-0000-0000-000000000005"))
                            .position(Position.VERKAEUFER)
                            .build()
                    ).collect(Collectors.toList()))
                    .build(),
                Autohaus.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                    .adresse(Adresse.builder()
                        .plz("60388")
                        .ort("Frankfurt")
                        .build())
                    .name("Volkshaus")
                    .homepage(buildURL("https://www.Benzhaus.com"))
                    .parkplatz(Parkplatz.builder().maxKapazitaet(34).build())
                    .mitarbeiter(Stream.of(
                        Mitarbeiter.builder()
                            .vorname("Joseph")
                            .nachname("Berolth")
                            .id(UUID.fromString("00000000-0000-0000-0000-000000000006"))
                            .position(Position.MANAGER)
                            .build(),
                        Mitarbeiter.builder()
                            .vorname("Mark")
                            .nachname("Rehbein")
                            .id(UUID.fromString("00000000-0000-0000-0000-000000000007"))
                            .position(Position.SEKRETAER)
                            .build(),
                        Mitarbeiter.builder()
                            .vorname("Melina")
                            .nachname("Weber")
                            .id(UUID.fromString("00000000-0000-0000-0000-000000000008"))
                            .position(Position.VERKAEUFER)
                            .build()
                    ).collect(Collectors.toList()))
                    .build()
            )
            // CAVEAT Stream.toList() erstellt eine "immutable" List
            .collect(Collectors.toList());
    }

    private DB() {
    }

    private static URL buildURL(final String url) {
        try {
            return URI.create(url).toURL();
        } catch (final MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
