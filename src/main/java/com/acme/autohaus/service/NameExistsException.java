package com.acme.autohaus.service;

import lombok.Getter;

/**
 * Exception, falls der Name bereits existiert.
 */
@Getter
public class NameExistsException extends RuntimeException {
    /**
     * Bereits vorhandener Name.
     */
    private final String name;

    NameExistsException(@SuppressWarnings("ParameterHidesMemberVariable") final String name) {
        super(STR."Der Name \{name} existiert bereits");
        this.name = name;
    }
}
