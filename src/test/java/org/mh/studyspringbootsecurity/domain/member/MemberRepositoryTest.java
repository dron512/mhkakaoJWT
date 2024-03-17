package org.mh.studyspringbootsecurity.domain.member;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Test
    public void saveProductTest() {
        //given
        Member optionalMember = memberRepository.save(Member.builder()
                .email("aaa@naver.com")
                .memberName("aaa")
                .memberType(MemberType.KAKAO)
                .role(Role.USER)
                .build());
        Assertions.assertEquals(optionalMember.getMemberName(), "aaa");
    }


}