package com.veda.server.dto.request;

public record UpdateSettingRequest(
        String theme,
        String lang
) {
}
