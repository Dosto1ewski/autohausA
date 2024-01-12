package com.acme.autohaus.rest;

import com.acme.autohaus.entity.Adresse;
import com.acme.autohaus.entity.Autohaus;
import com.acme.autohaus.entity.Mitarbeiter;
import com.acme.autohaus.entity.Parkplatz;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.net.URL;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

/**
 * Model-Klasse für Spring HATEOAS. @lombok.Data fasst die Annotationsn @ToString, @EqualsAndHashCode, @Getter, @Setter
 * und @RequiredArgsConstructor zusammen.
 */
@JsonPropertyOrder({
    "name", "homepage", "adresse", "mitarbeiter", "parkplatz"
})
@Relation(collectionRelation = "autohaeuser", itemRelation = "autohaus")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@ToString(callSuper = true)
class AutohausModel extends RepresentationModel<AutohausModel> {
    private final String name;

    @EqualsAndHashCode.Include
    private final URL homepage;

    private final Adresse adresse;
    private final List<Mitarbeiter> mitarbeiter;
    private final Parkplatz parkplatz;

    AutohausModel(final Autohaus autohaus) {
        name = autohaus.getName();
        homepage = autohaus.getHomepage();
        adresse = autohaus.getAdresse();
        mitarbeiter = autohaus.getMitarbeiter();
        parkplatz = autohaus.getParkplatz();
    }
}
