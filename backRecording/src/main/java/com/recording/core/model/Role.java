package com.recording.core.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Role implements GrantedAuthority {
    public Role(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> roles;

    @Override
    public String getAuthority() {
        return getName();
    }
}
