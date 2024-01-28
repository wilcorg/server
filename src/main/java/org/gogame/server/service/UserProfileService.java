package org.gogame.server.service;

import org.gogame.server.domain.entities.UserEntity;
import org.gogame.server.repositories.UserRepository;
import org.hibernate.JDBCException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserProfileService {

    private final UserRepository userRepo;

    public UserProfileService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserEntity getUserInfo(Long userId) throws SQLException {

        try {
            var result = userRepo.findById(userId);
            return result.orElseThrow(() -> new SQLException("User not found"));
        } catch (JDBCException ex) {
            throw new SQLException(ex);
        }
    }
}
