package org.mh.studyspringbootsecurity.domain.member;

import jakarta.persistence.Entity;
import lombok.*;

import jakarta.persistence.*;
import org.mh.studyspringbootsecurity.domain.common.BaseEntity;
import org.mh.studyspringbootsecurity.jwt.JwtTokenDto;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberType memberType;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Column(length = 200)
    private String password;

    @Column(nullable = false, length = 20)
    private String memberName;

    @Column(length = 200)
    private String profile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    @Column(length = 250)
    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    public void updateRefreshToken(JwtTokenDto jwtTokenDto) {
        this.refreshToken = jwtTokenDto.getRefreshToken();
        // Date 클래스 -> LocalDateTime 으로 변환
        this.tokenExpirationTime = jwtTokenDto.getRefreshTokenExpireTime()
                                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }
}
