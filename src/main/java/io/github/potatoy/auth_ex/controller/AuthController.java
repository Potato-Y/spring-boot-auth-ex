package io.github.potatoy.auth_ex.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.potatoy.auth_ex.dto.LoginDto;
import io.github.potatoy.auth_ex.dto.TokenDto;
import io.github.potatoy.auth_ex.jwt.JwtFilter;
import io.github.potatoy.auth_ex.jwt.TokenProvider;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // tokenProvider과 authenticationManagerBuilder를 주입받는다.
    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    /**
     * 
     * @param loginDto LoginDto의 username, password를 받는다.
     * @return ResponseEntity<TokenDto>
     */
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()); // 받은 파라미터를 통해 UsernamePasswordAuthenticationToken을 생성한다.

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // authenticate 메소드가 실행되며 CustomUserDetailsService의 loadUserByUsername이 실행된다.
        // 이를 통해 Authentication 객체가 생성된다. 이를 SecurityContext에 저장한다.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication); // Authentication객체를 createToken을 통해서 JWT Token을 생성한다.

        // JWT Token을 Response Header에 넣어준다.
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        // TokenDto를 이용해서 Response Body에도 넣어서 반환한다.
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

}
