package com.veda.server.controller;

import com.veda.server.dto.request.UpdateSettingRequest;
import com.veda.server.dto.response.UserSettingResponse;
import com.veda.server.exception.ResourceNotFound;
import com.veda.server.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts/me")
public class UserSettingController {

    private final UserSettingsService settingsService;

    @GetMapping("/settings")
    public ResponseEntity<UserSettingResponse> getUserSettingService(Authentication authentication) {
        if (authentication == null)
            throw new ResourceNotFound("Access token not found");

        Integer userId = Integer.valueOf(authentication.getName());
        return ResponseEntity.ok(settingsService.getSetting(userId));
    }

    @PutMapping("/settings")
    public ResponseEntity<UserSettingResponse> updateUserSetting(
            Authentication authentication,
            @RequestBody UpdateSettingRequest request
    ) {
       if (authentication == null)
           throw new ResourceNotFound("Access token not found");

       Integer userId = Integer.valueOf(authentication.getName());
       return ResponseEntity.ok(settingsService.updateSetting(userId, request));
    }
}
