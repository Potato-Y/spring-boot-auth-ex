package io.github.potatoy.auth_ex.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.github.potatoy.auth_ex.entity.User;
import io.github.potatoy.auth_ex.entity.UserRole;
import io.github.potatoy.auth_ex.repository.UserRepository;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // userRepository를 주입받는다.
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 로그인시에 DB에서 유저정보와 권한정보를 가져온다.
     * 
     * @param userEmail
     * @return UserDetails / 가져온 정보를 기반으로 userdetails.User 객체를 생성해서 반환한다.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String userEmail) {
        return userRepository.findOneWithAuthoritiesByUserEmail(userEmail)
                .map(user -> createUser(userEmail, user))
                .orElseThrow(() -> new UsernameNotFoundException(userEmail + "를(을) DB에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String userEmail, User user) {
        if (!user.isActivated()) { // 가져온 유저가 활성화 상태라면 실행
            throw new RuntimeException(userEmail + "는(은) 활성화되어 있지 않습니다.");
        }

        // List<GrantedAuthority> grantedAuthorities = user.getRole().stream() // 권한 정보
        //         .map(authrity -> new SimpleGrantedAuthority(authrity.getKey()))
        //         .collect(Collectors.toList());

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole().getKey()));

        return new org.springframework.security.core.userdetails.User(user.getUserEmail(), user.getPassword(),
                grantedAuthorities); // UserEmail과 Password를 가지고 User 객체를 반환한다.
    }

}
