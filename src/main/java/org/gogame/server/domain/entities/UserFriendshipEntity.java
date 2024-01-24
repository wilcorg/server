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
@Table(name = "user_friendship")
public class UserFriendshipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "friendship_id", nullable = false, unique = true)
    private Long friendshipId;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "user_a_id")
    private UserEntity userA;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "user_b_id")
    private UserEntity userB;
}
