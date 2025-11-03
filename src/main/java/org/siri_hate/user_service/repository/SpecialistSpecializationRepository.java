package org.siri_hate.user_service.repository;

import org.siri_hate.user_service.model.entity.SpecialistSpecialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistSpecializationRepository
        extends JpaRepository<SpecialistSpecialization, Long> {

    SpecialistSpecialization findSpecialistSpecializationsByName(String name);
}
