package com.dd.direkt.user.domain.repository;

import com.dd.direkt.user.domain.entity.Message;
import org.springframework.data.repository.ListCrudRepository;

public interface MessageRepository extends ListCrudRepository<Message, String> {
}
