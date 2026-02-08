package com.veda.server.repository;

import com.veda.server.model.UserSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSettingRepository extends JpaRepository<UserSetting, Integer> {
    Optional<UserSetting> findByUserId(Integer userId);
}
