package com.example.demo.repo;
import com.example.demo.models.Contacts;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


    public interface ContactsRepository extends CrudRepository<Contacts, Long> {
        List<Contacts> findById(long id);
    }

