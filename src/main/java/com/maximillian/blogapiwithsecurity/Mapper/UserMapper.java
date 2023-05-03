package com.maximillian.blogapiwithsecurity.Mapper;

import com.maximillian.blogapiwithsecurity.Dto.UsersDto;
import com.maximillian.blogapiwithsecurity.Models.Users;

public class UserMapper {
    public static Users dtoToEntity(UsersDto us){
        Users user = new Users();
        user.setUserName(us.getUserName());
        user.setEmail(us.getEmail());
        user.setPassword(us.getPassword());
        return user;
    }
    //Mapper from Entity DTO
    public static UsersDto mappedToDto(Users us){
        UsersDto user = new UsersDto();
        user.setId(us.getId());
        user.setUserName(us.getUsername());
        user.setEmail(us.getEmail());
        user.setPassword(us.getPassword());
        return user;
    }
}
