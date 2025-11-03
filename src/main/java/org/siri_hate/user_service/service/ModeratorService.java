package org.siri_hate.user_service.service;

import org.siri_hate.user_service.model.dto.request.moderator.ModeratorFullRequest;
import org.siri_hate.user_service.model.dto.response.moderator.ModeratorFullResponse;
import org.siri_hate.user_service.model.dto.response.moderator.ModeratorSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ModeratorService {

    void moderatorRegistration(ModeratorFullRequest moderator);

    Page<ModeratorSummaryResponse> getAllModerators(String username, Pageable pageable);

    ModeratorFullResponse getModeratorById(Long id);

    ModeratorFullResponse moderatorUpdate(Long id, ModeratorFullRequest moderator);

    void deleteModeratorById(Long id);

    void deleteModeratorByUsername(String username);
}
