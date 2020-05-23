package org.sid.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.sid.model.entities.AppUser;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetailsDto {

    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private boolean actived;
    private List<String> roles=new ArrayList<>();

    public UserDetailsDto(AppUser user)
    {
        this.id=user.getId();
        this.username=user.getUsername();
        this.actived=user.isActived();
        user.getRoles().stream().forEach(item->{
            roles.add(item.getRoleName());
        });
    }
}

