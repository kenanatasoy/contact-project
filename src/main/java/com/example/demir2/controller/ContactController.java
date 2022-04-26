package com.example.demir2.controller;

import com.example.demir2.entity.Contact;
import com.example.demir2.service.ContactService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;
import java.util.List;

@RequestScope
@RequestMapping("/api/")
@RestController
@CrossOrigin
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("contacts")
    public List<Contact> getContactsByName(@RequestParam String name){
        return contactService.getContactsByName(name);
    }


    @PostMapping("contact")
    public void addContact(@RequestBody Contact contact) throws IOException, ClassNotFoundException {
        contactService.addContact(contact);
    }


}
