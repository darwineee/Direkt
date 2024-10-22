package com.dd.direkt.shared_kernel.app.mapper;

import com.dd.direkt.shared_kernel.app.dto.LoginResponse;
import com.dd.direkt.shared_kernel.app.dto.SignUpRequest;
import com.dd.direkt.shared_kernel.domain.entity.Account;
import com.dd.direkt.shared_kernel.domain.entity.view.AccountDetail;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@org.mapstruct.Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AuthMapper {
    LoginResponse toLoginResponse(Account account);
    Account toAccount(SignUpRequest request);
    AccountDetail toAccountDetail(Account account);
}
