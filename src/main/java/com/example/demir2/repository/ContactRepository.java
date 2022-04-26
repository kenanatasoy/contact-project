package com.example.demir2.repository;

import com.example.demir2.entity.Contact;
import com.example.demir2.entity.ContactId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, ContactId> {

    List<Contact> findAllByName(String name);

}
