package com.nexora.agendaai.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ApiError {
    // Convention Properties (RFC 7807) - https://datatracker.ietf.org/doc/html/rfc7807
    private Integer status;
    private String type;
    private String title;
    private String detail;
    
    // No Convention Properties
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

}
