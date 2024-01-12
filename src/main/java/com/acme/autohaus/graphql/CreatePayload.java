package com.acme.autohaus.graphql;

import java.util.UUID;

/**
 * Value-Klasse für das Resultat, wenn an der GraphQL-Schnittstelle ein neues Autohaus angelegt wurde.
 *
 * @param id ID des neu angelegten Autohauses
 */
record CreatePayload(UUID id) {
}
