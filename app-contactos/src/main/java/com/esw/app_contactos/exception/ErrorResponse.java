package com.esw.app_contactos.exception;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe para padronizar respostas de erro da API
 */
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
     * Construtores
     */
    public ErrorResponse() {}

    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path, List<ValidationError> validationErrors, String traceId) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.validationErrors = validationErrors;
        this.traceId = traceId;
    }

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

    // Getters and Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    // Builder pattern implementation
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        private List<ValidationError> validationErrors;
        private String traceId;

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder validationErrors(List<ValidationError> validationErrors) {
            this.validationErrors = validationErrors;
            return this;
        }

        public Builder traceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(timestamp, status, error, message, path, validationErrors, traceId);
        }
    }
    
    /**
     * Classe interna para representar erros de validação específicos
     */
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

        // Constructors
        public ValidationError() {}

        public ValidationError(String field, Object rejectedValue, String message) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.message = message;
        }

        // Getters and Setters
        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Object getRejectedValue() {
            return rejectedValue;
        }

        public void setRejectedValue(Object rejectedValue) {
            this.rejectedValue = rejectedValue;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        // Builder pattern implementation
        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private String field;
            private Object rejectedValue;
            private String message;

            public Builder field(String field) {
                this.field = field;
                return this;
            }

            public Builder rejectedValue(Object rejectedValue) {
                this.rejectedValue = rejectedValue;
                return this;
            }

            public Builder message(String message) {
                this.message = message;
                return this;
            }

            public ValidationError build() {
                return new ValidationError(field, rejectedValue, message);
            }
        }
    }
}