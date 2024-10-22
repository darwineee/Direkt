package com.dd.direkt.user.app.mapper;

import com.dd.direkt.shared_kernel.app.dto.LoginResponse;
import com.dd.direkt.shared_kernel.domain.entity.Account;
import com.dd.direkt.user.app.dto.UpdateUserRequest;
import com.dd.direkt.user.app.dto.UserInfoResponse;
import com.dd.direkt.user.app.dto.UserLoginResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    UserInfoResponse toResponse(Account account);
    UserLoginResponse toResponse(LoginResponse response);

    void updateEntity(@MappingTarget Account account, UpdateUserRequest request);
}
