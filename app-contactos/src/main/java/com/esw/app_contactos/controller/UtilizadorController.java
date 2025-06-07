package com.esw.app_contactos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esw.app_contactos.dto.UtilizadorDTO;
import com.esw.app_contactos.service.UtilizadorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/utilizadores")
@Tag(name = "Utilizadores", description = "Gestão de utilizadores")
public class UtilizadorController {
    private UtilizadorService utilizadorService;

    @Operation(summary = "Criar utilizador", description = "Regista um novo utilizador")
    @ApiResponse(responseCode = "201", description = "Utilizador criado com sucesso")
    @PostMapping
    public ResponseEntity<UtilizadorDTO> createUtilizador(@RequestBody UtilizadorDTO dto) {
        UtilizadorDTO saved = utilizadorService.createUtilizador(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}