package org.siri_hate.user_service.model.dto.response.specialization;

public class SpecialistSpecializationFullResponse {

    private Long id;
    private String name;

    public SpecialistSpecializationFullResponse() {
    }

    public SpecialistSpecializationFullResponse(Long id, String name) {
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
