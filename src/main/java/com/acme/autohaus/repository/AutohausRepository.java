package com.acme.autohaus.repository;

import com.acme.autohaus.entity.Autohaus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository für den DB-Zugriff bei Autos
 */
@Repository
public interface AutohausRepository extends JpaRepository<Autohaus, UUID>, JpaSpecificationExecutor<Autohaus> {
    @NonNull
    @Override
    @EntityGraph(attributePaths = {"name", "homepage"})
    List<Autohaus> findAll();

    @NonNull
    @Override
    @EntityGraph(attributePaths = {"name", "homepage"})
    List<Autohaus> findAll(@NonNull Specification spec);

    @NonNull
    @Override
    @EntityGraph(attributePaths = {"name"})
    Optional<Autohaus> findById(@NonNull UUID id);

    /**
     * Autohaus anhand des Namen suchen
     * @param name Name des Autohauses
     * @return Alle gefundenen Autohäuser
     */
    @Query("""
        SELECT a
        FROM Autohaus a
        WHERE lower(a.name) LIKE concat('%', lower(:name), '%')
        """)
    @EntityGraph(attributePaths = {"name"})
    List<Autohaus> findByName(String name);

    /**
     * Existiert ein Name bereits
     * @param name Der Name
     * @return Existiert der Name?
     */
    boolean existsByName(String name);
}
