package com.example.rasnassesment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;


@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 5313493413859894403L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable=false)
    private String userId;

    @Column(nullable=false, length=50)
    private String firstName;

    @Column(nullable=false, length=50)
    private String lastName;

    @Column(nullable=false, length=120)
    private String email;

    @Column(nullable=false)
    private String encryptedPassword;


    @ManyToMany(cascade= { CascadeType.PERSIST }, fetch = FetchType.EAGER )
    @JoinTable(name="users_roles",
            joinColumns=@JoinColumn(name="users_id",referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="roles_id",referencedColumnName="id"))
    private Collection<Role> roles;



}