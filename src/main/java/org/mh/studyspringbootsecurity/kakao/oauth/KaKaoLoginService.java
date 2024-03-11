package org.mh.studyspringbootsecurity.kakao.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mh.studyspringbootsecurity.domain.member.Member;
import org.mh.studyspringbootsecurity.domain.member.MemberService;
import org.mh.studyspringbootsecurity.domain.member.MemberType;
import org.mh.studyspringbootsecurity.domain.member.Role;
import org.mh.studyspringbootsecurity.jwt.JwtTokenDto;
import org.mh.studyspringbootsecurity.jwt.TokenManager;
import org.mh.studyspringbootsecurity.kakao.OAuthAttributes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class KaKaoLoginService {

    private final MemberService memberService;
    private final TokenManager tokenManager;
    private final KakaoLoginApiService kakaoLoginApiService;

    // 카카오 로그인 성공시
    // 신규 회원일때 MEMBER 데이터 추가 회원가입
    // 기존 화원일때 MEMBER 데이터 리프레시 토큰 업데이트
    public KaKaoLoginDto.Response oauthLogin(String accessToken, MemberType memberType) {
        OAuthAttributes userInfo = kakaoLoginApiService.getUserInfo(accessToken);
        log.info("userInfo : {}",  userInfo);

        JwtTokenDto jwtTokenDto;
        Optional<Member> optionalMember = memberService.findMemberByEmail(userInfo.getEmail());
        if(optionalMember.isEmpty()) { // 신규 회원 가입
            Member oauthMember = userInfo.toMemberEntity(memberType, Role.ADMIN);
            oauthMember = memberService.registerMember(oauthMember);

            // 토큰 생성
            jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.getMemberId(), oauthMember.getRole());
            oauthMember.updateRefreshToken(jwtTokenDto);

            log.info("Member add");
        } else { // 기존 회원일 경우
            Member oauthMember = optionalMember.get();

            // 토큰 생성
            jwtTokenDto = tokenManager.createJwtTokenDto(oauthMember.getMemberId(), oauthMember.getRole());
            oauthMember.updateRefreshToken(jwtTokenDto);

            log.info("Member update");
        }

        return KaKaoLoginDto.Response.of(jwtTokenDto);
    }

}
