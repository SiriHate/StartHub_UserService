package org.siri_hate.user_service.repository;

import org.siri_hate.user_service.model.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findAdminByUsername(String username);
}
