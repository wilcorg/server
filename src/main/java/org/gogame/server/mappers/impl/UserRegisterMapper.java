package org.gogame.server.mappers.impl;

import org.gogame.server.domain.entities.UserEntity;
import org.gogame.server.auth.UserRegisterDto;
import org.gogame.server.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class UserRegisterMapper implements Mapper<UserRegisterDto, UserEntity> {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserRegisterMapper(ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity mapTo(UserRegisterDto userRegisterDto) {
        UserEntity userEntity = modelMapper.map(userRegisterDto, UserEntity.class);
        userEntity.setPasswordHash(passwordEncoder.encode(userRegisterDto.getPassword()));
        userEntity.setJoinDate(new Timestamp(new Date().getTime()));
        return userEntity;
    }

    // Should not be used
    @Override
    public UserRegisterDto mapFrom(UserEntity userEntity) {
        return null;
    }
}
