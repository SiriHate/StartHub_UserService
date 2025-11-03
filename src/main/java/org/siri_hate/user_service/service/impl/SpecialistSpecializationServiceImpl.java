package org.siri_hate.user_service.service.impl;

import jakarta.transaction.Transactional;
import org.siri_hate.user_service.model.dto.mapper.SpecialistSpecializationMapper;
import org.siri_hate.user_service.model.dto.request.specialization.SpecialistSpecializationRequest;
import org.siri_hate.user_service.model.dto.response.specialization.SpecialistSpecializationFullResponse;
import org.siri_hate.user_service.model.dto.response.specialization.SpecialistSpecializationSummaryResponse;
import org.siri_hate.user_service.model.entity.SpecialistSpecialization;
import org.siri_hate.user_service.repository.SpecialistSpecializationRepository;
import org.siri_hate.user_service.service.SpecialistSpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class SpecialistSpecializationServiceImpl implements SpecialistSpecializationService {

    final private SpecialistSpecializationRepository specialistSpecializationRepository;

    final private SpecialistSpecializationMapper specialistSpecializationMapper;


    @Autowired
    public SpecialistSpecializationServiceImpl(
            SpecialistSpecializationRepository specialistSpecializationRepository,
            SpecialistSpecializationMapper specialistSpecializationMapper
    ) {
        this.specialistSpecializationRepository = specialistSpecializationRepository;
        this.specialistSpecializationMapper = specialistSpecializationMapper;
    }


    @Override
    @Transactional
    public void createSpecialistSpecialization(@RequestBody SpecialistSpecializationRequest request) {
        SpecialistSpecialization specialistSpecializationEntity = specialistSpecializationMapper.toSpecialistSpecialization(
                request);
        specialistSpecializationRepository.save(specialistSpecializationEntity);
    }


    @Override
    public List<SpecialistSpecializationSummaryResponse> getAllSpecialistSpecialization() {

        List<SpecialistSpecialization> specialistSpecializations = specialistSpecializationRepository.findAll();
        if (specialistSpecializations.isEmpty()) {
            throw new RuntimeException("Specialist specializations not found");
        }
        return specialistSpecializationMapper.toSpecialistSpecializationSummaryResponseList(
                specialistSpecializations);
    }


    @Override
    public SpecialistSpecializationFullResponse getSpecialistSpecializationById(Long id) {
        SpecialistSpecialization specialistSpecialization = specialistSpecializationRepository.findById(
                id).orElseThrow(
                () -> new RuntimeException("Specialist specialization not found"));
        return specialistSpecializationMapper.toSpecialistSpecializationFullResponse(
                specialistSpecialization);

    }


    @Override
    public SpecialistSpecialization getSpecialistSpecializationEntityById(Long id) {
        return specialistSpecializationRepository.findById(id).orElseThrow(() -> new RuntimeException(
                "Specialist specialization not found"));
    }


    @Override
    @Transactional
    public void updateSpecialistSpecialization(Long id) {
        SpecialistSpecialization specialistSpecialization = specialistSpecializationRepository.findById(
                id).orElseThrow(
                () -> new RuntimeException("Specialist specialization not found"));
        specialistSpecializationRepository.save(specialistSpecialization);

    }


    @Override
    @Transactional
    public void deleteSpecialistSpecialization(Long id) {
        SpecialistSpecialization specialistSpecialization = specialistSpecializationRepository.findById(
                id).orElseThrow(
                () -> new RuntimeException("Specialist specialization not found"));
        specialistSpecializationRepository.delete(specialistSpecialization);

    }

}
