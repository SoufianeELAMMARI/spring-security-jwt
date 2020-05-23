package org.sid.services;


import org.sid.dao.AppUserRepository;
import org.sid.model.dto.UserDetailsDto;
import org.sid.model.entities.AppRole;
import org.sid.model.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AppUserService {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public Map<String,Object>  saveUser(UserDetailsDto userDetailsDto)
    {
        Map<String,Object> map=new HashMap<>();
        try {
            AppUser user=new AppUser();
            List<AppRole> roles=new ArrayList<>();
            user.setUsername(userDetailsDto.getUsername());
            user.setActived(userDetailsDto.isActived());

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(userDetailsDto.getPassword());
            user.setPassword(hashedPassword);

            userDetailsDto.getRoles().stream().forEach(item->{
                AppRole role=new AppRole();
                role.setRoleName(item);
                roles.add(role);
            });
            user.setRoles(roles);
            user=appUserRepository.save(user);
            map.put("user",new UserDetailsDto(user));
            map.put("success",true);
        } catch (Exception e) {
            map.put("user",null);
            map.put("success",false);
        }
        return map;
    }

}
