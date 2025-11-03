package org.siri_hate.user_service.service;

import org.siri_hate.user_service.model.dto.request.admin.AdminFullRequest;
import org.siri_hate.user_service.model.dto.response.admin.AdminFullResponse;
import org.siri_hate.user_service.model.dto.response.admin.AdminSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {

    AdminFullResponse createAdmin(AdminFullRequest admin);

    Page<AdminSummaryResponse> getAllAdmins(Pageable pageable);

    AdminFullResponse getAdminById(Long id);

    AdminFullResponse updateAdminById(Long id, AdminFullRequest admin);

    void deleteAdminById(Long id);

    void deleteAdminByUsername(String username);
}
