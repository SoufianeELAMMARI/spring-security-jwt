package org.sid.mapper;


import org.sid.model.dto.UserDetailsDto;
import org.sid.model.entities.AppUser;


public class UserMapper {


    public static UserDetailsDto BuildDtoFromUser(AppUser appUser) {
        UserDetailsDto userDetailsDto=new UserDetailsDto();
        userDetailsDto.setUsername(appUser.getUsername());
        return userDetailsDto;

    }


}
