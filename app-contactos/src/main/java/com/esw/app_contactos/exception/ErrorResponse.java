package com.esw.app_contactos.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe para padronizar respostas de erro da API
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    /**
     * Timestamp do erro
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    
    /**
     * Código de status HTTP
     */
    private int status;
    
    /**
     * Nome do erro HTTP
     */
    private String error;
    
    /**
     * Mensagem principal do erro
     */
    private String message;
    
    /**
     * Path da requisição que causou o erro
     */
    private String path;
    
    /**
     * Lista de erros de validação (quando aplicável)
     */
    private List<ValidationError> validationErrors;
    
    /**
     * ID único para rastreamento do erro (opcional)
     */
    private String traceId;
    
    /**
     * Construtor para erros simples
     */
    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
    
    /**
     * Classe interna para representar erros de validação específicos
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValidationError {
        
        /**
         * Nome do campo que contém o erro
         */
        private String field;
        
        /**
         * Valor rejeitado
         */
        private Object rejectedValue;
        
        /**
         * Mensagem de erro específica para este campo
         */
        private String message;
    }
}