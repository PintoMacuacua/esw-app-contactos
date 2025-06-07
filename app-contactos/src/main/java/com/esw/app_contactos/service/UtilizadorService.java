package com.esw.app_contactos.service;

import org.springframework.stereotype.Service;

import com.esw.app_contactos.dto.UtilizadorDTO;
import com.esw.app_contactos.model.Utilizador;
import com.esw.app_contactos.repository.UtilizadorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilizadorService {
    private final UtilizadorRepository utilizadorRepo;

    public UtilizadorDTO createUtilizador(UtilizadorDTO dto) {
        Utilizador utilizador = new Utilizador();
        utilizador.setNome(dto.nome());
        utilizador.setEmail(dto.email());
        utilizador = utilizadorRepo.save(utilizador);
        return new UtilizadorDTO(utilizador.getId(), utilizador.getNome(), utilizador.getEmail());
    }
}