package com.esw.app_contactos.service;

import org.springframework.stereotype.Service;

import com.esw.app_contactos.dto.ContactoDTO;
import com.esw.app_contactos.model.Contacto;
import com.esw.app_contactos.model.Utilizador;
import com.esw.app_contactos.repository.ContactoRepository;
import com.esw.app_contactos.repository.UtilizadorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactoService {
    private final ContactoRepository contactoRepo;
    private final UtilizadorRepository utilizadorRepo;

    public ContactoDTO createContacto(ContactoDTO dto) {
        Utilizador utilizador = utilizadorRepo.findById(dto.utilizadorId())
            .orElseThrow();

        Contacto contacto = new Contacto();
        contacto.setNome(dto.nome());
        contacto.setTelefone(dto.telefone());
        contacto.setUtilizador(utilizador);
        contacto = contactoRepo.save(contacto);

        return new ContactoDTO(contacto.getId(), contacto.getNome(), contacto.getTelefone(), utilizador.getId());
    }
}