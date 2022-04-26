package com.example.demir2.service.business;

import com.example.demir2.entity.Contact;
import com.example.demir2.entity.ContactId;
import com.example.demir2.repository.ContactRepository;
import com.example.demir2.service.ContactService;
import com.example.demir2.util.AppendableObjectOutputStream;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact getContactById(ContactId contactId) {
        return contactRepository.findById(contactId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void writeContactToFile(Contact contact) throws IOException {

        var file = new File("C:/Users/Aydin/IdeaProjects/demir2/src/main/resources","contacts.dat");
        boolean append = file.exists();

        var fos = new FileOutputStream(file, append);
        var oos = new AppendableObjectOutputStream(fos, append);
        oos.writeObject(contact);

    }

    public static List<Contact> importContactsFromFile() throws IOException, ClassNotFoundException{

        ArrayList<Contact> contacts = new ArrayList<Contact>();
        var fis = new FileInputStream(new File("C:/Users/Aydin/IdeaProjects/demir2/src/main/resources","contacts.dat"));
        ObjectInputStream ois = new ObjectInputStream(fis);

        Contact obj = null;

        boolean isExist = true;

        while(isExist){
            if(fis.available() != 0){
                obj = (Contact) ois.readObject();
                contacts.add(obj);
            }
            else{
                isExist =false;
            }
        }
        return contacts;
    }


    @Override
    public List<Contact> getContactsByName(String name) {
        return contactRepository.findAllByName(name);
    }

    @Override
    public void addContact(Contact contact) throws IOException, ClassNotFoundException {

        writeContactToFile(contact);

        List<Contact> contacts = importContactsFromFile();

        var uniqueContacts = contacts.stream()
                .collect( Collectors.groupingBy(c -> c.getName() + "-" + c.getLastName(),
                        Collectors.mapping(Contact::getPhones,
                                Collectors.collectingAndThen(Collectors.toUnmodifiableSet(), Set::toString))) );

        System.out.println(uniqueContacts);

        uniqueContacts.forEach(
                (key, value) -> {
                    String[] keys = key.split("-");

                    StringBuilder sb = new StringBuilder(value);
                    sb.deleteCharAt(value.length() - 1);
                    sb.deleteCharAt(0);

                    String[] newValues = sb.toString().split(", ");
                    for(var newValue : newValues) {
                        System.out.println(newValue);
                    }
                    Set<String> valueSet = new HashSet<>();
                    Collections.addAll(valueSet, newValues);

                    contactRepository.save(new Contact(keys[0], keys[1], valueSet));
                }
        );
    }


}
