package com.dd.direkt.user.domain.repository;

import com.dd.direkt.user.domain.entity.RoomMember;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface RoomMemberRepository extends ListCrudRepository<RoomMember, Long> {
    Optional<RoomMember> findByRoomIdAndMemberId(long roomId, long memberId);
    void deleteByRoomIdAndMemberId(long roomId, long memberId);
}
