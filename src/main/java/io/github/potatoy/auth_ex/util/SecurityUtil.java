package io.github.potatoy.auth_ex.util;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    private SecurityUtil() {

    }

    /**
     * Security Context의 Authentication 객체를 이용해 userEmail을 반환한다.
     * 
     * @return
     */
    public static Optional<String> getCurrentUserEmail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Security Context에 Authentication 객체가 저장되는 시점은 JwtFilter의 doFilter 메소드에서
        // request가 들어올 때 SecurityContext에 Authentication 객체를 저장해서 사용하게 된다.

        if (authentication == null) {
            logger.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String userEmail = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            userEmail = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            userEmail = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(userEmail);
    }

}
