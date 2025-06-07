package com.esw.app_contactos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import com.esw.app_contactos.dto.UtilizadorDTO;
import com.esw.app_contactos.service.UtilizadorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/utilizadores")
@Tag(name = "Utilizadores", description = "Gestão de utilizadores")
@RequiredArgsConstructor
public class UtilizadorController {
    
    private final UtilizadorService utilizadorService;

    @Operation(summary = "Criar utilizador", description = "Regista um novo utilizador")
    @ApiResponse(responseCode = "201", description = "Utilizador criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou email já existente")
    @PostMapping
    public ResponseEntity<UtilizadorDTO> createUtilizador(@Valid @RequestBody UtilizadorDTO dto) {
        UtilizadorDTO saved = utilizadorService.createUtilizador(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Obter utilizador por ID", description = "Busca um utilizador específico pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Utilizador encontrado")
    @ApiResponse(responseCode = "404", description = "Utilizador não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<UtilizadorDTO> getUtilizadorById(@PathVariable Long id) {
        Optional<UtilizadorDTO> utilizador = utilizadorService.getUtilizadorById(id);
        return utilizador.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obter utilizador por email", description = "Busca um utilizador específico pelo seu email")
    @ApiResponse(responseCode = "200", description = "Utilizador encontrado")
    @ApiResponse(responseCode = "404", description = "Utilizador não encontrado")
    @GetMapping("/email")
    public ResponseEntity<UtilizadorDTO> getUtilizadorByEmail(@RequestParam String email) {
        Optional<UtilizadorDTO> utilizador = utilizadorService.getUtilizadorByEmail(email);
        return utilizador.map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar todos os utilizadores", description = "Retorna todos os utilizadores existentes")
    @ApiResponse(responseCode = "200", description = "Lista de utilizadores")
    @GetMapping
    public ResponseEntity<List<UtilizadorDTO>> getAllUtilizadores() {
        List<UtilizadorDTO> utilizadores = utilizadorService.getAllUtilizadores();
        return ResponseEntity.ok(utilizadores);
    }

    @Operation(summary = "Atualizar utilizador", description = "Atualiza os dados de um utilizador existente")
    @ApiResponse(responseCode = "200", description = "Utilizador atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Utilizador não encontrado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou email já existente")
    @PutMapping("/{id}")
    public ResponseEntity<UtilizadorDTO> updateUtilizador(@PathVariable Long id, @Valid @RequestBody UtilizadorDTO dto) {
        UtilizadorDTO updated = utilizadorService.updateUtilizador(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Eliminar utilizador", description = "Remove um utilizador do sistema")
    @ApiResponse(responseCode = "204", description = "Utilizador eliminado com sucesso")
    @ApiResponse(responseCode = "404", description = "Utilizador não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilizador(@PathVariable Long id) {
        utilizadorService.deleteUtilizador(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Verificar existência de utilizador por ID", description = "Verifica se um utilizador existe pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Status de existência do utilizador")
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        boolean exists = utilizadorService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Verificar existência de utilizador por email", description = "Verifica se um utilizador existe pelo seu email")
    @ApiResponse(responseCode = "200", description = "Status de existência do utilizador")
    @GetMapping("/email/exists")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        boolean exists = utilizadorService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}