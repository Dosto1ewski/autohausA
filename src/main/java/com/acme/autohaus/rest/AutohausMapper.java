package com.acme.autohaus.rest;

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
interface AutohausMapper {
    /**
     * Ein DTO-Objekt von AutohausDTO in ein Objekt für Autohaus konvertieren.
     *
     * @param dto DTO-Objekt für AutohausDTO ohne ID
     * @return Konvertiertes Autohaus-Objekt mit null als ID
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "erzeugt", ignore = true)
    @Mapping(target = "aktualisiert", ignore = true)
    Autohaus toAutohaus(AutohausDTO dto);

    /**
     * Ein DTO-Objekt von MitarbeiterDTO in ein Objekt für Mitarbeiter konvertieren.
     *
     * @param dto DTO-Objekt für MitarbeiterDTO ohne ID
     * @return Konvertiertes Mitarbeiter-Objekt mit null als ID
     */
    @Mapping(target = "id", ignore = true)
    Mitarbeiter toMitarbeiter(MitarbeiterDTO dto);

    /**
     * Ein DTO-Objekt von ParkplatzDTO in ein Objekt für Parkplatz konvertieren.
     *
     * @param dto DTO-Objekt für ParkplatzDTO ohne ID
     * @return Konvertiertes Parkplatz-Objekt mit null als ID
     */
    @Mapping(target = "id", ignore = true)
    Parkplatz toParkplatz(ParkplatzDTO dto);
}
