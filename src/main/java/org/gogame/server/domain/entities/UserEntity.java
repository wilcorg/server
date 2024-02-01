package org.gogame.server.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.gogame.server.domain.entities.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_info")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(length = 64, nullable = false, unique = true)
    private String nickname;

    @Column(name = "password_hash", length = 100, nullable = false)
    private String passwordHash;

    @Column(length = 128, nullable = false, unique = true)
    private String email;

    @Column(name = "join_date")
    private Timestamp joinDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
