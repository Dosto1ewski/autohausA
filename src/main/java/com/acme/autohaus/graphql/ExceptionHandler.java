package com.acme.autohaus.graphql;

import com.acme.autohaus.entity.Autohaus;
import com.acme.autohaus.service.ConstraintViolationsException;
import com.acme.autohaus.service.NotFoundException;
import graphql.GraphQLError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.graphql.execution.ErrorType.BAD_REQUEST;
import static org.springframework.graphql.execution.ErrorType.NOT_FOUND;

/**
 * Abbildung von Exceptions auf GraphQLError.
 */
@ControllerAdvice
final class ExceptionHandler {
    @GraphQlExceptionHandler
    @SuppressWarnings("unused")
    GraphQLError onNotFound(final NotFoundException ex) {
        final var id = ex.getId();
        final var message = id == null
            ? STR."Kein Autohaus gefunden: suchkriterien=\{ex.getSuchkriterien()}"
            : STR."Kein Autohaus mit der ID \{id} gefunden";

        return GraphQLError.newError()
            .errorType(NOT_FOUND)
            .message(message)
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
        final List<Object> path = new ArrayList<>(5);
        path.add("input");
        for (final Path.Node node: violation.getPropertyPath()) {
            path.add(node.toString());
        }
        return GraphQLError.newError()
            .errorType(BAD_REQUEST)
            .message(violation.getMessage())
            .path(path)
            .build();
    }
}
