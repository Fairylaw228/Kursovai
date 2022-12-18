package com.example.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "tovaradd")
public class Tovaradd {


    public Tovaradd(String opisanie, String cena, String kolichestvo) {
        this.opisanie = opisanie;
        this.cena = cena;
        this.kolichestvo = kolichestvo;
    }

    public Tovaradd() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String opisanie, cena, kolichestvo;
    private String filename;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpisanie() {
        return opisanie;
    }



    public void setOpisanie(String opisanie) {
        this.opisanie = opisanie;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getKolichestvo() {
        return kolichestvo;
    }

    public void setKolichestvo(String kolichestvo) {
        this.kolichestvo = kolichestvo;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


}

