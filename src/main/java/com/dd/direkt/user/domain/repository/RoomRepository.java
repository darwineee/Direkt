package com.dd.direkt.user.domain.repository;

import com.dd.direkt.user.domain.entity.Room;
import org.springframework.data.repository.ListCrudRepository;

public interface RoomRepository extends ListCrudRepository<Room, Long> {
}
