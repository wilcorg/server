package org.gogame.server.service;

import lombok.RequiredArgsConstructor;
import org.gogame.server.domain.entities.UserFriendInviteEntity;
import org.gogame.server.domain.entities.UserFriendshipEntity;
import org.gogame.server.domain.entities.dto.UserInviteDto;
import org.gogame.server.repositories.UserFriendInviteRepository;
import org.gogame.server.repositories.UserFriendshipRepository;
import org.gogame.server.repositories.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendInviteService {

    private final UserFriendInviteRepository userFriendInviteRepo;
    private final UserRepository userRepo;
    private final UserFriendshipRepository userFriendshipRepo;

    public void setUserFriendInvite(UserInviteDto userInviteDto) {
        var duplicatesList = userFriendInviteRepo
                .findDuplicatingInvites(
                        userInviteDto.getUserSenderId(),
                        userInviteDto.getUserReceiverId());

        if (!duplicatesList.isEmpty()) {
            throw new DuplicateKeyException("Invite already exists");
        }
        var userFriendInviteEntity = UserFriendInviteEntity.builder()
                .userSender(userRepo.findById(userInviteDto.getUserSenderId()).orElseThrow())
                .userReceiver(userRepo.findById(userInviteDto.getUserReceiverId()).orElseThrow())
                .build();

        userFriendInviteRepo.save(userFriendInviteEntity);
    }

    public List<UserInviteDto> fetchUserFriendInvites(Long id) {
        var userInviteEntityList = userFriendInviteRepo.findAllPendingInvites(id);
        List<UserInviteDto> userInviteDtoList = new ArrayList<>();

        for (var inviteEntity : userInviteEntityList) {
            userInviteDtoList.add(UserInviteDto.builder()
                    .userSenderId(inviteEntity.getUserSender().getUserId())
                    .userReceiverId(inviteEntity.getUserReceiver().getUserId())
                    .build()
            );
        }
        return userInviteDtoList;
    }

    public void acceptFriendInvite(UserInviteDto userInviteDto) throws SQLException {
        var userFriendInviteEntity = userFriendInviteRepo
                .findOnePendingInvite(
                        userInviteDto.getUserSenderId(),
                        userInviteDto.getUserReceiverId());

        if (userFriendInviteEntity.isEmpty()) {
            throw new DuplicateKeyException("Invite does not exist");
        }

        try {
            userFriendInviteRepo.delete(userFriendInviteEntity.get());

            userFriendshipRepo.save(UserFriendshipEntity.builder()
                    .userA(userRepo.findById(userInviteDto.getUserSenderId()).orElseThrow())
                    .userB(userRepo.findById(userInviteDto.getUserReceiverId()).orElseThrow())
                    .build());
        } catch (Exception ex) {
            throw new SQLException(ex);
        }
    }

    public void rejectFriendInvite(UserInviteDto userInviteDto) throws SQLException {
        var userFriendInviteEntity = userFriendInviteRepo
                .findOnePendingInvite(
                        userInviteDto.getUserSenderId(),
                        userInviteDto.getUserReceiverId());

        if (userFriendInviteEntity.isEmpty()) {
            throw new DuplicateKeyException("Invite does not exist");
        }

        try {
            userFriendInviteRepo.delete(userFriendInviteEntity.get());
        } catch (Exception ex) {
            throw new SQLException(ex);
        }
    }
}
