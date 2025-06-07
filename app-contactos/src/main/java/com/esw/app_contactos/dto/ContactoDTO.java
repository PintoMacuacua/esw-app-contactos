package com.esw.app_contactos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ContactoDTO(
    Long id,
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    String nome,
    
    @Pattern(regexp = "^[+]?[0-9\\s()-]+$", message = "Formato de telefone inválido")
    @Size(min = 7, max = 20, message = "Telefone deve ter entre 7 e 20 caracteres")
    String telefone,
    
    @NotNull(message = "ID do utilizador é obrigatório")
    Long utilizadorId  // ID do utilizador associado
) {}