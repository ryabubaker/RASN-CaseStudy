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
@Table(name="authorities")
public class Authority implements Serializable {

    @Serial
    private static final long serialVersionUID = -5828101164006114538L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable=false, length=20)
    private String name;

    @ManyToMany(mappedBy="authorities")
    private Collection<Role> roles;

    public Authority() {}

    public Authority(String name) {
        this.name = name;
    }

}