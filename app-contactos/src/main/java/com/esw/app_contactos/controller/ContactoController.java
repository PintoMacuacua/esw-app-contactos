package com.esw.app_contactos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esw.app_contactos.dto.ContactoDTO;
import com.esw.app_contactos.service.ContactoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/contactos")
@Tag(name = "Contactos", description = "Gestão de contactos associados a utilizadores")
public class ContactoController {
    private ContactoService contactoService;

    @Operation(summary = "Criar contacto", description = "Associa um contacto a um utilizador existente")
    @ApiResponse(responseCode = "201", description = "Contacto criado com sucesso")
    @PostMapping
    public ResponseEntity<ContactoDTO> createContacto(@RequestBody ContactoDTO dto) {
        ContactoDTO saved = contactoService.createContacto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}