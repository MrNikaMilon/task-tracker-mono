package com.nion.tasktracker.mapper;

import com.nion.tasktracker.dto.request.create.CreateTaskUserRequest;
import com.nion.tasktracker.dto.request.update.UpdateTaskUserRequest;
import com.nion.tasktracker.dto.response.TaskUserResponse;
import com.nion.tasktracker.entity.TaskUserEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ITaskUserMapper {

    @Mappings({
            @Mapping(target = "userId", ignore = true),
            @Mapping(target ="createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "lastActivity", ignore = true),
            @Mapping(target = "role", ignore = true)
    })
    TaskUserEntity toEntity(CreateTaskUserRequest taskUserCreateRequest);
    @Mappings({
            @Mapping(target = "userId", ignore = true),
            @Mapping(target ="createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "lastActivity", ignore = true),
            @Mapping(target = "role", ignore = true)
    })
    TaskUserEntity toEntity(UpdateTaskUserRequest taskUserUpdateRequest);
    @Mappings({
            @Mapping(target = "id", source = "userId"),
            @Mapping(target ="active", source = "lastActivity", qualifiedByName = "isUserActive"),
            @Mapping(target ="name", source = "userName")
    })
    TaskUserResponse toResponse(TaskUserEntity taskUserEntity);

    @Named("isUserActive")
    default boolean isUserActive(LocalDateTime lasDateTimeActivity) {
        return lasDateTimeActivity.isAfter(LocalDateTime.now().minusDays(1));
    }
}
