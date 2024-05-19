package com.mftplus.onlineshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@Entity(name = "userEntity")
@Table(name = "user_tbl")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_first_name", nullable = false)
    @NotEmpty
    private String firstName;

    @Column(name = "user_last_name", nullable = false)
    @NotEmpty
    private String lastName;

    @Column(name = "user_email", nullable = false, unique = true)
    @NotEmpty
    @Email(message = "{errors.invalid_email}")
    private String email;

    @Column(name = "user_password", nullable = false)
    @NotEmpty
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = {@JoinColumn (name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn (name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    public User() {
    }

    public User(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.password;
        this.roles = user.getRoles();
    }
}