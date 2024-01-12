package com.acme.autohaus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.UUID;

/**
 * Aufbau eines Parkplatzes eines Autohauses. |
 */
@Builder
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parkplatz")
@SuppressWarnings({"JavadocDeclaration"})
public class Parkplatz {
    /**
     * Die ID eines Parkplatzes.
     * @param id Autohaus-ID
     * @return Autohaus-ID
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Der Name des Parkplatzes. |
     */
    @NotNull
    private String name;

    /**
     * Die maximale Anzahl an Stellplätzen des Autohauses. |
     */
    @PositiveOrZero
    private int maxKapazitaet;

    /**
     * Die verfügbare Anzahl an Stellplätzen des Autohauses. |
     */
    @PositiveOrZero
    private int kapazitaet;
}
