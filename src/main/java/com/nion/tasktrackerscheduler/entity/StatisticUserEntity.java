package com.nion.tasktrackerscheduler.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Entity @Table(name = "statistic_table")
@EntityListeners(AuditingEntityListener.class) @NoArgsConstructor @Data @AllArgsConstructor
public class StatisticUserEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID statisticId;
    @Column(nullable = false) private String email;
    private long uncompletedTask;
    private long completedTask;
    private long allTask;
    @CreatedDate private LocalDate createdDate;
}