package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="token")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long tokenId;

    private String token;

    private Boolean revoked;

    private Boolean expired;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private UserEntity user;
}
