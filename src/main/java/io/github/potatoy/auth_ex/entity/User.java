package io.github.potatoy.auth_ex.entity;

import lombok.*;

import javax.persistence.*;

import java.util.Set;

@Entity // DB하고 1:1 매칭 객체
@Table(name = "`user`")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email", length = 50, unique = true)
    private String userEmail;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "activated")
    private boolean activated;

    @Enumerated(EnumType.STRING)
    @Column(name = "country", nullable = false)
    private Country country;

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language;

    @ManyToMany // 일대다, 다대일 관계의 조인 테이블로 정의
    @JoinTable(name = "user_authority", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "user_id") }, inverseJoinColumns = {
                    @JoinColumn(name = "authority_name", referencedColumnName = "authority_name") })
    private Set<Authority> authorities;

}
