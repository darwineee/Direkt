package com.dd.direkt.customer.app.mapper;

import com.dd.direkt.customer.app.dto.RequestCreateClient;
import com.dd.direkt.customer.app.dto.RequestUpdateClient;
import com.dd.direkt.customer.app.dto.ResponseGetClient;
import com.dd.direkt.customer.domain.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClientMapper {
    ResponseGetClient toResponse(Client client);

    Client toEntity(RequestCreateClient request);

    void updateEntity(@MappingTarget Client client, RequestUpdateClient request);
}
