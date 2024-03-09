package org.mh.studyspringbootsecurity.kakao;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mh.studyspringbootsecurity.domain.member.MemberType;
import org.mh.studyspringbootsecurity.error.AuthenticationException;
import org.mh.studyspringbootsecurity.error.BusinessException;
import org.mh.studyspringbootsecurity.error.ErrorCode;
import org.mh.studyspringbootsecurity.jwt.GrantType;
import org.mh.studyspringbootsecurity.kakao.oauth.OauthLoginDto;
import org.mh.studyspringbootsecurity.kakao.oauth.OauthLoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KaKaoController {

    private final KakaoTokenClient kakaoTokenClient;
    private final OauthLoginService oauthLoginService;


    //import org.springframework.beans.factory.annotation.Value;
    //롬복에 있는 value 아님
    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @PostMapping("/login")
    public ResponseEntity<OauthLoginDto.Response> oauthLogin(@RequestBody OauthLoginDto.Request oauthLoginRequestDto,
                                                             HttpServletRequest httpServletRequest) {
        // ================카카오 토큰이 들어왔는지 확인 시작
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        // 1. authorization 토큰 들어왔는치 체크
        if(!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }

        // 2. authorizationHeader Bearer 체크
        String[] authorizations = authorizationHeader.split(" ");
        if(authorizations.length < 2 || (!GrantType.BEARER.getType().equals(authorizations[0]))) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }

        // 3. 카카오 로그인인지 확인
        if(!MemberType.isMemberType(oauthLoginRequestDto.getMemberType())) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
        }

        String accessToken = authorizationHeader.split(" ")[1];
        OauthLoginDto.Response jwtTokenResponseDto = oauthLoginService
                .oauthLogin(accessToken, MemberType.from(oauthLoginRequestDto.getMemberType()));
        return ResponseEntity.ok(jwtTokenResponseDto);
    }

    @GetMapping("/oauth/kakao/callback")
    public @ResponseBody String loginCallback(String code) {
        String contentType = "application/x-www-form-urlencoded;charset=utf-8";
        KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .grant_type("authorization_code")
                .code(code)
                .redirect_uri("http://localhost:8080/oauth/kakao/callback")
                .build();
        KakaoTokenDto.Response kakaoToken = kakaoTokenClient.requestKakaoToken(contentType, kakaoTokenRequestDto);
        return "kakao toekn : " + kakaoToken;
    }
}
