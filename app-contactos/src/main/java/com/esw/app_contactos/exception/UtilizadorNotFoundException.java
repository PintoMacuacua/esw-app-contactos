package com.esw.app_contactos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um utilizador não é encontrado
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UtilizadorNotFoundException extends RuntimeException {
    
    public UtilizadorNotFoundException(String message) {
        super(message);
    }
    
    public UtilizadorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public UtilizadorNotFoundException(Long id) {
        super("Utilizador não encontrado com ID: " + id);
    }
    
    public UtilizadorNotFoundException(String email, boolean isEmail) {
        super("Utilizador não encontrado com email: " + email);
    }
}