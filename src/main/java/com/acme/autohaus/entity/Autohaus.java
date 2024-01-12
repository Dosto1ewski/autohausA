package com.acme.autohaus.entity;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.List;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

/**
 * Daten eines Autohauses.
 */
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "autohaus")
@SuppressWarnings({"JavadocDeclaration"})
public class Autohaus {
    /**
     * ID des Autohauses.
     *
     * @param id Autohaus-ID
     * @return Autohaus-ID
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Versionsnummer für optimistische Synchronisierung
     *
     * @param version Version
     * @return Version
     */
    @Version
    private int version;

    /**
     * Name des Autohauses.
     */
    @NotBlank
    private String name;

    /**
     * Die URL zur Homepage des Autohauses.
     *
     * @param homepage Die URL zur Homepage.
     * @return Die URL zur Homepage.
     */
    private URL homepage;

    /**
     * Adresse des Autohauses.
     */
    @OneToOne(optional = false, cascade = {PERSIST, REMOVE}, fetch = LAZY, orphanRemoval = true)
    @Valid
    @NotNull
    @ToString.Exclude
    private Adresse adresse;

    /**
     * Mitarbeiter des Autohauses.
     */
    @OneToMany(cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    @NotNull
    private List<Mitarbeiter> mitarbeiter;

    /**
     * Parkplatz des Autohauses.
     */
    @Valid
    @OneToOne(optional = false, cascade = {PERSIST, REMOVE}, fetch = LAZY, orphanRemoval = true)
    @NotNull
    @ToString.Exclude
    private Parkplatz parkplatz;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * Autodaten überschreiben
     * @param autohaus Autodaten
     */
    public void set(final Autohaus autohaus) {
        name = autohaus.name;
        homepage = autohaus.homepage;
    }
}
