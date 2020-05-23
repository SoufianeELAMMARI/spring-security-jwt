package org.sid;

import org.sid.dao.AppRoleRepository;
import org.sid.dao.AppUserRepository;
import org.sid.model.entities.AppRole;
import org.sid.model.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class JwtSecurityApplication implements CommandLineRunner {

    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    AppRoleRepository appRoleRepository;

    public static void main(String[] args) {
        SpringApplication.run(JwtSecurityApplication.class, args);
    }

    @Override
    public void run(String... args) {
        AppRole role=new AppRole();
        role.setRoleName("ROLE_USER");
        role=appRoleRepository.save(role);


        List<AppRole> roles=new ArrayList<>();
        roles.add(role);


        AppUser user=new AppUser();
        user.setUsername("soufiane");
        user.setActived(true);
        user.setRoles(roles);


        String password = "1234";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);


        appUserRepository.save(user);
    }
}

