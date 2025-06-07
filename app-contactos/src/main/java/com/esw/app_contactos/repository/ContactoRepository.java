package com.esw.app_contactos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esw.app_contactos.model.Contacto;

import java.util.List;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
     
    List<Contacto> findByUtilizadorId(Long utilizadorId);
}
