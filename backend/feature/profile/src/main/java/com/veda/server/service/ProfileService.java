package com.veda.server.service;

import com.veda.server.UserAuthorizedEvent;
import com.veda.server.UserRegisteredEvent;
import com.veda.server.exception.ResourceNotFound;
import com.veda.server.model.Profile;
import com.veda.server.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.veda.server.dto.request.UpdateProfileRequest;
import com.veda.server.dto.response.ProfileResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public ProfileResponse getProfileByUserId(Integer userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.warn("Profile not foud for userId: {}", userId);
                    return new ResourceNotFound("Profile not found");
                });

        log.debug("Profile found: {}", profile);
        return mapToResponse(profile);
    }

    @Transactional
    public ProfileResponse updateProfile(Integer userId, UpdateProfileRequest request) {
        log.debug("Updating profile for userId: {}", userId);

        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.warn("Profile not found for userId: {}", userId);
                    return new ResourceNotFound("Profile not found");
                });

        if (request.firstName() != null) profile.setFirstName(request.firstName());
        if (request.lastName() != null) profile.setLastName(request.lastName());
        if (request.bio() != null) profile.setBio(request.bio());

        log.info("Profile updated for userId: {}", userId);

        profileRepository.save(profile);
        return mapToResponse(profile);
    }

    @EventListener
    @Transactional
    public void onUserRegistered(UserRegisteredEvent event) {
        log.info("Event: User registered -> Creating profile for userId: {}", event.userId());

        Profile profile = new Profile();
        profile.setUserId(event.userId());

        String[] names = splitFullName(event.fullName());
        profile.setFirstName(names[0]);
        profile.setLastName(names[1]);

        profileRepository.save(profile);
    }

    @EventListener
    @Transactional
    public void onUserAuthorized(UserAuthorizedEvent event) {
        profileRepository.findByUserId(event.userId()).ifPresent(profile -> {
            profile.setLastLoginAt(event.authorizedAt());
            profileRepository.save(profile);
        });
    }

    private ProfileResponse mapToResponse(Profile p) {
        return new ProfileResponse(
                p.getUserId(),
                p.getFirstName(),
                p.getLastName(),
                p.getBio(),
                p.getLastLoginAt()
        );
    }

    private String[] splitFullName(String fullName) {
        if (fullName == null || fullName.isBlank())
            return new String[]{"", ""};

        String trimmed = fullName.trim();

        int lastSpace = trimmed.lastIndexOf(" ");

        if (lastSpace == -1)
            return new String[]{trimmed, ""};

        return new String[]{trimmed.substring(0, lastSpace), trimmed.substring(lastSpace + 1)};
    }
}