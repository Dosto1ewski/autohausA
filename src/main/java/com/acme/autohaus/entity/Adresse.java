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

@Builder
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adresse")
@SuppressWarnings({"JavadocDeclaration"})
public class Adresse {
    /**
     * Die ID einer Adresse. |
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Konstante für den regulären Ausdruck einer Postleitzahl als 5-stellige Zahl mit führender Null.
     */
    public static final String PLZ_PATTERN = "^\\d{5}$";

    /**
     * Die Postleitzahl für die Adresse.
     * @param plz Die Postleitzahl als String
     * @return Die Postleitzahl als String
     */
    @NotNull
    @Pattern(regexp = PLZ_PATTERN)
    private String plz;

    /**
     * Der Ort für die Adresse.
     * @param ort Der Ort als String
     * @return Der Ort als String
     */
    @NotBlank
    private String ort;
}
