package org.gogame.server.controllers;

import lombok.RequiredArgsConstructor;
import org.gogame.server.domain.entities.dto.user.UserInviteDto;
import org.gogame.server.repositories.UserRepository;
import org.gogame.server.service.FriendInviteService;
import org.gogame.server.service.PermissionValidatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friend/invite")
@RequiredArgsConstructor
public class FriendInviteController {

    private final FriendInviteService friendInviteService;

    private final PermissionValidatorService validatorService;
    private final UserRepository userRepo;

    @PostMapping("/send")
    public ResponseEntity<?> sendFriendInvite(
            @RequestBody UserInviteDto request,
            @RequestHeader("Authorization") String token
    ) {
        if (!validatorService.validateUserId(request.getUserSenderId(), token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (userRepo.findById(request.getUserSenderId()).isEmpty() ||
                userRepo.findById(request.getUserReceiverId()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            friendInviteService.setUserFriendInvite(request);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<List<UserInviteDto>> fetchFriendInvite(
            @PathVariable Long id,
            @RequestHeader("Authorization") String token
    ) {
        if (!validatorService.validateUserId(id, token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<UserInviteDto> userInviteDtoList;
        try {
            userInviteDtoList = friendInviteService.fetchUserFriendInvites(id);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(userInviteDtoList, HttpStatus.OK);
    }

    @PostMapping("/accept")
    public ResponseEntity<?> acceptFriendInvite(
            @RequestBody UserInviteDto request,
            @RequestHeader("Authorization") String token
    ) {
        if (!validatorService.validateUserId(request.getUserReceiverId(), token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            friendInviteService.acceptFriendInvite(request);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping("/reject")
    public ResponseEntity<?> rejectFriendInvite(
            @RequestBody UserInviteDto request,
            @RequestHeader("Authorization") String token
    ) {
        if (!validatorService.validateUserId(request.getUserReceiverId(), token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            friendInviteService.rejectFriendInvite(request);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
