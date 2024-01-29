package org.gogame.server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gogame.server.auth.UserLoginDto;
import org.gogame.server.auth.UserRegisterDto;
import org.gogame.server.repositories.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthenticationControllerIntegrationTests {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public AuthenticationControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatRegisterUserSuccessfullyReturns201() throws Exception {
        UserRegisterDto regA = TestData.RegisterDtoUtils.createA();
        String regJson = objectMapper.writeValueAsString(regA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(regJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.CREATED.value())
        );
    }

    @Test
    public void testThatDoubleSameUserRegisterReturns403() throws Exception {
        UserRegisterDto regA = TestData.RegisterDtoUtils.createA();
        String regJson = objectMapper.writeValueAsString(regA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(regJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.CREATED.value())
        );
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(regJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.FORBIDDEN.value())
        );
    }

    @Test
    public void testThatUnknownUserLoginReturns404() throws Exception {
        UserLoginDto logU = TestData.LoginDtoUtils.createUnknownUser();
        String logJson = objectMapper.writeValueAsString(logU);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(logJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value())
        );
    }

    @Test
    public void testThatRegisteredUserSuccessfulLoginReturns200() throws Exception {
        UserRegisterDto regA = TestData.RegisterDtoUtils.createA();
        String regJson = objectMapper.writeValueAsString(regA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(regJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.CREATED.value())
        );

        UserLoginDto logA = TestData.LoginDtoUtils.createA();
        String logJson = objectMapper.writeValueAsString(logA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(logJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );
    }
}
