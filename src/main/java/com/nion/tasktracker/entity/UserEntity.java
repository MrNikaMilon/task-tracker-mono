package com.nion.tasktracker.entity;

import com.nion.tasktracker.entity.dictionary.UserRole;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)
public record UserEntity(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "user_id")
        long userId,
        String userName,
        String email,
        @Enumerated(EnumType.STRING)
        UserRole role,
        @CreatedDate
        LocalDateTime createdAt,
        @LastModifiedBy
        LocalDateTime updatedAt,
        LocalDateTime lastActivity
) {
}
