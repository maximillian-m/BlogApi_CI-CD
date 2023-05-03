package com.maximillian.blogapiwithsecurity.Service;

import com.maximillian.blogapiwithsecurity.Dto.AuthResponse;
import com.maximillian.blogapiwithsecurity.Dto.UsersDto;
import com.maximillian.blogapiwithsecurity.Exceptions.CustomException;

public interface UserService {
     AuthResponse createUser(UsersDto userDto) throws CustomException;
     AuthResponse loginUsers(UsersDto userDto)throws CustomException;

     AuthResponse createAdmin(UsersDto user);

     UsersDto findUserById(Long id) throws CustomException;
}
