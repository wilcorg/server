package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

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
    @Column(nullable = false, unique = true, name = "user_id")
    private Long userId;


    @Column(length=64, nullable = false, unique = true)
    private String nickname;

    @Column(length=64, nullable = false, name = "password_hash_sha512")
    private String passwordHashSha512;

    @Column(length=128, nullable = false, unique = true)
    private String email;

    @Column(name = "join_date")
    private Timestamp joinDate;
}
