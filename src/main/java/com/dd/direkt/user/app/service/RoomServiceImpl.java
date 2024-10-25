package com.dd.direkt.user.app.service;

import com.dd.direkt.user.app.dto.CreateRoomRequest;
import com.dd.direkt.user.app.mapper.RoomMapper;
import com.dd.direkt.user.domain.entity.Room;
import com.dd.direkt.user.domain.entity.RoomMember;
import com.dd.direkt.user.domain.repository.RoomMemberRepository;
import com.dd.direkt.user.domain.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomMemberRepository roomMemberRepository;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public boolean isRoomMember(long roomId, long userId) {
        return roomMemberRepository
                .findByRoomIdAndMemberId(roomId, userId)
                .isPresent();
    }

    @Transactional
    @Override
    public void joinRoom(long roomId, long userId) {
        roomMemberRepository.save(
                RoomMember.builder()
                        .roomId(roomId)
                        .memberId(userId)
                        .build()
        );
    }

    @Transactional
    @Override
    public void leaveRoom(long roomId, long userId) {
        roomMemberRepository.deleteByRoomIdAndMemberId(roomId, userId);
    }

    @Transactional
    @Override
    public Room createRoom(CreateRoomRequest request, long userId) {
        var room = roomMapper.toRoomEntity(request);
        room.setOwnerId(userId);
        var savedRoom = roomRepository.save(room);
        request.getMembers().forEach(member -> joinRoom(savedRoom.getId(), member));
        return savedRoom;
    }
}
