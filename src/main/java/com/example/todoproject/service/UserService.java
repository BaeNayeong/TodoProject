package com.example.todoproject.service;

import com.example.todoproject.model.UserEntity;
import com.example.todoproject.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity){

        if(userEntity==null || userEntity.getEmail()==null){
            throw new RuntimeException("Invalid arguments");
        }

        final String email = userEntity.getEmail();

        if(userRepository.existsByEmail(email)){
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(userEntity);
    }

    public UserEntity getByCredential(final String email, final String password){
        return userRepository.findByEmailAndPassword(email, password);
    }
}
