package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_bio")
public class UserBioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_bio_id", nullable = false, unique = true)
    private Long userBioId;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private UserEntity user;

    @Builder.Default
    @Column(length = 2048)
    private String bio = "";
}