package com.nexora.agendaai.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Represents a standardized error response body, following the RFC 7807 Problem Details format.
 * This class helps to provide consistent and detailed error information in API responses.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from the JSON response
@Getter
@Builder
public class ApiError {

    /**
     * HTTP status code of the error (e.g., 400, 404, 500).
     * This follows the standard HTTP status codes.
     */
    private Integer status;

    /**
     * URI reference that identifies the type of problem.
     * It provides a machine-readable identifier for the error type.
     */
    private String type;

    /**
     * Short, human-readable summary of the problem type.
     * Example: "Bad Request", "Resource Not Found", etc.
     */
    private String title;

    /**
     * Detailed, human-readable explanation of the specific error that occurred.
     * This is intended to help developers or users understand the error cause.
     */
    private String detail;

    /**
     * Timestamp indicating when the error occurred.
     * Defaults to the current time when the object is created.
     */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}