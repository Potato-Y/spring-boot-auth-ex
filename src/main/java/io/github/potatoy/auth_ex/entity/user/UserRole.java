package io.github.potatoy.auth_ex.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {

    GUEST("ROLE_GUEST", "Guest"),
    USER("ROLE_USER", "User"),
    ADMIN("ROLE_ADMIN", "Admin");

    private final String key;
    private final String title;
}
