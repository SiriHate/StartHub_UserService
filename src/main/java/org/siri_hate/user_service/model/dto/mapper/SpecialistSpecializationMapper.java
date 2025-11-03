package org.siri_hate.user_service.model.dto.mapper;

import org.mapstruct.Mapper;
import org.siri_hate.user_service.model.dto.request.specialization.SpecialistSpecializationRequest;
import org.siri_hate.user_service.model.dto.response.specialization.SpecialistSpecializationFullResponse;
import org.siri_hate.user_service.model.dto.response.specialization.SpecialistSpecializationSummaryResponse;
import org.siri_hate.user_service.model.entity.SpecialistSpecialization;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpecialistSpecializationMapper {

    SpecialistSpecialization toSpecialistSpecialization(SpecialistSpecializationRequest request);

    SpecialistSpecializationSummaryResponse toSpecialistSpecializationSummaryResponse(
            SpecialistSpecialization specialization);

    List<SpecialistSpecializationSummaryResponse> toSpecialistSpecializationSummaryResponseList(
            List<SpecialistSpecialization> specializations);

    SpecialistSpecializationFullResponse toSpecialistSpecializationFullResponse(
            SpecialistSpecialization specialization);
}
