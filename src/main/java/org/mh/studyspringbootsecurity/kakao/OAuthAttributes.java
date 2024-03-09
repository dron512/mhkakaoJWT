package org.mh.studyspringbootsecurity.kakao;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.mh.studyspringbootsecurity.domain.member.Member;
import org.mh.studyspringbootsecurity.domain.member.MemberType;
import org.mh.studyspringbootsecurity.domain.member.Role;

@ToString
@Getter @Builder
public class OAuthAttributes {

    private String name;
    private String email;
    private String profile;
    private MemberType memberType;

    public Member toMemberEntity(MemberType memberType, Role role) {
        return Member.builder()
                .memberName(name)
                .email(email)
                .memberType(memberType)
                .profile(profile)
                .role(role)
                .build();
    }

}
