package org.siri_hate.user_service.controller;

import org.siri_hate.user_service.model.dto.request.tokens.RegistrationTokenRequest;
import org.siri_hate.user_service.service.ConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/user_service/confirmations")
public class ConfirmationController {

    private final ConfirmationService confirmationService;

    @Autowired
    public ConfirmationController(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    @GetMapping("/check_confirmation_token")
    public ResponseEntity<String> checkConfirmationToken(@RequestParam String token) {
        confirmationService.findConfirmationTokenByTokenValue(token);
        return new ResponseEntity<>("Confirmation token found", HttpStatus.OK);
    }

    @PostMapping("/confirm-registration")
    public ResponseEntity<String> confirmRegistration(
            @RequestBody RegistrationTokenRequest registrationTokenRequest) {
        confirmationService.checkConfirmationToken(registrationTokenRequest.getToken());
        return new ResponseEntity<>("Registration has been successfully confirmed", HttpStatus.OK);
    }
}
