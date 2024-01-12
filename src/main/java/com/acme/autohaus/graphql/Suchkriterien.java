package com.acme.autohaus.graphql;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Eine Value-Klasse für Eingabedaten passend zu Suchkriterien aus dem GraphQL-Schema.
 *
 * @param name Name
 * @param homepage Homepage
 */
record Suchkriterien(
    String name,
    String homepage
) {
    /**
     * Konvertierung in eine Map.
     *
     * @return Das konvertierte Map-Objekt
     */
    Map<String, List<String>> toMap() {
        final Map<String, List<String>> map = new HashMap<>(2, 1);
        if (name != null) {
            map.put("name", Collections.singletonList(name));
        }
        if (homepage != null) {
            map.put("homepage", Collections.singletonList(homepage));
        }
        return map;
    }
}
