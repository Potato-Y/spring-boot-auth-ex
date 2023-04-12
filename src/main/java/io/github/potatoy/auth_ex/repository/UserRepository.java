package io.github.potatoy.auth_ex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import io.github.potatoy.auth_ex.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository를 extends하면 findAll, save 등의 메소드를 기본적으로 사용할 수 있게 된다.
    
    /**
     * userEmail을 기준으로 User 정보를 가져올 때 권한 정보도 같이 가져오게 된다.
     * @param userEmail
     * @return
     */
    @EntityGraph(attributePaths = "authorities") // 쿼리가 수행될 때 Lazy조회가 아닌 Eager 조회로 authorities 정보를 같이 가져오게 된다.
    Optional<User> findOneWithAuthoritiesByUserEmail(String userEmail);
}
