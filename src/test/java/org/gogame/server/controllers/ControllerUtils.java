package org.gogame.server.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gogame.server.auth.UserRegisterDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class ControllerUtils {

    public static MvcResult registerUser(MockMvc mockMvc, ObjectMapper objectMapper, UserRegisterDto userRegisterDto) throws Exception{
        String regAJson = objectMapper.writeValueAsString(userRegisterDto);

        return mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(regAJson)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.CREATED.value())
        ).andReturn();
    }

    public static String getJwtToken(MvcResult mvcResult) {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode;
        try {
            String responseContent = mvcResult.getResponse().getContentAsString();
            jsonNode = objectMapper.readTree(responseContent);
        } catch (Exception e) {
            System.err.println("Unable do decode JSON");
            return "";
        }
        return "Bearer " + jsonNode.get("token").asText();
    }

    public static Long getUserId(MvcResult mvcResult) {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode;
        try {
            String responseContent = mvcResult.getResponse().getContentAsString();
            jsonNode = objectMapper.readTree(responseContent);
        } catch (Exception e) {
            System.err.println("Unable do decode JSON");
            return -1L;
        }
        return jsonNode.get("userId").asLong(-1L);
    }
}
