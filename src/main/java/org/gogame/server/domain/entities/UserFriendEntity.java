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
@Table(name = "user_friend")
public class UserFriendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friend_id_seq")
    @SequenceGenerator(name = "friend_id_seq", allocationSize = 1)
    @Column(nullable = false, unique = true, name = "friend_id")
    private Long friendId;

    @ManyToOne
    @JoinColumn(name = "user_a_id", referencedColumnName = "user_id")
    private UserEntity userAId;

    @ManyToOne
    @JoinColumn(name = "user_b_id", referencedColumnName = "user_id")
    private UserEntity userBId;
}
