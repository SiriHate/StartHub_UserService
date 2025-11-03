package org.siri_hate.user_service.controller;

import jakarta.validation.constraints.Positive;
import org.siri_hate.user_service.model.dto.request.admin.AdminFullRequest;
import org.siri_hate.user_service.model.dto.response.admin.AdminFullResponse;
import org.siri_hate.user_service.model.dto.response.admin.AdminSummaryResponse;
import org.siri_hate.user_service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/user_service/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<AdminFullResponse> createAdmin(
            @Validated @RequestBody AdminFullRequest admin) {
        AdminFullResponse createdAdmin = adminService.createAdmin(admin);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<AdminSummaryResponse>> getAllAdmins(
            @PageableDefault(size = 1) Pageable pageable) {
        Page<AdminSummaryResponse> admins = adminService.getAllAdmins(pageable);
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminFullResponse> getAdminById(@Positive @PathVariable Long id) {
        AdminFullResponse admin = adminService.getAdminById(id);
        return new ResponseEntity<>(admin, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    ResponseEntity<AdminFullResponse> updateAdminById(@PathVariable Long id,
                                                      @Validated @RequestBody AdminFullRequest admin) {
        AdminFullResponse updatedAdmin = adminService.updateAdminById(id, admin);
        return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteAdminById(@Positive @PathVariable Long id) {
        adminService.deleteAdminById(id);
        return new ResponseEntity<>("Administrator has been successfully deleted",
                HttpStatus.NO_CONTENT);
    }
}
