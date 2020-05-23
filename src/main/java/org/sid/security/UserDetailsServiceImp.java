package org.sid.security;

import org.sid.dao.AppUserRepository;
import org.sid.model.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         AppUser user=appUserRepository.findByUsername(username);
        if(user==null) throw new UsernameNotFoundException(username);
        List<GrantedAuthority> authorities=new ArrayList<>();
        user.getRoles().stream().forEach(item->authorities.add(new SimpleGrantedAuthority(item.getRoleName())));
        return new User(user.getUsername(),user.getPassword(),authorities);
    }
}
