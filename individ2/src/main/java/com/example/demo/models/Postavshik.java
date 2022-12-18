package com.example.demo.models;

import javax.persistence.*;
import java.util.Collection;

public class Postavshik {


    public Postavshik(String namepostavshik, Collection<Tovaradd> tovaradd ) {

        this.namepostavshik = namepostavshik;
        this.tovaradd = tovaradd;
    }

    public Postavshik() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String namepostavshik;



    @OneToMany(mappedBy = "postavshik", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Tovaradd> tovaradd;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamepostavshik() {
        return namepostavshik;
    }

    public void setNamepostavshik(String namepostavshik) {
        this.namepostavshik = namepostavshik;
    }




    public Collection<Tovaradd> getTovaradd() {
        return tovaradd;
    }

    public void setTovaradd(Collection<Tovaradd> tovaradd) {
        this.tovaradd = tovaradd;
    }
}
