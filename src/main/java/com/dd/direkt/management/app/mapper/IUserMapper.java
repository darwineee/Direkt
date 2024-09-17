package com.dd.direkt.management.app.mapper;

import com.dd.direkt.management.app.dto.RequestCreateUser;
import com.dd.direkt.management.app.dto.RequestUpdateUser;
import com.dd.direkt.management.app.dto.ResponseGetUser;
import com.dd.direkt.management.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IUserMapper {
    ResponseGetUser toResponse(User user);

    User toEntity(RequestCreateUser request);

    void updateEntity(@MappingTarget User user, RequestUpdateUser request);
}
