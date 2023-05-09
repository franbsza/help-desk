package com.digital.helpdesk.models;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails , Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "users_id")
    private UUID id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Must be not empty. Please, provide a valid nickname")
    private String nickname;

    @Column(nullable = false, unique = true)
    @Email(message = "Please, provide a valid email")
    @NotEmpty(message = "Must be not empty")
    private String email;

    @Column
    private String name;

    @Column
    @NotEmpty(message = "Must be not empty")
    @Length(min = 5, message = "You need to provide a password that contains at least 5 characters")
    private String password;

    @Column
    @NotEmpty(message = "Must be not empty")
    private String lastName;

    @Column
    private boolean active = true;

    @ManyToMany(targetEntity = Role.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id") ,
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private List<Role> roles;

    public User(){}

    public User(String email, String name, String lastName, boolean active, String password, List<Role> roles){
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }

    public User(UUID id, String email, String name, String lastName, boolean active, String password, List<Role> roles){
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(x -> new SimpleGrantedAuthority(x.getName()))
                .collect(Collectors.toList());
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
