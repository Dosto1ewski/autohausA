package com.acme.autohaus.repository;

import com.acme.autohaus.entity.Autohaus;
import com.acme.autohaus.entity.Autohaus_;
import com.acme.autohaus.entity.Mitarbeiter_;
import com.acme.autohaus.entity.Mitarbeiter;
import jakarta.persistence.criteria.Join;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class SpecificationBuilder {
    public Optional<Specification<Autohaus>> build(final Map<String, List<String>> suchkriterien) {
        log.debug("build: suchkriterien={}", suchkriterien);
        if (suchkriterien.isEmpty()) {
            return Optional.empty();
        }

        final var specs = suchkriterien
            .entrySet()
            .stream()
            .map(this::toSpecification)
            .toList();

        if (specs.isEmpty() || specs.contains(null)) {
            return Optional.empty();
        }

        return Optional.of(Specification.allOf(specs));
    }

    private Specification<Autohaus> toSpecification (final Map.Entry<String, List<String>> entry) {
        log.trace("toSpecification: entry={}", entry);
        final var key = entry.getKey();
        final var values = entry.getValue();

        if (values == null || values.size() != 1) {
            return null;
        }

        final var value = values.getFirst();
        return switch (key) {
            case "name" -> name(value);
            case "homepage" -> homepage(value);
            case "mitarbeiternachname" -> mitarbeiterNachname(value);
            default -> null;
        };
    }

    private Specification<Autohaus> name(final String input) {
        return (root, _, builder) ->
            builder.like(builder.lower(root.get(Autohaus_.name)),
                         builder.lower(builder.literal(STR."%\{input}%")));
    }

    private Specification<Autohaus> homepage(final String input) {
        return (root, _, builder) ->
            builder.like(
                builder.lower(root.get(Autohaus_.homepage).as(String.class)),
                builder.lower(builder.literal(STR."%\{input}%"))
            );
    }

    private Specification<Autohaus> mitarbeiterNachname(final String input) {
        return (root, _, builder) -> {
            Join<Autohaus, Mitarbeiter> mitarbeiterJoin = root.join(Autohaus_.mitarbeiter);
            return builder.like(builder.lower(mitarbeiterJoin.get(Mitarbeiter_.nachname)),
                builder.lower(builder.literal(STR."%\{input}%")));
        };
    }
}
