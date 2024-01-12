package com.acme.autohaus.graphql;

import com.acme.autohaus.entity.Adresse;
import com.acme.autohaus.entity.Autohaus;
import com.acme.autohaus.entity.Mitarbeiter;
import com.acme.autohaus.entity.Parkplatz;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;

/**
 * Mapper zwischen Entity-Klassen.
 */
@Mapper(componentModel = "spring", nullValueIterableMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
interface AutohausInputMapper {
    /**
     * Ein AutohausInput-Objekt in ein Objekt für Autohaus konvertieren.
     *
     * @param input AutohausInput ohne ID
     * @return Konvertiertes Autohaus-Objekt
     */
    @Mapping(target = "id", ignore = true)
    Autohaus toAutohaus(AutohausInput input);

    /**
     * Ein AdresseInput-Objekt in ein Objekt für Adresse konvertieren.
     *
     * @param input AdresseInput ohne autohaus
     * @return Konvertiertes Adresse-Objekt
     */
    Adresse toAdresse(AdresseInput input);

    /**
     * Ein MitarbeiterInput-Objekt in ein Objekt für Mitarbeiter konvertieren.
     *
     * @param input MitarbeiterInput-Objekt ohne autohaus
     * @return Konvertiertes Mitarbeiter-Objekt
     */
    @Mapping(target = "id", ignore = true)
    Mitarbeiter toMitarbeiter(MitarbeiterInput input);

    /**
     * Ein ParkplatzInput-Objekt in ein Objekt für Parkplatz konvertieren.
     *
     * @param input ParkplatzInput-Objekt ohne autohaus
     * @return Konvertiertes Parkplatz-Objekt
     */
    @Mapping(target = "id", ignore = true)
    Parkplatz toParkplatz(ParkplatzInput input);
}
