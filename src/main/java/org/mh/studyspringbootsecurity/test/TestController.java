package org.mh.studyspringbootsecurity.test;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class TestController {

    private final TestRepository testRepository;

    @GetMapping("insert")
    public String insert(){
        testRepository.save(new Test());
        return "insert";
    }

    @GetMapping("update")
    @Transactional
    public String update(){
        Test testEntity= testRepository.findById(1l).orElseThrow();
        testEntity.setAa("test");
        return "update";
    }
}
