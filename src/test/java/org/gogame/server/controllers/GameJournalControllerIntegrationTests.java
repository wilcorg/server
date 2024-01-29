package org.gogame.server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gogame.server.auth.UserRegisterDto;
import org.gogame.server.domain.entities.dto.game.GameJournalDto;
import org.gogame.server.domain.entities.dto.user.UserInviteDto;
import org.gogame.server.domain.entities.enums.GameAction;
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
public class GameJournalControllerIntegrationTests {

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;


    @Autowired
    public GameJournalControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatTwoSameUserMovesReturns208() throws Exception {

        UserRegisterDto regA = TestData.RegisterDtoUtils.createA();
        MvcResult mvcAResult = ControllerUtils.registerUser(mockMvc, objectMapper, regA);

        UserRegisterDto regB = TestData.RegisterDtoUtils.createB();
        MvcResult mvcBResult = ControllerUtils.registerUser(mockMvc, objectMapper, regB);

        UserInviteDto invite = UserInviteDto.builder()
                .userSenderId(ControllerUtils.getUserId(mvcAResult))
                .userReceiverId(ControllerUtils.getUserId(mvcBResult))
                .build();

        sendGameInvite(
                invite,
                ControllerUtils.getJwtToken(mvcAResult)
        );

        String inviteJson = objectMapper.writeValueAsString(invite);

        String senderToken = ControllerUtils.getJwtToken(mvcBResult);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/invite/accept")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inviteJson)
                        .header("Authorization", ControllerUtils.getJwtToken(mvcBResult))
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/game/invite/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inviteJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value())
        );

        String moveJson = objectMapper.writeValueAsString(GameJournalDto.builder()
                .gameId(1L)
                .authorId(2L)
                .turnX(5)
                .turnY(5)
                .action(GameAction.MOVE)
                .build());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/move/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moveJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );

        senderToken = ControllerUtils.getJwtToken(mvcAResult);
        moveJson = objectMapper.writeValueAsString(GameJournalDto.builder()
                .gameId(1L)
                .authorId(1L)
                .turnX(6)
                .turnY(6)
                .action(GameAction.MOVE)
                .build());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/move/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moveJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );

        senderToken = ControllerUtils.getJwtToken(mvcBResult);
        moveJson = objectMapper.writeValueAsString(GameJournalDto.builder()
                .gameId(1L)
                .authorId(1L)
                .turnX(6)
                .turnY(6)
                .action(GameAction.MOVE)
                .build());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/move/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moveJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.ALREADY_REPORTED.value())
        );
    }

    @Test
    public void testThatTwoUsersCanMoveFlawlessly() throws Exception {

        UserRegisterDto regA = TestData.RegisterDtoUtils.createA();
        MvcResult mvcAResult = ControllerUtils.registerUser(mockMvc, objectMapper, regA);

        UserRegisterDto regB = TestData.RegisterDtoUtils.createB();
        MvcResult mvcBResult = ControllerUtils.registerUser(mockMvc, objectMapper, regB);

        UserInviteDto invite = UserInviteDto.builder()
                .userSenderId(ControllerUtils.getUserId(mvcAResult))
                .userReceiverId(ControllerUtils.getUserId(mvcBResult))
                .build();

        sendGameInvite(
                invite,
                ControllerUtils.getJwtToken(mvcAResult)
        );

        String inviteJson = objectMapper.writeValueAsString(invite);

        String senderToken = ControllerUtils.getJwtToken(mvcBResult);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/invite/accept")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inviteJson)
                        .header("Authorization", ControllerUtils.getJwtToken(mvcBResult))
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/game/invite/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inviteJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value())
        );

        String moveJson = objectMapper.writeValueAsString(GameJournalDto.builder()
                .gameId(1L)
                .authorId(2L)
                .turnX(5)
                .turnY(5)
                .action(GameAction.MOVE)
                .build());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/move/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moveJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );

        senderToken = ControllerUtils.getJwtToken(mvcAResult);
        moveJson = objectMapper.writeValueAsString(GameJournalDto.builder()
                .gameId(1L)
                .authorId(1L)
                .turnX(6)
                .turnY(6)
                .action(GameAction.MOVE)
                .build());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/move/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moveJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );

        senderToken = ControllerUtils.getJwtToken(mvcBResult);
        moveJson = objectMapper.writeValueAsString(GameJournalDto.builder()
                .gameId(1L)
                .authorId(2L)
                .turnX(7)
                .turnY(7)
                .action(GameAction.MOVE)
                .build());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/move/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moveJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );
    }

    private void sendGameInvite(UserInviteDto userInviteDto, String senderToken) throws Exception {
        String inviteJson = objectMapper.writeValueAsString(userInviteDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/invite/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inviteJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.CREATED.value())
        );
    }

    @Test
    public void testThatLastTurnFetchReturns200() throws Exception {

        UserRegisterDto regA = TestData.RegisterDtoUtils.createA();
        MvcResult mvcAResult = ControllerUtils.registerUser(mockMvc, objectMapper, regA);

        UserRegisterDto regB = TestData.RegisterDtoUtils.createB();
        MvcResult mvcBResult = ControllerUtils.registerUser(mockMvc, objectMapper, regB);

        UserInviteDto invite = UserInviteDto.builder()
                .userSenderId(ControllerUtils.getUserId(mvcAResult))
                .userReceiverId(ControllerUtils.getUserId(mvcBResult))
                .build();

        sendGameInvite(
                invite,
                ControllerUtils.getJwtToken(mvcAResult)
        );

        String inviteJson = objectMapper.writeValueAsString(invite);

        String senderToken = ControllerUtils.getJwtToken(mvcBResult);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/invite/accept")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inviteJson)
                        .header("Authorization", ControllerUtils.getJwtToken(mvcBResult))
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/game/invite/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inviteJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value())
        );

        String moveJson = objectMapper.writeValueAsString(GameJournalDto.builder()
                .gameId(1L)
                .authorId(2L)
                .turnX(5)
                .turnY(5)
                .action(GameAction.MOVE)
                .build());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/move/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moveJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );

        senderToken = ControllerUtils.getJwtToken(mvcAResult);
        moveJson = objectMapper.writeValueAsString(GameJournalDto.builder()
                .gameId(1L)
                .authorId(1L)
                .turnX(6)
                .turnY(6)
                .action(GameAction.MOVE)
                .build());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/move/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moveJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );

        senderToken = ControllerUtils.getJwtToken(mvcAResult);
        var mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/game/move/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testStoneChocking() throws Exception {

        UserRegisterDto regA = TestData.RegisterDtoUtils.createA();
        MvcResult mvcAResult = ControllerUtils.registerUser(mockMvc, objectMapper, regA);

        UserRegisterDto regB = TestData.RegisterDtoUtils.createB();
        MvcResult mvcBResult = ControllerUtils.registerUser(mockMvc, objectMapper, regB);

        UserInviteDto invite = UserInviteDto.builder()
                .userSenderId(ControllerUtils.getUserId(mvcAResult))
                .userReceiverId(ControllerUtils.getUserId(mvcBResult))
                .build();

        sendGameInvite(
                invite,
                ControllerUtils.getJwtToken(mvcAResult)
        );

        String inviteJson = objectMapper.writeValueAsString(invite);

        String senderToken = ControllerUtils.getJwtToken(mvcBResult);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/invite/accept")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inviteJson)
                        .header("Authorization", ControllerUtils.getJwtToken(mvcBResult))
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/game/invite/fetch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inviteJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value())
        );

        String moveJson = objectMapper.writeValueAsString(GameJournalDto.builder()
                .gameId(1L)
                .authorId(2L)
                .turnX(0)
                .turnY(0)
                .action(GameAction.MOVE)
                .build());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/move/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moveJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );

        senderToken = ControllerUtils.getJwtToken(mvcAResult);
        moveJson = objectMapper.writeValueAsString(GameJournalDto.builder()
                .gameId(1L)
                .authorId(1L)
                .turnX(0)
                .turnY(1)
                .action(GameAction.MOVE)
                .build());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/move/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moveJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );

        moveJson = objectMapper.writeValueAsString(GameJournalDto.builder()
                .gameId(1L)
                .authorId(2L)
                .turnX(1)
                .turnY(1)
                .action(GameAction.MOVE)
                .build());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/move/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moveJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );

        senderToken = ControllerUtils.getJwtToken(mvcAResult);
        moveJson = objectMapper.writeValueAsString(GameJournalDto.builder()
                .gameId(1L)
                .authorId(1L)
                .turnX(1)
                .turnY(0)
                .action(GameAction.MOVE)
                .build());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/game/move/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(moveJson)
                        .header("Authorization", senderToken)
        ).andExpect(
                MockMvcResultMatchers.status().is(HttpStatus.OK.value())
        );
    }
}
