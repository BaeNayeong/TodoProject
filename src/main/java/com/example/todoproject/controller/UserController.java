package com.example.todoproject.controller;

import com.example.todoproject.dto.ResponseDto;
import com.example.todoproject.dto.UserDto;
import com.example.todoproject.model.UserEntity;
import com.example.todoproject.security.TokenProvider;
import com.example.todoproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider; // 가져올 토큰

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){

        try {
            UserEntity user= UserEntity.builder()
                    .email(userDto.getEmail())
                    .username(userDto.getUsername())
                    .password(userDto.getPassword())
                    .build();

            // 서비스를 이용해 리포지토리에 사용자 저장
            UserEntity registeredUser= userService.create(user);

            UserDto responseUserDto=UserDto.builder()
                    .email(registeredUser.getEmail())
                    .id(registeredUser.getId())
                    .username(registeredUser.getUsername())
                    .build();

            return ResponseEntity.ok().body(responseUserDto);
        }
        catch(Exception e){

            // 사용자 정보는 항상 하나이므로 리스트로 만들어야 하는 ResponseDto를 사용하지 않고 그냥 UserDto 리턴
            ResponseDto responseDto=ResponseDto.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto){
        UserEntity user = userService.getByCredential(userDto.getEmail(),userDto.getPassword());

        if(user!=null){

            final String token=tokenProvider.create(user);
            final UserDto responseUserDto = UserDto.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .username(user.getUsername())
                    .token(token)
                    .build();

            return ResponseEntity.ok().body(responseUserDto);
        }
        else {

            ResponseDto responseDto = ResponseDto.builder()
                    .error("Login failed")
                    .build();

            return ResponseEntity.badRequest().body(responseDto);
        }
    }
}
