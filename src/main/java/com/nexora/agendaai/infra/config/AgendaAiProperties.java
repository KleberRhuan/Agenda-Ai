package com.nexora.agendaai.infra.config;

import com.nexora.agendaai.api.exception.ApiErrorType;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "agendaai")
@Getter
@Setter
public class AgendaAiProperties {
    private String url;
    
    @PostConstruct
    public void init() {
        ApiErrorType.setBaseUrl(url);
    }
}