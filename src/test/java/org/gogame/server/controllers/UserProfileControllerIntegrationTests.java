package org.gogame.server.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.gogame.server.domain.entities.dto.UserBioDto;
import org.gogame.server.domain.entities.dto.UserRegisterDto;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class UserProfileControllerIntegrationTests {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Autowired
    public UserProfileControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatUserFetchingProfileReturns200() throws Exception {
        UserRegisterDto regA = TestData.RegisterDtoUtils.createA();
        MvcResult mvcResult = ControllerUtils.registerUser(mockMvc, objectMapper, regA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/user/profile/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", ControllerUtils.getJwtToken(mvcResult))
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );
    }

    @Test
    public void testThatUpdateUserBioReturns200() throws Exception {
        UserRegisterDto regA = TestData.RegisterDtoUtils.createA();
        MvcResult mvcResult = ControllerUtils.registerUser(mockMvc, objectMapper, regA);

        UserBioDto bio = UserBioDto.builder().userId(1L).bio("good boi").build();
        String bioJson = objectMapper.writeValueAsString(bio);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/user/bio/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", ControllerUtils.getJwtToken(mvcResult))
                        .content(bioJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );
    }

    @Test
    public void testThatUnauthorizedUserBioUpdateReturns401() throws Exception {
        UserRegisterDto regA = TestData.RegisterDtoUtils.createA();
        ControllerUtils.registerUser(mockMvc, objectMapper, regA);

        UserRegisterDto regB = TestData.RegisterDtoUtils.createB();
        MvcResult mvcResult = ControllerUtils.registerUser(mockMvc, objectMapper, regB);


        UserBioDto bio = UserBioDto.builder().userId(1L).bio("hackerman").build();
        String bioJson = objectMapper.writeValueAsString(bio);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/user/bio/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", ControllerUtils.getJwtToken(mvcResult))
                        .content(bioJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value())
        );
    }
}
