package org.gogame.server.mappers.impl;

import org.gogame.server.domain.entities.GameJournalEntity;
import org.gogame.server.domain.entities.dto.game.GameJournalDto;
import org.gogame.server.mappers.Mapper;
import org.gogame.server.repositories.GameRepository;
import org.gogame.server.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class GameJournalMapper implements Mapper<GameJournalEntity, GameJournalDto> {

    private final UserRepository userRepo;
    private final GameRepository gameRepo;

    public GameJournalMapper(UserRepository userRepo, GameRepository gameRepo) {
        this.userRepo = userRepo;
        this.gameRepo = gameRepo;
    }


    @Override
    public GameJournalDto mapTo(GameJournalEntity gameJournalEntity) {
        return GameJournalDto.builder()
                .gameId(gameJournalEntity.getGame().getGameId())
                .authorId(gameJournalEntity.getAuthor().getUserId())
                .turnX(gameJournalEntity.getTurnX())
                .turnY(gameJournalEntity.getTurnY())
                .action(gameJournalEntity.getAction())
                .build();
    }

    @Override
    public GameJournalEntity mapFrom(GameJournalDto gameJournalDto) {
        return GameJournalEntity.builder()
                .author(userRepo.findById(gameJournalDto.getAuthorId()).orElseThrow())
                .game(gameRepo.findById(gameJournalDto.getGameId()).orElseThrow())
                .turnX(gameJournalDto.getTurnX())
                .turnY(gameJournalDto.getTurnY())
                .action(gameJournalDto.getAction())
                .turnDate(new Timestamp(new Date().getTime()))
                .build();
    }
}
