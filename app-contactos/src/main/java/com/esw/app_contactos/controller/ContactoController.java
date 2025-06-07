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
import org.springframework.web.bind.annotation.RestController;

import com.esw.app_contactos.dto.ContactoDTO;
import com.esw.app_contactos.service.ContactoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contactos")
@Tag(name = "Contactos", description = "Gestão de contactos associados a utilizadores")
@RequiredArgsConstructor
public class ContactoController {
    
    private final ContactoService contactoService;

    @Operation(summary = "Criar contacto", description = "Associa um contacto a um utilizador existente")
    @ApiResponse(responseCode = "201", description = "Contacto criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Utilizador não encontrado")
    @PostMapping
    public ResponseEntity<ContactoDTO> createContacto(@RequestBody ContactoDTO dto) {
        ContactoDTO saved = contactoService.createContacto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Obter contacto por ID", description = "Busca um contacto específico pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Contacto encontrado")
    @ApiResponse(responseCode = "404", description = "Contacto não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ContactoDTO> getContactoById(@PathVariable Long id) {
        Optional<ContactoDTO> contacto = contactoService.getContactoById(id);
        return contacto.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar todos os contactos", description = "Retorna todos os contactos existentes")
    @ApiResponse(responseCode = "200", description = "Lista de contactos")
    @GetMapping
    public ResponseEntity<List<ContactoDTO>> getAllContactos() {
        List<ContactoDTO> contactos = contactoService.getAllContactos();
        return ResponseEntity.ok(contactos);
    }

    @Operation(summary = "Listar contactos por utilizador", description = "Retorna todos os contactos de um utilizador específico")
    @ApiResponse(responseCode = "200", description = "Lista de contactos do utilizador")
    @GetMapping("/utilizador/{utilizadorId}")
    public ResponseEntity<List<ContactoDTO>> getContactosByUtilizadorId(@PathVariable Long utilizadorId) {
        List<ContactoDTO> contactos = contactoService.getContactosByUtilizadorId(utilizadorId);
        return ResponseEntity.ok(contactos);
    }

    @Operation(summary = "Atualizar contacto", description = "Atualiza os dados de um contacto existente")
    @ApiResponse(responseCode = "200", description = "Contacto atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Contacto não encontrado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PutMapping("/{id}")
    public ResponseEntity<ContactoDTO> updateContacto(@PathVariable Long id, @RequestBody ContactoDTO dto) {
        ContactoDTO updated = contactoService.updateContacto(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Eliminar contacto", description = "Remove um contacto do sistema")
    @ApiResponse(responseCode = "204", description = "Contacto eliminado com sucesso")
    @ApiResponse(responseCode = "404", description = "Contacto não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContacto(@PathVariable Long id) {
        contactoService.deleteContacto(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Verificar existência de contacto", description = "Verifica se um contacto existe pelo seu ID")
    @ApiResponse(responseCode = "200", description = "Status de existência do contacto")
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        boolean exists = contactoService.existsById(id);
        return ResponseEntity.ok(exists);
    }
}