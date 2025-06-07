package com.esw.app_contactos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esw.app_contactos.dto.UtilizadorDTO;
import com.esw.app_contactos.model.Utilizador;
import com.esw.app_contactos.repository.UtilizadorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilizadorService {
    
    private final UtilizadorRepository utilizadorRepository;

    public UtilizadorDTO createUtilizador(UtilizadorDTO dto) {
        // Verificar se já existe um utilizador com o mesmo email
        if (utilizadorRepository.findByEmail(dto.email()) != null) {
            throw new RuntimeException("Já existe um utilizador com o email: " + dto.email());
        }
        
        Utilizador utilizador = new Utilizador();
        utilizador.setNome(dto.nome());
        utilizador.setEmail(dto.email());
        
        utilizador = utilizadorRepository.save(utilizador);
        
        return new UtilizadorDTO(utilizador.getId(), utilizador.getNome(), utilizador.getEmail());
    }

    public Optional<UtilizadorDTO> getUtilizadorById(Long id) {
        return utilizadorRepository.findById(id)
            .map(utilizador -> new UtilizadorDTO(
                utilizador.getId(), 
                utilizador.getNome(), 
                utilizador.getEmail()
            ));
    }

    public Optional<UtilizadorDTO> getUtilizadorByEmail(String email) {
        Utilizador utilizador = utilizadorRepository.findByEmail(email);
        if (utilizador != null) {
            return Optional.of(new UtilizadorDTO(
                utilizador.getId(), 
                utilizador.getNome(), 
                utilizador.getEmail()
            ));
        }
        return Optional.empty();
    }

    public List<UtilizadorDTO> getAllUtilizadores() {
        return utilizadorRepository.findAll().stream()
            .map(utilizador -> new UtilizadorDTO(
                utilizador.getId(), 
                utilizador.getNome(), 
                utilizador.getEmail()
            ))
            .toList();
    }

    public UtilizadorDTO updateUtilizador(Long id, UtilizadorDTO dto) {
        Utilizador utilizador = utilizadorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Utilizador não encontrado com ID: " + id));

        // Verificar se o novo email já está em uso por outro utilizador
        if (dto.email() != null && !dto.email().equals(utilizador.getEmail())) {
            Utilizador existingUtilizador = utilizadorRepository.findByEmail(dto.email());
            if (existingUtilizador != null && !existingUtilizador.getId().equals(id)) {
                throw new RuntimeException("Já existe um utilizador com o email: " + dto.email());
            }
        }

        if (dto.nome() != null) {
            utilizador.setNome(dto.nome());
        }
        if (dto.email() != null) {
            utilizador.setEmail(dto.email());
        }

        utilizador = utilizadorRepository.save(utilizador);

        return new UtilizadorDTO(
            utilizador.getId(), 
            utilizador.getNome(), 
            utilizador.getEmail()
        );
    }

    public void deleteUtilizador(Long id) {
        if (!utilizadorRepository.existsById(id)) {
            throw new RuntimeException("Utilizador não encontrado com ID: " + id);
        }
        utilizadorRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return utilizadorRepository.existsById(id);
    }

    public boolean existsByEmail(String email) {
        return utilizadorRepository.findByEmail(email) != null;
    }
}