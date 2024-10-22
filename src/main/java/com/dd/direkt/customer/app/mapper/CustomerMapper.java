package com.dd.direkt.customer.app.mapper;

import com.dd.direkt.customer.app.dto.*;
import com.dd.direkt.shared_kernel.app.dto.LoginResponse;
import com.dd.direkt.shared_kernel.app.dto.SignUpRequest;
import com.dd.direkt.shared_kernel.domain.entity.Account;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@org.mapstruct.Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CustomerMapper {
    CustomerInfoResponse toGetCustomerRsp(Account account);

    CustomerLoginResponse toCustomerLoginRsp(LoginResponse rsp);

    SignUpRequest toSignUpRequest(CustomerSignUpRequest request);

    SignUpRequest toSignUpRequest(CreateUserRequest request);

    void updateAccountEntity(@MappingTarget Account account, UpdateCustomerRequest request);

    UserInfoResponse toUserInfoRsp(Account account);
}
