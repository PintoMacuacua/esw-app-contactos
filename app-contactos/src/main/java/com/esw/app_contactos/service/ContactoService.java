package com.esw.app_contactos.service;

import java.util.List;
import java.util.Optional;

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
    
    private final ContactoRepository contactoRepository;
    private final UtilizadorRepository utilizadorRepository;

    public ContactoDTO createContacto(ContactoDTO dto) {
        Utilizador utilizador = utilizadorRepository.findById(dto.utilizadorId())
            .orElseThrow(() -> new RuntimeException("Utilizador não encontrado com ID: " + dto.utilizadorId()));

        Contacto contacto = new Contacto();
        contacto.setNome(dto.nome());
        contacto.setTelefone(dto.telefone());
        contacto.setUtilizador(utilizador);
        
        contacto = contactoRepository.save(contacto);

        return new ContactoDTO(contacto.getId(), contacto.getNome(), contacto.getTelefone(), utilizador.getId());
    }

    public Optional<ContactoDTO> getContactoById(Long id) {
        return contactoRepository.findById(id)
            .map(contacto -> new ContactoDTO(
                contacto.getId(), 
                contacto.getNome(), 
                contacto.getTelefone(), 
                contacto.getUtilizador().getId()
            ));
    }

    public List<ContactoDTO> getAllContactos() {
        return contactoRepository.findAll().stream()
            .map(contacto -> new ContactoDTO(
                contacto.getId(), 
                contacto.getNome(), 
                contacto.getTelefone(), 
                contacto.getUtilizador().getId()
            ))
            .toList();
    }

    public List<ContactoDTO> getContactosByUtilizadorId(Long utilizadorId) {
        return contactoRepository.findByUtilizadorId(utilizadorId).stream()
            .map(contacto -> new ContactoDTO(
                contacto.getId(), 
                contacto.getNome(), 
                contacto.getTelefone(), 
                contacto.getUtilizador().getId()
            ))
            .toList();
    }

    public ContactoDTO updateContacto(Long id, ContactoDTO dto) {
        Contacto contacto = contactoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Contacto não encontrado com ID: " + id));

        if (dto.utilizadorId() != null && !dto.utilizadorId().equals(contacto.getUtilizador().getId())) {
            Utilizador novoUtilizador = utilizadorRepository.findById(dto.utilizadorId())
                .orElseThrow(() -> new RuntimeException("Utilizador não encontrado com ID: " + dto.utilizadorId()));
            contacto.setUtilizador(novoUtilizador);
        }

        if (dto.nome() != null) {
            contacto.setNome(dto.nome());
        }
        if (dto.telefone() != null) {
            contacto.setTelefone(dto.telefone());
        }

        contacto = contactoRepository.save(contacto);

        return new ContactoDTO(
            contacto.getId(), 
            contacto.getNome(), 
            contacto.getTelefone(), 
            contacto.getUtilizador().getId()
        );
    }

    public void deleteContacto(Long id) {
        if (!contactoRepository.existsById(id)) {
            throw new RuntimeException("Contacto não encontrado com ID: " + id);
        }
        contactoRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return contactoRepository.existsById(id);
    }
}