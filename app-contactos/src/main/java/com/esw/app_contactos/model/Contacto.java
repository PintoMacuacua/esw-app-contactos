package com.esw.app_contactos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "contactos")
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @Pattern(regexp = "^[+]?[0-9\\s()-]+$", message = "Formato de telefone inválido")
    @Size(min = 7, max = 20, message = "Telefone deve ter entre 7 e 20 caracteres")
    @Column(length = 20)
    private String telefone;

    @Email(message = "Formato de email inválido")
    @Size(max = 150, message = "Email deve ter no máximo 150 caracteres")
    @Column(length = 150)
    private String email;

    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    @Column(length = 500)
    private String observacoes;

    @NotNull(message = "Utilizador é obrigatório")
    @ManyToOne
    @JoinColumn(name = "utilizador_id", nullable = false)
    private Utilizador utilizador;

    // Constructors
    public Contacto() {}

    public Contacto(String nome, String telefone, Utilizador utilizador) {
        this.nome = nome;
        this.telefone = telefone;
        this.utilizador = utilizador;
    }

    public Contacto(String nome, String telefone, String email, String observacoes, Utilizador utilizador) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.observacoes = observacoes;
        this.utilizador = utilizador;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Utilizador getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    // equals, hashCode and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contacto contacto = (Contacto) o;
        return id != null ? id.equals(contacto.id) : contacto.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Contacto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", observacoes='" + observacoes + '\'' +
                ", utilizador=" + (utilizador != null ? utilizador.getId() : null) +
                '}';
    }
}