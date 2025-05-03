package com.nion.tasktracker.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Table(name = "tokens") @EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class TokenEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "access_token",  nullable = false)
    private String accessToken;

    @Column(name = "refresh_token",  nullable = false)
    private String refreshToken;

    @Column(name = "is_expired", nullable = false)
    private boolean isExpired;

    @Column(name = "is_revoked", nullable = false)
    private boolean isRevoked;

    @CreatedDate @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    private TaskUserEntity taskUserEntity;
}
