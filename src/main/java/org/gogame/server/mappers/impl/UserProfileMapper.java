package org.gogame.server.mappers.impl;

import org.gogame.server.domain.entities.UserBioEntity;
import org.gogame.server.domain.entities.UserEntity;
import org.gogame.server.domain.entities.dto.user.UserProfileDto;
import org.gogame.server.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper implements Mapper<Pair<UserEntity, UserBioEntity>, UserProfileDto>{

    private final ModelMapper modelMapper;

    public UserProfileMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserProfileDto mapTo(Pair<UserEntity, UserBioEntity> userProfilePair) {

        UserProfileDto userProfileDto = modelMapper.map(userProfilePair.getFirst(), UserProfileDto.class);
        userProfileDto.setBio(userProfilePair.getSecond().getBio());
        return userProfileDto;
    }

    @Override
    public Pair<UserEntity, UserBioEntity> mapFrom(UserProfileDto userProfileDto) {

        return Pair.of(
                modelMapper.map(userProfileDto, UserEntity.class),
                modelMapper.map(userProfileDto, UserBioEntity.class)
        );
    }
}