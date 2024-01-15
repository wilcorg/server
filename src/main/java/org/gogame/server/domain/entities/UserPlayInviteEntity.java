package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_play_invite")
public class UserPlayInviteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_play_invite_id_seq")
    @SequenceGenerator(name = "user_play_invite_id_seq", allocationSize = 1)
    @Column(nullable = false, unique = true, name = "user_play_invite_id")
    private Long userPlayInviteId;

    @ManyToOne
    @JoinColumn(name = "user_sender_id", referencedColumnName = "user_id")
    private UserEntity userSenderId;

    @ManyToOne
    @JoinColumn(name = "user_receiver_id", referencedColumnName = "user_id")
    private UserEntity userReceiverId;
}
