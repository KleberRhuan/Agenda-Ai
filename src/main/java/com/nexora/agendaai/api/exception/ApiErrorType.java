package com.nexora.agendaai.api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ApiErrorType {
    BUSINESS_ERROR("/negocio", "Erro de negócio"),
    MESSAGE_NOT_READABLE("/messagem-incompreensivel", "Mensagem incompreensível"),
    RESOURCE_NOT_FOUND("/recurso-nao-encontrado", "Recurso não encontrado"),
    INVALID_PARAMETER("/parametro-invalido", "Parâmetro inválido"),
    SYSTEM_ERROR("/erro-interno", "Erro interno."); 
    
    private final String title;
    private final String uri;
    @Setter
    private static String baseUrl;
    
    ApiErrorType(String path, String title) {
        this.title = title;
        this.uri = path;
    }

    public String getUri() {
        return baseUrl + uri;
    }
}
