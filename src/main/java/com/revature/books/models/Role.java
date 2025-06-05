package com.revature.books.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity // This entity is mapped to the roles table in the database
@Table(name = "roles")
public class Role {

    @Id
    @Column(name="role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }

    // @Data above takes care of setters, getters, hashCode() & Equals()
}
