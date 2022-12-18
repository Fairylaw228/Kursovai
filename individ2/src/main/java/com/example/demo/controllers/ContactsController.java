package com.example.demo.controllers;


import com.example.demo.models.Contacts;
import com.example.demo.repo.ContactsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

    @Controller
    public class ContactsController {
        private final ContactsRepository contactsRepository;

        public ContactsController(ContactsRepository contactsRepository) {
            this.contactsRepository = contactsRepository;
        }

        @GetMapping("/contacts")
        public String contactsMain(Model model) {
            Iterable<Contacts> contacts = contactsRepository.findAll();
            model.addAttribute("contacts", contacts);
            return "contacts";
        }





    }

