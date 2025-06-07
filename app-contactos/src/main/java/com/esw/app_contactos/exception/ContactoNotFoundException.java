package com.esw.app_contactos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um contacto não é encontrado
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ContactoNotFoundException extends RuntimeException {
    
    public ContactoNotFoundException(String message) {
        super(message);
    }
    
    public ContactoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ContactoNotFoundException(Long id) {
        super("Contacto não encontrado com ID: " + id);
    }
}