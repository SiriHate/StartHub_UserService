package org.siri_hate.user_service.model.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.siri_hate.user_service.model.dto.request.admin.AdminFullRequest;
import org.siri_hate.user_service.model.dto.response.admin.AdminFullResponse;
import org.siri_hate.user_service.model.dto.response.admin.AdminSummaryResponse;
import org.siri_hate.user_service.model.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    @Mappings({
            @Mapping(source = "username", target = "username"),
            @Mapping(target = "role", constant = "ADMIN"),
            @Mapping(target = "enabled", constant = "true"),
            @Mapping(target = "password", ignore = true)
    })
    Admin toAdmin(AdminFullRequest admin);

    AdminFullResponse toAdminFullResponse(Admin admin);

    AdminSummaryResponse toAdminSummaryResponse(Admin admin);

    List<AdminSummaryResponse> toAdminSummaryResponseList(List<Admin> admins);

    Admin adminUpdate(AdminFullRequest request, @MappingTarget Admin admin);

    default Page<AdminSummaryResponse> toAdminSummaryResponsePage(Page<Admin> admins) {
        List<AdminSummaryResponse> summaryResponses = toAdminSummaryResponseList(admins.getContent());
        return new PageImpl<>(summaryResponses, admins.getPageable(), admins.getTotalElements());
    }
}
