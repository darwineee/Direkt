package com.dd.direkt.user.app.mapper;

import com.dd.direkt.user.app.dto.CreateRoomRequest;
import com.dd.direkt.user.app.dto.CreateRoomResponse;
import com.dd.direkt.user.app.dto.RoomInfoResponse;
import com.dd.direkt.user.domain.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RoomMapper {
    Room toRoomEntity(CreateRoomRequest request);
    CreateRoomResponse toCreateRoomResponse(Room room);
    RoomInfoResponse toRoomInfoResponse(Room room);
}
