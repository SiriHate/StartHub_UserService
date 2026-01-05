package org.siri_hate.user_service.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "specialist_specializations")
public class SpecialistSpecialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public SpecialistSpecialization() {}

    public SpecialistSpecialization(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
