package org.mh.studyspringbootsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StudyspringbootsecurityApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(StudyspringbootsecurityApplication.class, args);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StudyspringbootsecurityApplication.class);
    }


}
