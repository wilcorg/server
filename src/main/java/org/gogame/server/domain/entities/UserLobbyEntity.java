package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gogame.server.domain.entities.enums.UserLobbyState;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_lobby")
public class UserLobbyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_lobby_id", nullable = false, unique = true)
    private Long userLobbyId;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_lobby_state", length = 10, nullable = false)
    private UserLobbyState userLobbyState;
}
