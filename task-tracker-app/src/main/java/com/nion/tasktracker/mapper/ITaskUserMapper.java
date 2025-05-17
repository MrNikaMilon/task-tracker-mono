package com.nion.tasktracker.mapper;

import com.nion.tasktracker.dto.request.create.RegistrationRequest;
import com.nion.tasktracker.dto.response.RegistrationResponse;
import com.nion.tasktracker.entity.TaskUserEntity;
import com.nion.tasktracker.entity.TokenEntity;
import com.nion.tasktracker.repository.ITokenRepository;
import org.mapstruct.*;

import java.time.LocalDateTime;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ITaskUserMapper {

    @Mappings({
            @Mapping(target = "userId", ignore = true),
            @Mapping(target ="createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "lastActivity", ignore = true),
            @Mapping(target = "role", ignore = true),
            @Mapping(target = "tasks", ignore = true),
    })
    TaskUserEntity toEntity(RegistrationRequest taskUserCreateRequest);

    @Mappings({
          @Mapping(target = "tokens", source = "userId", qualifiedByName = "getTokensByUser"),
    })
    RegistrationResponse toRegistrationResponse(TaskUserEntity taskUserEntity, @Context ITokenRepository tokenRepository);

    @Named("isUserActive")
    default boolean isUserActive(LocalDateTime lasDateTimeActivity) {
        return lasDateTimeActivity.isAfter(LocalDateTime.now().minusDays(1));
    }

    @Named("getTokensByUser")
    default String[] getTokensByUser(Long userId, @Context ITokenRepository tokenRepository){
        return tokenRepository.findAllTokensByUser(userId).stream()
                .map(TokenEntity::getAccessToken)
                .toArray(String[]::new);
    }
}
