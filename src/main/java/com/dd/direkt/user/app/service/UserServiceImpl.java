package com.dd.direkt.user.app.service;

import com.dd.direkt.shared_kernel.domain.entity.Account;
import com.dd.direkt.user.app.dto.UpdateUserRequest;
import com.dd.direkt.user.app.dto.UserInfoResponse;
import com.dd.direkt.user.app.mapper.UserMapper;
import com.dd.direkt.user.domain.exception.UserNotFound;
import com.dd.direkt.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public UserInfoResponse find(long id) {
        return userRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(UserNotFound::new);
    }

    @Transactional
    @Override
    public UserInfoResponse update(UpdateUserRequest request, long userId) {
        Account account = userRepository.findById(userId).orElseThrow(UserNotFound::new);
        mapper.updateEntity(account, request);
        return mapper.toResponse(userRepository.save(account));
    }
}