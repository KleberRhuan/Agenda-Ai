package com.nexora.agendaai.api.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.nexora.agendaai.domain.exception.BusinessException;
import com.nexora.agendaai.domain.exception.EntityNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /* *********************************************************************
     * Global Exception Handlers
     * *********************************************************************/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        ApiError apiError = toApiErrorBuilder(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ApiErrorType.SYSTEM_ERROR,
                "Ocorreu um erro interno no servidor, tente novamente e se o problema persistir, entre em contato com o administrador.")
                .build();
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        ApiError apiError = toApiErrorBuilder(
                HttpStatus.BAD_REQUEST,
                ApiErrorType.BUSINESS_ERROR,
                ex.getMessage())
                .build();
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ApiError apiError = toApiErrorBuilder(
                HttpStatus.NOT_FOUND,
                ApiErrorType.RESOURCE_NOT_FOUND,
                ex.getMessage())
                .build();
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido";
        String detail = String.format(
                "O parâmetro '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo '%s'.",
                ex.getName(), ex.getValue(), requiredType);
        ApiError apiError = toApiErrorBuilder(
                HttpStatus.BAD_REQUEST,
                ApiErrorType.MESSAGE_NOT_READABLE,
                detail)
                .build();
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /* *********************************************************************
     * Overridden Methods from ResponseEntityExceptionHandler
     * *********************************************************************/

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatusCode status,
                                                                   WebRequest request) {
        String httpMethod = ex.getHttpMethod();
        String requestURL = ex.getRequestURL();
        String detail = String.format("O endpoint '%s %s' não foi encontrado. "
                        + "Por favor, verifique se o URL está correto e tente novamente.",
                httpMethod, requestURL);
        
        ApiError apiError = toApiErrorBuilder(HttpStatus.NOT_FOUND, ApiErrorType.RESOURCE_NOT_FOUND, detail)
                .build();
        
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException invalidFormatException) {
            return handleInvalidFormatException(invalidFormatException, headers, status, request);
        }
        String detail = "O corpo da requisição é inválido, por favor verifique a sintaxe.";
        ApiError apiError = toApiErrorBuilder(
                HttpStatus.BAD_REQUEST,
                ApiErrorType.MESSAGE_NOT_READABLE,
                detail)
                .build();
        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex,
                                                                    HttpHeaders headers,
                                                                    HttpStatusCode status,
                                                                    WebRequest request) {
        String requestedUri = request.getDescription(false).replace("uri=", "");
        String detail = String.format("O recurso '%s' não foi encontrado. Por favor, verifique o endereço e tente novamente.", requestedUri);
        ApiError apiError = toApiErrorBuilder(
                HttpStatus.NOT_FOUND,
                ApiErrorType.RESOURCE_NOT_FOUND,
                detail)
                .build();
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             @Nullable Object body,
                                                             HttpHeaders headers,
                                                             HttpStatusCode statusCode,
                                                             WebRequest request) {
        if (body == null) {
            body = toApiErrorBuilder(statusCode, ApiErrorType.BUSINESS_ERROR, ex.getMessage()).build();
        } else if (body instanceof String string) {
            body = toApiErrorBuilder(statusCode, ApiErrorType.BUSINESS_ERROR, string).build();
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    /* *********************************************************************
     * Private Helper Methods
     * *********************************************************************/

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
                                                                HttpHeaders headers,
                                                                HttpStatusCode status,
                                                                WebRequest request) {
        String path = ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
        String detail = String.format(
                "A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo '%s'.",
                path, ex.getValue(), ex.getTargetType());
        ApiError apiError = toApiErrorBuilder(
                HttpStatus.BAD_REQUEST,
                ApiErrorType.MESSAGE_NOT_READABLE,
                detail)
                .build();
        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    private ApiError.ApiErrorBuilder toApiErrorBuilder(HttpStatusCode status,
                                                       ApiErrorType apiErrorType,
                                                       String detail) {
        return ApiError.builder()
                .status(status.value())
                .type(apiErrorType.getUri())
                .title(apiErrorType.getTitle())
                .detail(detail);
    }
}