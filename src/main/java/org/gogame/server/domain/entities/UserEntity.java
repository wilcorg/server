package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_list")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", allocationSize = 1)
    @Column(nullable = false, unique = true)
    private Long user_id;


    @Column(length=64, nullable = false, unique = true)
    private String nickname;

    @Column(length=64, nullable = false)
    private String password_hash;

    @Column(length=128, nullable = false, unique = true)
    private String email;

    private java.sql.Timestamp join_date;
}
