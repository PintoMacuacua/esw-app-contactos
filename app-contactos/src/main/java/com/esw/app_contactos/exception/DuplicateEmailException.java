package com.esw.app_contactos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando se tenta criar ou atualizar um utilizador 
 * com um email que já está em uso por outro utilizador
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateEmailException extends RuntimeException {
    
    public DuplicateEmailException(String message) {
        super(message);
    }
    
    public DuplicateEmailException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public DuplicateEmailException(String email, boolean isEmail) {
        super("Já existe um utilizador com o email: " + email);
    }
}