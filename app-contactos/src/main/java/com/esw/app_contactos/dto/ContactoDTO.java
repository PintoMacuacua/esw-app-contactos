package com.esw.app_contactos.dto;

public record ContactoDTO(
    Long id,
    String nome,
    String telefone,
    Long utilizadorId  // ID do utilizador associado
) {}