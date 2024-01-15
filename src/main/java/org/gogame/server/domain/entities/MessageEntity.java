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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turn_id_seq")
    @SequenceGenerator(name = "turn_id_seq", allocationSize = 1)
    @Column(nullable = false, unique = true, name = "message_id")
    private Long messageId;

    @Column(name = "game_id")
    private Long gameId;

    @ManyToOne
    @JoinColumn(name = "user_rx_id", referencedColumnName = "user_id")
    private UserEntity userRx;

    @ManyToOne
    @JoinColumn(name = "user_tx_id", referencedColumnName = "user_id")
    private UserEntity userTx;

    @Column(nullable = false, length = 2048)
    private String text;
}
