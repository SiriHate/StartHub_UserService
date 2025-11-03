package org.siri_hate.user_service.service;

import org.siri_hate.user_service.model.dto.request.specialization.SpecialistSpecializationRequest;
import org.siri_hate.user_service.model.dto.response.specialization.SpecialistSpecializationFullResponse;
import org.siri_hate.user_service.model.dto.response.specialization.SpecialistSpecializationSummaryResponse;
import org.siri_hate.user_service.model.entity.SpecialistSpecialization;

import java.util.List;

public interface SpecialistSpecializationService {

    void createSpecialistSpecialization(SpecialistSpecializationRequest request);

    List<SpecialistSpecializationSummaryResponse> getAllSpecialistSpecialization();

    SpecialistSpecializationFullResponse getSpecialistSpecializationById(Long id);

    SpecialistSpecialization getSpecialistSpecializationEntityById(Long id);

    void updateSpecialistSpecialization(Long id);

    void deleteSpecialistSpecialization(Long id);
}
