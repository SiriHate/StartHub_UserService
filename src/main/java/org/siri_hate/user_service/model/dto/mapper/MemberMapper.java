package org.siri_hate.user_service.model.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.siri_hate.user_service.model.dto.request.member.MemberChangeAvatarRequest;
import org.siri_hate.user_service.model.dto.request.member.MemberFullRequest;
import org.siri_hate.user_service.model.dto.request.member.MemberProfileDataRequest;
import org.siri_hate.user_service.model.dto.request.member.MemberRegistrationRequest;
import org.siri_hate.user_service.model.dto.response.member.MemberFullResponse;
import org.siri_hate.user_service.model.dto.response.member.MemberSummaryResponse;
import org.siri_hate.user_service.model.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mappings({
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "password", target = "password")
    })
    Member toMemberFromRegistration(MemberRegistrationRequest member);

    @Mapping(source = "specialization.name", target = "specialization")
    MemberFullResponse toMemberFullResponse(Member member);

    @Mapping(source = "specialization.name", target = "specialization")
    MemberSummaryResponse toMemberSummaryResponse(Member member);

    List<MemberSummaryResponse> toMemberSummaryResponseList(List<Member> members);

    Member memberUpdateProfileData(
            MemberProfileDataRequest profileDataRequest, @MappingTarget Member member);

    Member memberUpdateFullData(MemberFullRequest memberFullRequest, @MappingTarget Member member);

    Member memberUpdateAvatar(MemberChangeAvatarRequest newAvatar, @MappingTarget Member member);

    default Page<MemberSummaryResponse> toMemberSummaryResponsePage(Page<Member> members) {
        List<MemberSummaryResponse> summaryResponses =
                toMemberSummaryResponseList(members.getContent());
        return new PageImpl<>(summaryResponses, members.getPageable(), members.getTotalElements());
    }
}
