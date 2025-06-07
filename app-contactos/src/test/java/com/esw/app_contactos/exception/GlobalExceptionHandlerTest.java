package com.esw.app_contactos.exception;

import com.esw.app_contactos.controller.ContactoController;
import com.esw.app_contactos.controller.UtilizadorController;
import com.esw.app_contactos.dto.ContactoDTO;
import com.esw.app_contactos.dto.UtilizadorDTO;
import com.esw.app_contactos.service.ContactoService;
import com.esw.app_contactos.service.UtilizadorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({ContactoController.class, UtilizadorController.class, GlobalExceptionHandler.class})
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactoService contactoService;

    @MockBean
    private UtilizadorService utilizadorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testContactoNotFoundException() throws Exception {
        when(contactoService.getContactoById(anyLong()))
                .thenThrow(new ContactoNotFoundException(1L));

        mockMvc.perform(get("/api/contactos/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Contacto não encontrado com ID: 1"))
                .andExpect(jsonPath("$.path").value("/api/contactos/1"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.traceId").exists());
    }

    @Test
    void testUtilizadorNotFoundException() throws Exception {
        when(utilizadorService.getUtilizadorById(anyLong()))
                .thenThrow(new UtilizadorNotFoundException(1L));

        mockMvc.perform(get("/api/utilizadores/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Utilizador não encontrado com ID: 1"))
                .andExpect(jsonPath("$.path").value("/api/utilizadores/1"));
    }

    @Test
    void testDuplicateEmailException() throws Exception {
        UtilizadorDTO dto = new UtilizadorDTO(null, "João Silva", "joao@example.com");
        
        when(utilizadorService.createUtilizador(any(UtilizadorDTO.class)))
                .thenThrow(new DuplicateEmailException("joao@example.com", true));

        mockMvc.perform(post("/api/utilizadores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message").value("Já existe um utilizador com o email: joao@example.com"));
    }

    @Test
    void testValidationException() throws Exception {
        // DTO com dados inválidos (nome vazio)
        UtilizadorDTO invalidDto = new UtilizadorDTO(null, "", "invalid-email");

        mockMvc.perform(post("/api/utilizadores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.message").value("Erro de validação nos dados fornecidos"))
                .andExpect(jsonPath("$.validationErrors").exists())
                .andExpect(jsonPath("$.validationErrors").isArray());
    }

    @Test
    void testContactoValidationException() throws Exception {
        // DTO com dados inválidos (nome muito curto e telefone inválido)
        ContactoDTO invalidDto = new ContactoDTO(null, "A", "abc123", 1L);

        mockMvc.perform(post("/api/contactos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.validationErrors").exists());
    }

    @Test
    void testMethodArgumentTypeMismatchException() throws Exception {
        // Enviar string onde esperamos Long
        mockMvc.perform(get("/api/contactos/invalid-id"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void testHttpMessageNotReadableException() throws Exception {
        // JSON malformado
        String malformedJson = "{ invalid json }";

        mockMvc.perform(post("/api/utilizadores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(malformedJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Formato JSON inválido ou malformado"));
    }

    @Test
    void testGenericException() throws Exception {
        when(contactoService.getAllContactos())
                .thenThrow(new RuntimeException("Erro interno inesperado"));

        mockMvc.perform(get("/api/contactos"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("Ocorreu um erro interno no servidor. Por favor, tente novamente mais tarde."))
                .andExpect(jsonPath("$.traceId").exists());
    }

    @Test
    void testValidContactoCreation() throws Exception {
        ContactoDTO validDto = new ContactoDTO(1L, "João Silva", "123456789", 1L);
        
        when(contactoService.createContacto(any(ContactoDTO.class)))
                .thenReturn(validDto);

        mockMvc.perform(post("/api/contactos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.telefone").value("123456789"))
                .andExpect(jsonPath("$.utilizadorId").value(1));
    }

    @Test
    void testValidUtilizadorCreation() throws Exception {
        UtilizadorDTO validDto = new UtilizadorDTO(1L, "João Silva", "joao@example.com");
        
        when(utilizadorService.createUtilizador(any(UtilizadorDTO.class)))
                .thenReturn(validDto);

        mockMvc.perform(post("/api/utilizadores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@example.com"));
    }
}