package com.example.demir2.service;

import com.example.demir2.entity.Contact;
import com.example.demir2.entity.ContactId;

import java.io.IOException;
import java.util.List;

public interface ContactService {

    void writeContactToFile(Contact contact)  throws IOException;

    Contact getContactById(ContactId contactId);

    List<Contact> getContactsByName(String name);

    void addContact(Contact contact) throws IOException, ClassNotFoundException;

 }
