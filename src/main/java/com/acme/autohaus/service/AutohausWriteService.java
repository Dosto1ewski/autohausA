package com.acme.autohaus.service;

import com.acme.autohaus.entity.Autohaus;
import com.acme.autohaus.repository.AutohausRepository;
import jakarta.validation.Validator;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AutohausWriteService {
    private final AutohausRepository repo;
    private final Validator validator;

    /**
     * Ein neues Autohaus anlegen.
     *
     * @param autohaus Das Objekt des neu anzulegenden Autohauses.
     * @return Das neu angelegte Autohaus mit generierter ID
     * @throws ConstraintViolationsException Falls mindestens ein Constraint verletzt ist.
     * @throws NameExistsException Es gibt bereits ein Autohaus mit diesem Namen.
     */
    @Transactional
    public Autohaus create(final Autohaus autohaus) {
        log.debug("create: {}", autohaus);

        final var violations = validator.validate(autohaus);
        if (!violations.isEmpty()) {
            log.debug("create: violations={}", violations);
            throw new ConstraintViolationsException(violations);
        }

        final var name = autohaus.getName();
        if (repo.existsByName(name)) {
            throw new NameExistsException(autohaus.getName());
        }

        final var autohausDB = repo.save(autohaus);
        log.debug("create: {}", autohausDB);
        return autohausDB;
    }

    /**
     * Ein vorhandenes Autohaus aktualisieren.
     *
     * @param autohaus Das Objekt mit den neuen Daten (ohne ID)
     * @param id ID des zu aktualisierenden Autohauses
     * @throws ConstraintViolationsException Falls mindestens ein Constraint verletzt ist.
     * @throws NotFoundException Falls kein Autohaus zur ID vorhanden.
     * @throws NameExistsException Es gibt bereits ein Autohaus mit dem Namen.
     */
    @Transactional
    public Autohaus update(final Autohaus autohaus, final UUID id, final int version) {
        log.debug("update: {}", autohaus);
        log.debug("update: id={}", id);

        final var violations = validator.validate(autohaus);
        if (!violations.isEmpty()) {
            log.debug("update: violations={}", violations);
            throw new ConstraintViolationsException(violations);
        }

        final var autohausDBOpt = repo.findById(id);
        if (autohausDBOpt.isEmpty()) {
            throw new NotFoundException(id);
        }

        var autohausDB = autohausDBOpt.get();
        final var versionDB = autohausDB.getVersion();
        if (version != versionDB) {
            log.debug("update: Versionsnummern req={} db={} stimmt nicht überein.", version, versionDB);
            throw new VersionOutdatedException(version);
        }

        final var name = autohaus.getName();
        if (autohausDB.getName().equals(name) && repo.existsByName(name)) {
            log.debug("update: name {} existiert bereits", name);
            throw new NameExistsException(name);
        }

        autohausDB.set(autohaus);
        autohausDB = repo.save(autohaus);
        log.debug("update: {} -> {}", autohausDB, autohaus);
        return autohausDB;
    }

    public void delete(final UUID id) {
        log.debug("delete: id={}", id);

        final var autohausDBOpt = repo.findById(id);
        if (autohausDBOpt.isEmpty()) {
            throw new NotFoundException(id);
        }

        repo.deleteById(id);
        log.debug("delete: {}", autohausDBOpt);
    }
}
