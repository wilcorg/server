package org.gogame.server.service;

import org.gogame.server.domain.entities.UserBioEntity;
import org.gogame.server.repositories.UserBioRepository;
import org.hibernate.JDBCException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserBioService {

    private final UserBioRepository userBioRepo;

    public UserBioService(UserBioRepository userBioRepo) {
        this.userBioRepo = userBioRepo;
    }

    public UserBioEntity getUserBio(Long userId) throws SQLException {

        try {
            var result = userBioRepo.findByUserId(userId);
            return result.orElseThrow(() -> new SQLException("User bio not found"));
        } catch (JDBCException ex) {
            throw new SQLException(ex);
        }
    }

    public void setUserBio(UserBioEntity userBioEntity) throws SQLException {
        try {
            userBioRepo.save(userBioEntity);
        } catch (JDBCException ex) {
            throw new SQLException(ex);
        }
    }
}
