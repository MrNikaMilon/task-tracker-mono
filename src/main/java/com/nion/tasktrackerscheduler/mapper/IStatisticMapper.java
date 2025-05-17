package com.nion.tasktrackerscheduler.mapper;


import com.nion.tasktrackerscheduler.dto.response.StatisticResponse;
import com.nion.tasktrackerscheduler.dto.message.from.RabbitStatisticMessage;
import com.nion.tasktrackerscheduler.dto.message.to.RabbitStatisticNotifyMessage;
import com.nion.tasktrackerscheduler.entity.StatisticUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface IStatisticMapper {

    @Mappings({
            @Mapping(target = "statisticId", ignore = true),
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "uncompletedTask", target = "uncompletedTask"),
            @Mapping(source = "completedTask", target = "completedTask"),
            @Mapping(source = "allTask", target = "allTask"),
    })
    StatisticUserEntity toEntity(RabbitStatisticMessage message);

    RabbitStatisticNotifyMessage toNotifyResponse(StatisticUserEntity entity);

    StatisticResponse toResponse(StatisticUserEntity entity);


}
