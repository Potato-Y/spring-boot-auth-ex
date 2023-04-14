package io.github.potatoy.auth_ex.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Country {

    KR("KR","UNITED STATES"),
    US("US", "KOREA");

    private final String code;
    private final String fullName;
    
    @JsonCreator
    public static Country from(String code) {
        for (Country country : Country.values()) {
            if (country.getCode().equals(code)) {
                return country;
            }
        }
        return null;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

}
