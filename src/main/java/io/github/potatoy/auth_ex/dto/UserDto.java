package io.github.potatoy.auth_ex.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.potatoy.auth_ex.entity.Country;
import io.github.potatoy.auth_ex.entity.Language;
import io.github.potatoy.auth_ex.entity.User;
import io.github.potatoy.auth_ex.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Email
    private String userEmail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;


    @NotNull
    private Country country;

    @NotNull
    private Language language;

    private UserRole role;


    public static UserDto from(User user) {
        if (user == null)
            return null;

        return UserDto.builder()
                .userEmail(user.getUserEmail())
                .nickname(user.getNickname())
                .country(user.getCountry())
                .language(user.getLanguage())
                .role(user.getRole())
                .build();
    }

}
