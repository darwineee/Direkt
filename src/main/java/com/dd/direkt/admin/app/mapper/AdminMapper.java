package com.dd.direkt.admin.app.mapper;

import com.dd.direkt.admin.app.dto.AdminLoginResponse;
import com.dd.direkt.admin.app.dto.CustomerInfoResponse;
import com.dd.direkt.shared_kernel.app.dto.LoginResponse;
import com.dd.direkt.shared_kernel.domain.entity.Account;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@org.mapstruct.Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AdminMapper {
    CustomerInfoResponse toCustomerInfoRsp(Account account);

    AdminLoginResponse toAdminLoginRsp(LoginResponse loginResponse);
}
