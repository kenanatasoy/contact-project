package com.example.demir2.entity;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name= "contacts")
@IdClass(ContactId.class)
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Contact implements Serializable {

    @Id
    private String name;
    @Id
    private String lastName;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Set<String> phones;


}
