package com.api.loveanddonateapi.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table( name = "users", uniqueConstraints = {
        @UniqueConstraint( columnNames = "email")
    })
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY )
    private Long id;

    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @ManyToMany( fetch = FetchType.LAZY )
    @JoinTable( name = "user_roles",
                joinColumns = @JoinColumn( name = "user_id" ),
                inverseJoinColumns = @JoinColumn( name = "role_id" ) )
    private Set<Role> roles = new HashSet<>();

    private Boolean locked = false;
    private Boolean enabled = false;

    public User( String email,
                 String password ) {
        this.email = email;
        this.password = password;
    }

}
