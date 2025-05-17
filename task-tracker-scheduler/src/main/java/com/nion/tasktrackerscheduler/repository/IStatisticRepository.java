package com.nion.tasktrackerscheduler.repository;

import com.nion.tasktrackerscheduler.entity.StatisticUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface IStatisticRepository extends JpaRepository<StatisticUserEntity, UUID> {
    List<StatisticUserEntity> findAllByCreatedDate(LocalDate date);
}
