package org.mh.studyspringbootsecurity.domain.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void findMemberByEmail() {
        Optional<Member> optionalMember = memberService.findMemberByEmail("aaa@naver.com");
        if(optionalMember.isEmpty())
            System.out.println("optionalMember is empty");
        else
            System.out.println(optionalMember.get().getMemberName());
    }

}