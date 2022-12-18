package com.example.demo.repo;


import com.example.demo.models.Tovaradd;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TovarRepository extends CrudRepository<Tovaradd, Long> {

    List<Tovaradd> findByIdContains(String id);

}