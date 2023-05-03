package com.maximillian.blogapiwithsecurity.controller;

import com.maximillian.blogapiwithsecurity.Dto.AuthResponse;
import com.maximillian.blogapiwithsecurity.Dto.UsersDto;
import com.maximillian.blogapiwithsecurity.Exceptions.CustomException;
import com.maximillian.blogapiwithsecurity.Models.Token;
import com.maximillian.blogapiwithsecurity.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/registerAdmin", method = RequestMethod.POST)
    public AuthResponse createAdmin(@Valid @RequestBody UsersDto user) {
        return userService.createAdmin(user);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public AuthResponse createUser(@Valid  @RequestBody UsersDto user) {
     return userService.createUser(user);
    }

    //endpoint that takes care of the user login methods
    @PostMapping("/login")
    public AuthResponse loginUser(@RequestBody UsersDto userDto, HttpServletRequest httpServletRequest)throws CustomException {
            return userService.loginUsers(userDto);
    }

    //The logout endpoint for the user
    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession(false);
        if(session != null){
            session.invalidate();
            return new ResponseEntity<>("You are now logged out", HttpStatus.OK);
        }
        return new ResponseEntity<>("User is not logged in yet", HttpStatus.FORBIDDEN);
    }

}
