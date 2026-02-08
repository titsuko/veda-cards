package com.veda.server.service;

import com.veda.server.UserRegisteredEvent;
import com.veda.server.dto.request.UpdateSettingRequest;
import com.veda.server.dto.response.UserSettingResponse;
import com.veda.server.exception.ResourceNotFound;
import com.veda.server.model.UserSetting;
import com.veda.server.repository.UserSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSettingsService {

    private final UserSettingRepository settingRepository;

    @Transactional
    public UserSettingResponse getSetting(Integer userId) {
        UserSetting setting = settingRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.warn("User not foud with id {}", userId);
                    return new ResourceNotFound("setting not found for userId: " + userId);
                });

        log.debug("Setting found: {}", setting);
        return mapToResponse(setting);
    }

    @Transactional
    public UserSettingResponse updateSetting(Integer userId, UpdateSettingRequest request) {
        log.debug("Updating user setting for userId: {}", userId);

        UserSetting setting = settingRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.warn("User not found with id {}", userId);
                    return new ResourceNotFound("setting not found for userId: " + userId);
                });

        if (request.theme() != null) setting.setTheme(request.theme());
        if (request.lang() != null) setting.setLang(request.lang());

        log.debug("Setting updated for userId: {}", userId);

        settingRepository.save(setting);
        return mapToResponse(setting);
    }

    @EventListener
    @Transactional
    public void onUserRegistered(UserRegisteredEvent event) {
        log.info("Event: User registered -> Creating settings for userId: {}", event.userId());

        UserSetting settings = new UserSetting();
        settings.setUserId(event.userId());
        settingRepository.save(settings);
    }

    private UserSettingResponse mapToResponse(UserSetting setting) {
        return new UserSettingResponse(
                setting.getTheme(),
                setting.getLang()
        );
    }
}
