package org.siri_hate.user_service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.siri_hate.user_service.model.dto.request.moderator.ModeratorFullRequest;
import org.siri_hate.user_service.model.dto.response.moderator.ModeratorFullResponse;
import org.siri_hate.user_service.model.dto.response.moderator.ModeratorSummaryResponse;
import org.siri_hate.user_service.service.ModeratorService;
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
@RequestMapping("/api/v1/user_service/moderators")
public class ModeratorController {

    private final ModeratorService moderatorService;

    @Autowired
    ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    @PostMapping
    public ResponseEntity<String> moderatorRegistration(
            @RequestBody @Valid ModeratorFullRequest moderator) {
        moderatorService.moderatorRegistration(moderator);
        return new ResponseEntity<>("Successful registration", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ModeratorSummaryResponse>> getAllModerators(
            @RequestParam(required = false) String username,
            @PageableDefault() Pageable pageable) {
        Page<ModeratorSummaryResponse> moderators = moderatorService.getAllModerators(username,
                pageable);
        return new ResponseEntity<>(moderators, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeratorFullResponse> getModeratorById(
            @PathVariable @Positive(message = "ID should be greater than zero") Long id) {
        ModeratorFullResponse moderator = moderatorService.getModeratorById(id);
        return new ResponseEntity<>(moderator, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ModeratorFullResponse> moderatorUpdate(
            @PathVariable @Positive(message = "ID should be greater than zero") Long id,
            @Valid @RequestBody ModeratorFullRequest moderator) {
        ModeratorFullResponse updatedModerator = moderatorService.moderatorUpdate(id, moderator);
        return new ResponseEntity<>(updatedModerator, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteModeratorById(
            @PathVariable @Positive(message = "ID should be greater than zero") Long id) {
        moderatorService.deleteModeratorById(id);
        return new ResponseEntity<>("Successful deletion", HttpStatus.NO_CONTENT);
    }
}
