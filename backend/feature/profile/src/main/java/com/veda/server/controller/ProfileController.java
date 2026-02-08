package com.veda.server.controller;

import com.veda.server.dto.request.UpdateProfileRequest;
import com.veda.server.dto.response.ProfileResponse;
import com.veda.server.exception.ResourceNotFound;
import com.veda.server.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile(Authentication authentication) {
        if (authentication == null)
            throw new ResourceNotFound("Access token not found");

        Integer userId = Integer.valueOf(authentication.getName());
        return ResponseEntity.ok(profileService.getProfileByUserId(userId));
    }

    @PutMapping("/me")
    public ResponseEntity<ProfileResponse> updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateProfileRequest request
    ) {
        Integer userId = Integer.valueOf(authentication.getName());
        return ResponseEntity.ok(profileService.updateProfile(userId, request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getUserProfile(@PathVariable Integer userId) {
        return ResponseEntity.ok(profileService.getProfileByUserId(userId));
    }
}
