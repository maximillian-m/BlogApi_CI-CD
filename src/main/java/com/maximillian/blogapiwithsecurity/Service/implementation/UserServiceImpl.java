package com.maximillian.blogapiwithsecurity.Service.implementation;

import com.maximillian.blogapiwithsecurity.Config.SecurityConfig.JwtService;
import com.maximillian.blogapiwithsecurity.Dto.AuthResponse;
import com.maximillian.blogapiwithsecurity.Dto.UsersDto;
import com.maximillian.blogapiwithsecurity.Enums.*;
import com.maximillian.blogapiwithsecurity.Exceptions.CustomException;
import com.maximillian.blogapiwithsecurity.Models.Token;
import com.maximillian.blogapiwithsecurity.Models.Users;
import com.maximillian.blogapiwithsecurity.Repositories.TokenRepository;
import com.maximillian.blogapiwithsecurity.Repositories.UserRepository;
import com.maximillian.blogapiwithsecurity.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.maximillian.blogapiwithsecurity.Mapper.UserMapper.dtoToEntity;
import static com.maximillian.blogapiwithsecurity.Mapper.UserMapper.mappedToDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager manager;

    private final TokenRepository tokenRepository;

    //Method to create a regular
    @Override
    public AuthResponse createUser(UsersDto userDto) {
        Users user = dtoToEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(Roles.REGULAR);
      Users savedUser = userRepository.save(user);
      String jwtToken = jwtService.generateToken(user);
      saveToken(savedUser, jwtToken);
      return AuthResponse.builder()
              .Token(jwtToken)
              .build();
    }


    //helper method to save token
    public void saveToken(Users user, String token){
        Token toke = Token.builder()
                .token(token)
                .user(user)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(toke);
    }
//Method to take care of Login
@Override
    public AuthResponse loginUsers(UsersDto userDto)throws CustomException{
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getEmail(),
                        userDto.getPassword()
                )
        );
       Users user  = userRepository.findByEmail(userDto.getEmail())
               .orElseThrow();
       String jwtToken = jwtService.generateToken(user);
       revokeAllTokens(user);
       saveToken(user, jwtToken);
        return AuthResponse.builder()
                .Token(jwtToken)
                .build();
    }

    private void revokeAllTokens(Users user) {
        List<Token> listOfTokens = tokenRepository.findAllTokenByUser(user.getId());
        if(listOfTokens.isEmpty()){
            return;
        }
        listOfTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(listOfTokens);
    }

    //Method to create ADMIN
    @Override
    public AuthResponse createAdmin(UsersDto userDto) {
        Users user = dtoToEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(Roles.ADMIN);
        Users savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(savedUser);
        saveToken(savedUser, jwtToken);
      return AuthResponse.builder()
              .Token(jwtToken)
              .build();
    }
    //Mapper from DTO to Entity
    public UsersDto findUserById(Long id) throws CustomException {
        Users users = userRepository.findById(id).orElseThrow(() ->(new CustomException("User not found")));
        return mappedToDto(users);
    }
}
