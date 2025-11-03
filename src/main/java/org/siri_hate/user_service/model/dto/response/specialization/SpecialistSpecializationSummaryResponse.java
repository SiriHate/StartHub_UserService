package org.siri_hate.user_service.model.dto.response.specialization;

public class SpecialistSpecializationSummaryResponse {

    private Long id;
    private String name;

    public SpecialistSpecializationSummaryResponse() {
    }

    public SpecialistSpecializationSummaryResponse(Long id, String name) {
        this.id = id;
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
