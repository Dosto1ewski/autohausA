package com.acme.autohaus.rest;

import com.acme.autohaus.service.ConstraintViolationsException;
import com.acme.autohaus.service.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

/**
 * Handler für allgemeine Exceptions in allen Controllern
 */
@ControllerAdvice
@Slf4j
class CommonExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    void onNotFound(final NotFoundException ex) {
        log.debug("onNotFound: {}", ex.getMessage());
    }

    @ExceptionHandler
    ProblemDetail onConstraintViolations(final ConstraintViolationsException ex, final HttpServletRequest request) {
        log.debug("onConstraintViolations: {}", ex.getMessage());

        final var autohausViolations = ex.getViolations()
            .stream()
            .map(violation -> STR."\{violation.getPropertyPath()}")
            .toList();
        log.trace("onConstraintViolations: {}", autohausViolations);

        final var problemDetail = ProblemDetail.forStatusAndDetail(UNPROCESSABLE_ENTITY, autohausViolations.toString());
        problemDetail.setType(URI.create("/problem/unprocessable"));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));

        return problemDetail;
    }
}
