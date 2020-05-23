package org.sid.web;
import org.sid.model.dto.UserDetailsDto;
import org.sid.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/userManager")
public class UserController {

    @Autowired
    AppUserService appUserService;


    @PostMapping("/saveUser")
    public Map<String ,Object> saveUser(@RequestBody UserDetailsDto userDetailsDto){
        return appUserService.saveUser(userDetailsDto);
    }

}
