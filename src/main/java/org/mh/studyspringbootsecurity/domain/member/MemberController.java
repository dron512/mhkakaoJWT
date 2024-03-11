package org.mh.studyspringbootsecurity.domain.member;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.mh.studyspringbootsecurity.error.AuthenticationException;
import org.mh.studyspringbootsecurity.error.ErrorCode;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("adminMember")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/{id}")
    public MappingJacksonValue getById(@PathVariable Long id){

        Optional<Member> dbMember = memberRepository.findById(id);

        AdminMember adminMember = new AdminMember();
        dbMember.ifPresentOrElse(item -> {
            BeanUtils.copyProperties(item,adminMember);
            System.out.println(adminMember);
        },()->{
            throw new AuthenticationException(ErrorCode.MEMBER_NOT_EXISTS);
        });

        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.filterOutAllExcept("email","memberName");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("AdminMember",simpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(adminMember);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }
}
