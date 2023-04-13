package io.github.potatoy.auth_ex.service;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.potatoy.auth_ex.dto.UserDto;
import io.github.potatoy.auth_ex.entity.User;
import io.github.potatoy.auth_ex.entity.UserRole;
import io.github.potatoy.auth_ex.exception.DuplicateMemberException;
import io.github.potatoy.auth_ex.exception.NotFoundMemberException;
import io.github.potatoy.auth_ex.repository.UserRepository;
import io.github.potatoy.auth_ex.util.SecurityUtil;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // UserRepository 와 PasswordEncoder 주입
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto signup(UserDto userDto) { // 회원 가입 수행 메소드
        if (userRepository.findOneWithAuthoritiesByUserEmail(userDto.getUserEmail()).orElse(null) != null) {
            // userEmail이 db에 없으면 Authority와 User 정보를 생성해서 UserRepository의 save 메소드를 통해 DB에
            // 정보를 저장
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        // Authority authority = Authority.builder()
        //         .authorityName("ROLE_USER")
        //         .build();
        // 권한 정보 저장

        User user = User.builder()
                .userEmail(userDto.getUserEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                // .authorities(Collections.singleton(authority))
                // .role(UserRole.USER)
                .role(UserRole.USER)
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }

    /**
     * userEmail을 기준으로 정보를 가져온다.
     * 
     * @param userEmail
     * @return
     */
    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String userEmail) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUserEmail(userEmail).orElse(null));
    }

    /**
     * 현재 SecurityContext에 저장된 userEmail의 정보를 가져온다.
     * 
     * @return
     */
    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUserEmail()
                        .flatMap(userRepository::findOneWithAuthoritiesByUserEmail)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found")));
    }
}
