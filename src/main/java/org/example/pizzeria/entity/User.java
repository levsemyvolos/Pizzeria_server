package org.example.pizzeria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String email;

    private String passwordHash;
    private String name;
    private String phone;
    private String address;
    private String role; // "CUSTOMER" or "ADMIN"

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Order> orders;

    public User(Long id, String email, String passwordHash, String name, String phone, String address, String role) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(this.role);
    }

    @Transient
    public List<GrantedAuthorityImpl> getAuthorities() {
        return List.of(new GrantedAuthorityImpl(this.role));
    }
}

// A simple Authority implementation
class GrantedAuthorityImpl implements org.springframework.security.core.GrantedAuthority {
    private final String role;
    GrantedAuthorityImpl(String role) { this.role = role; }
    @Override
    public String getAuthority() { return role; }
}
