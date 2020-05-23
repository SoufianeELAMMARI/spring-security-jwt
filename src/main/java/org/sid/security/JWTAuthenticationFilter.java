package org.sid.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.sid.dao.AppUserRepository;
import org.sid.mapper.UserMapper;
import org.sid.model.dto.UserDetailsDto;
import org.sid.model.entities.AppUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    private AppUserRepository appUserRepository;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,AppUserRepository appUserRepository) {
        super();
        this.authenticationManager = authenticationManager;
        this.appUserRepository=appUserRepository;

    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        AppUser user=null;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
        } catch (Exception e) {
            throw new RuntimeException("problèmes lié au mapping d'utilisateur username/password");
        }
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                ));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse
            response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User springUser=(User)authResult.getPrincipal();
        String jwtToken= Jwts.builder()
                .setSubject(springUser.getUsername())
                .setExpiration(new
                        Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .claim("roles", springUser.getAuthorities())
                .compact();
        response.addHeader(SecurityConstants.HEADER_STRING,
                SecurityConstants.TOKEN_PREFIX+jwtToken);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        UserDetailsDto userDto=UserMapper.BuildDtoFromUser(appUserRepository.findByUsername(springUser.getUsername()));
        PrintWriter out = response.getWriter();
        String userJson = gson.toJson(userDto);
        String authorization = SecurityConstants.TOKEN_PREFIX+jwtToken;
        String body = "{\"Authorization\": \""+authorization+"\",\"user\":"+userJson+"}";
        out.println(body);
        out.close();
    }
}
