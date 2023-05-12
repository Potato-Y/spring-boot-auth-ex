package io.github.potatoy.auth_ex.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Language {
    
    KO("KO","korean"),
    EN("EN","english");

    private final String code;
    private final String fullName;

}
