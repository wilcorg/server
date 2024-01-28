package org.gogame.server.mappers.impl;

import org.gogame.server.domain.entities.UserBioEntity;
import org.gogame.server.domain.entities.dto.UserBioDto;
import org.gogame.server.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserBioMapper implements Mapper<UserBioEntity, UserBioDto>{

    private final ModelMapper modelMapper;

    public UserBioMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserBioDto mapTo(UserBioEntity userBioEntity) {
        return modelMapper.map(userBioEntity, UserBioDto.class);
    }

    @Override
    public UserBioEntity mapFrom(UserBioDto userBioDto) {
        return modelMapper.map(userBioDto, UserBioEntity.class);
    }
}