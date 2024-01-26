package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "message")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "message_id", nullable = false, unique = true)
    private Long messageId;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private GameEntity game;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private UserEntity author;

    @Column(nullable = false, length = 2048)
    private String content;
}
