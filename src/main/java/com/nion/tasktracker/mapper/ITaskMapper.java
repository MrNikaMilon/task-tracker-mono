package com.nion.tasktracker.mapper;

import com.nion.tasktracker.dto.request.create.CreateTaskRequest;
import com.nion.tasktracker.dto.request.update.UpdateTaskRequest;
import com.nion.tasktracker.dto.request.update.UpdateTaskUserRequest;
import com.nion.tasktracker.dto.response.TaskResponse;
import com.nion.tasktracker.entity.TaskEntity;
import com.nion.tasktracker.entity.TaskUserEntity;
import com.nion.tasktracker.handler.exception.TaskNotFoundException;
import com.nion.tasktracker.handler.exception.TaskUserNotFoundException;
import com.nion.tasktracker.repository.ITaskRepository;
import com.nion.tasktracker.repository.ITaskUserRepository;
import org.mapstruct.*;
import org.springframework.scheduling.config.Task;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ITaskMapper {

    @Mappings({
            @Mapping(target = "taskId", ignore = true),
            @Mapping(target = "taskName", source = "name"),
            @Mapping(target = "taskDescription", source = "description"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "lastEditedAt", ignore = true),
            @Mapping(target = "taskUserEntity", source = "userId", qualifiedByName = "getEntityById")

    })
    TaskEntity toEntity(CreateTaskRequest createTaskRequest, @Context ITaskUserRepository taskUserRepository);
    @Mappings({
            @Mapping(target = "taskId", ignore = true),
            @Mapping(target = "taskName", source = "name"),
            @Mapping(target = "taskDescription", source = "description"),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "lastEditedAt", ignore = true),
            @Mapping(target = "taskUserEntity", source = "userId", qualifiedByName = "getEntityById")
    })
    TaskEntity toEntity(UpdateTaskRequest updateTaskRequest, @Context ITaskUserRepository taskUserRepository);
    @Mappings({
            @Mapping(target = "id", source = "taskId"),
            @Mapping(target = "name", source = "taskName"),
            @Mapping(target = "description", source = "taskDescription"),
            @Mapping(target = "userName", source = "taskUserEntity", qualifiedByName = "getUserName"),
            @Mapping(target = "userEmail", source = "taskUserEntity", qualifiedByName = "getUserEmail"),

    })
    TaskResponse toResponse(TaskEntity taskEntity);

    @Named("getEntityById")
    default TaskUserEntity getEntityById(Long userId, @Context ITaskUserRepository taskUserRepository) {
        return taskUserRepository.findById(userId)
                .orElseThrow(() -> new TaskUserNotFoundException("not found user by id: %d".formatted(userId)));
    }

    @Named("getUserName")
    default String getUserName(TaskUserEntity taskUserEntity) {
        return taskUserEntity.getUserName();
    }

    @Named("getUserEmail")
    default String getUserEmail(TaskUserEntity taskUserEntity) {
        return taskUserEntity.getEmail();
    }
}
