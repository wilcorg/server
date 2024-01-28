package org.gogame.server.controllers;

import lombok.RequiredArgsConstructor;
import org.gogame.server.domain.entities.UserBioEntity;
import org.gogame.server.domain.entities.dto.UserBioDto;
import org.gogame.server.service.PermissionValidatorService;
import org.gogame.server.service.UserBioService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserBioController {

    private final UserBioService userBioService;

    private final PermissionValidatorService validatorService;

    private final ModelMapper modelMapper;

    @GetMapping("/user/bio/{id}")
    public ResponseEntity<UserBioDto> getUserBio(@PathVariable Long id) {

        UserBioEntity userBio;
        try {
            userBio = userBioService.getUserBio(id);
        } catch (SQLException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(modelMapper.map(userBio, UserBioDto.class), HttpStatus.OK);
    }

    @PutMapping("/user/bio/{id}")
    public ResponseEntity<UserBioDto> setUserBio(@PathVariable Long id, @RequestBody UserBioDto userBioDto, @RequestHeader("Authorization") String token) {
        if (!validatorService.validateUserId(id, token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            userBioService.setUserBio(modelMapper.map(userBioDto, UserBioEntity.class));
        } catch (SQLException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userBioDto, HttpStatus.OK);
    }
}
