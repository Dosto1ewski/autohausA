package com.acme.autohaus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.UUID;

/**
 * Aufbau eines Mitarbeiters eines Autohauses. |
 */
@Builder
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mitarbeiter")
@SuppressWarnings({"JavadocDeclaration"})
public class Mitarbeiter {
    /**
     * Die ID eines Mitarbeiters. |
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Der Vorname eines Mitarbeiters.
     * @param id Autohaus-ID
     * @return Autohaus-ID
     */
    @NotBlank
    private String vorname;

    /**
     * Ein Muster für einen gültigen Nachnamen. |
     */
    public static final String NACHNAME_PATTERN =
        "(o'|von|von der|von und zu|van)?[A-ZÄÖÜ][a-zäöüß]+(-[A-ZÄÖÜ][a-zäöüß]+)?";

    /**
     * Der Nachname eines Mitarbeiters. |
     */
    @NotNull
    @Pattern(regexp = NACHNAME_PATTERN)
    private String nachname;

    /**
     * Die Position eines Mitarbeiters. |
     */
    @EqualsAndHashCode.Include
    private Position position;

    /**
     * Die möglichen Positionen der Mitarbeiter. |
     */
    public enum Position {
        SEKRETAER,
        MANAGER,
        VERKAEUFER
    }
}
