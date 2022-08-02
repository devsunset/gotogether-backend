package com.gotogether;

import com.gotogether.entity.Role;
import com.gotogether.repository.RoleRepository;
import com.gotogether.system.enums.RoleType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@PropertySource(value = {"classpath:/appinfo-${spring.profiles.active}.properties"})
public class GotogetherApplication {

    public static void main(String[] args) {
        SpringApplication.run(GotogetherApplication.class, args);
    }

    @Bean
    public CommandLineRunner initRoleTypeDataJPACommandLineRunner(RoleRepository repository) {
        return (args) -> {
            repository.save(new Role(RoleType.ROLE_GUEST));
            repository.save(new Role(RoleType.ROLE_USER));
            repository.save(new Role(RoleType.ROLE_ADMIN));
        };
    }

}
