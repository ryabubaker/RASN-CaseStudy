package com.example.rasnassesment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;



@Setter
@Getter
@Entity
@Table(name="roles")
public class Role implements Serializable {

    @Serial
    private static final long serialVersionUID = 5605260522147928803L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(nullable=false, length=20)
    private String name;

    @ManyToMany(mappedBy="roles")
    private Collection<User> users;

    @ManyToMany(cascade= { CascadeType.PERSIST }, fetch = FetchType.EAGER )
    @JoinTable(name="roles_authorities",
            joinColumns=@JoinColumn(name="roles_id",referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="authorities_id",referencedColumnName="id"))
    private Collection<Authority> authorities;


    public Role(String name) {
        this.name=name;
    }

    public Role() {

    }
}