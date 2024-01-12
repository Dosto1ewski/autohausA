package com.acme.autohaus.service;

import com.acme.autohaus.entity.Autohaus;
import com.acme.autohaus.repository.AutohausRepository;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.acme.autohaus.repository.SpecificationBuilder;

/**
 * Anwendunglogik für Autohaus
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AutohausReadService {
    private final AutohausRepository repo;
    private final SpecificationBuilder specBuilder;

    /**
     * Ein Autohaus anhand seiner ID suchen.
     *
     * @param id Die Id des gesuchten Autohauses
     * @return Das gefundene Autohaus
     * @throws NotFoundException Falls kein Autohaus gefunden wurde
     */
    public Autohaus findById(final UUID id) {
        log.debug("findById: {}", id);

        final var autohaus = repo.findById(id)
            .orElseThrow(() -> new NotFoundException(id));

        log.debug("findById: {}", autohaus);
        return autohaus;
    }

    /**
     * Ein Autohaus anhand seiner ID suchen.
     *
     * @param suchkriterien Suchkriterien für das Autohaus
     * @return Alle Autohäuser entsprechend der Suchkriterien
     * @throws NotFoundException Falls kein Autohaus mit suchkriterien gefunden wurde
     */
    public Collection<Autohaus> find(final Map<String, List<String>> suchkriterien) {
        log.debug("find: {}", suchkriterien);

        if (suchkriterien.isEmpty()) {
            return repo.findAll();
        }

        if (suchkriterien.size() == 1) {
            return findSingleCriteria(suchkriterien);
        }

        final var specs = specBuilder.build(suchkriterien)
            .orElseThrow(() -> new NotFoundException(suchkriterien));

        final var autohaeuser = repo.findAll(specs);
        if (autohaeuser.isEmpty()) {
            throw new NotFoundException(suchkriterien);
        }

        log.debug("find: {}", autohaeuser);
        return autohaeuser;
    }

    /**
     * Ausgelagerte Methode für die Rückgabe von Autohäusern bei nur einem Suchkriterium
     * @param suchkriterien Suchkriterium
     * @return Autohaus-Objekt entsprechend dem Suchkriterium
     */
    private Collection<Autohaus> findSingleCriteria(final Map<String, List<String>> suchkriterien) {
        final var namen = suchkriterien.get("name");
        if (namen != null && namen.size() == 1) {
            final var autohaeuser = repo.findByName(namen.getFirst());
            if (autohaeuser.isEmpty()) {
                throw new NotFoundException(suchkriterien);
            }
            log.debug("find (name): {}", autohaeuser);
            return autohaeuser;
        }

        throw new NotFoundException(suchkriterien);
    }
}

