package org.mh.studyspringbootsecurity.example;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mh.studyspringbootsecurity.domain.member.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@Import({MemberController.class, MemberService.class})
public class MemberTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void findMemberByEmail() {
        Optional<Member> optionalMember = memberService.findMemberByEmail("aaa@naver.com");

        Assertions.assertEquals(optionalMember.get().getMemberName(), "aaa");
    }

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