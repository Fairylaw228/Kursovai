package com.example.demo.models;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.persistence.*;
import java.util.List;

@Entity
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1, max = 40, message = "От 1 до 40 символов")
    @Email(message = "Почта не корректна")
    private String email;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1, max = 40, message = "От 1 до 40 символов")
    private String telefone;

    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 1, max = 50, message = "От 1 до 50 символов")
    private String adress;

    public Contacts(Long id, String email, String telefone, String adress) {
        this.id = id;
        this.email = email;
        this.telefone = telefone;
        this.adress = adress;
    }

    public Contacts() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getAdress() { return adress;}

    public void setAdress(String ad) {
        this.adress = adress;
    }

}