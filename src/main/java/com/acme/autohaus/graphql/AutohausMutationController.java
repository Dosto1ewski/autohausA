package com.acme.autohaus.graphql;

import com.acme.autohaus.service.AutohausWriteService;
import com.acme.autohaus.service.NameExistsException;
import graphql.GraphQLError;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.ConstraintViolation;
import com.acme.autohaus.service.ConstraintViolationsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import static org.springframework.graphql.execution.ErrorType.BAD_REQUEST;
import com.acme.autohaus.entity.Autohaus;

/**
 * Eine Controller-Klasse für das Schreiben mit der GraphQL-Schnittstelle und den Typen aus dem GraphQL-Schema.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
class AutohausMutationController {
    private final AutohausWriteService service;
    private final AutohausInputMapper mapper;

    /**
     * Ein neues Autohaus anlegen.
     *
     * @param input Die Eingabedaten für ein neues Autohaus
     * @return Die generierte ID für das neue Autohaus als Payload
     */
    @MutationMapping
    CreatePayload create(@Argument final AutohausInput input) {
        log.debug("create: input={}", input);
        final var autohausNew = mapper.toAutohaus(input);
        final var id = service.create(autohausNew).getId();
        log.debug("create: id={}", id);
        return new CreatePayload(id);
    }

    @GraphQlExceptionHandler
    @SuppressWarnings("unused")
    GraphQLError onNameExists(final NameExistsException ex) {
        return GraphQLError.newError()
            .errorType(BAD_REQUEST)
            .message(STR."Der Name \{ex.getName()} existiert bereits.")
            .path(List.of("input", "name"))
            .build();
    }

    @GraphQlExceptionHandler
    @SuppressWarnings("unused")
    Collection<GraphQLError> onConstraintViolations(final ConstraintViolationsException ex) {
        return ex.getViolations()
            .stream()
            .map(this::violationToGraphQLError)
            .collect(Collectors.toList());
    }

    private GraphQLError violationToGraphQLError(final ConstraintViolation<Autohaus> violation) {
        return GraphQLError.newError()
            .errorType(BAD_REQUEST)
            .message(violation.getMessage())
            .path(Arrays.asList("input", violation.getPropertyPath().toString()))
            .build();
    }
}
