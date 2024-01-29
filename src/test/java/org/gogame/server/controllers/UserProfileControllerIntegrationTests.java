package org.gogame.server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gogame.server.domain.entities.dto.user.UserBioDto;
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
        String regJson = objectMapper.writeValueAsString(regA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(regJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.CREATED.value())
        );

        UserRegisterDto regB = TestData.RegisterDtoUtils.createB();
        regJson = objectMapper.writeValueAsString(regB);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(regJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.CREATED.value())
        ).andReturn();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/user/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestData.getJwtToken(mvcResult))
                        .param("idAuthor", "2")
                        .param("idAbout", "1")
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );
    }

    @Test
    public void testThatUpdateUserBioReturns200() throws Exception {
        UserRegisterDto regA = TestData.RegisterDtoUtils.createA();
        String regJson = objectMapper.writeValueAsString(regA);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(regJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.CREATED.value())
        ).andReturn();

        UserBioDto bio = UserBioDto.builder().userId(1L).bio("good boi").build();
        String bioJson = objectMapper.writeValueAsString(bio);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/user/bio/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestData.getJwtToken(mvcResult))
                        .content(bioJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );
    }

    @Test
    public void testThatUnauthorizedUserBioUpdateReturns401() throws Exception {
        UserRegisterDto regA = TestData.RegisterDtoUtils.createA();
        String regJson = objectMapper.writeValueAsString(regA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(regJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.CREATED.value())
        );


        UserRegisterDto regB = TestData.RegisterDtoUtils.createB();
        regJson = objectMapper.writeValueAsString(regB);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(regJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.CREATED.value())
        ).andReturn();


        UserBioDto bio = UserBioDto.builder().userId(1L).bio("hackerman").build();
        String bioJson = objectMapper.writeValueAsString(bio);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/user/bio/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TestData.getJwtToken(mvcResult))
                        .content(bioJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.UNAUTHORIZED.value())
        );
    }
}
