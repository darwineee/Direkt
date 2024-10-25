package com.dd.direkt.user.app.mapper;

import com.dd.direkt.user.app.dto.SendTextMsgRequest;
import com.dd.direkt.user.domain.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MsgMapper {
    Message toMsgEntity(SendTextMsgRequest request);
}
