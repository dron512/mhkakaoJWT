package org.mh.studyspringbootsecurity.domain.member;

public enum Role {

    USER, ADMIN;

    public static Role from(String role) {
        return Role.valueOf(role);
    }

}
