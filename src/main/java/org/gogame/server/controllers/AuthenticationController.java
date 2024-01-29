package org.gogame.server.controllers;

import lombok.RequiredArgsConstructor;
import org.gogame.server.auth.AuthResponseDto;
import org.gogame.server.auth.UserLoginDto;
import org.gogame.server.auth.UserRegisterDto;
import org.gogame.server.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register (
            @RequestBody UserRegisterDto request
    ) {
        AuthResponseDto response;
        try {
            response = service.register(request);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDto> authenticate (
            @RequestBody UserLoginDto request
    ) {

        AuthResponseDto response;
        try {
            response = service.authenticate(request);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
