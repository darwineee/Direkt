package com.dd.direkt.management.app.service;

import com.dd.direkt.customer.domain.exception.ClientNotFoundException;
import com.dd.direkt.customer.domain.repository.IClientRepository;
import com.dd.direkt.management.app.dto.RequestCreateUser;
import com.dd.direkt.management.app.dto.RequestUpdateUser;
import com.dd.direkt.management.app.dto.ResponseGetUser;
import com.dd.direkt.management.app.mapper.IUserMapper;
import com.dd.direkt.management.domain.entity.User;
import com.dd.direkt.management.domain.exception.UserNotFoundException;
import com.dd.direkt.management.domain.exception.UserIdentityExisted;
import com.dd.direkt.management.domain.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IClientRepository clientRepository;
    private final IUserMapper mapper;

    @Override
    public ResponseGetUser find(long id) {
        return userRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Page<ResponseGetUser> findAll(long clientId, String name, String email, Pageable pageable) {
        Page<User> page = userRepository.findAll(clientId, email, name, pageable);
        return new PageImpl<>(
                page.getContent().stream().map(mapper::toResponse).collect(Collectors.toList()),
                pageable,
                page.getTotalElements()
        );
    }

    @Transactional
    @Override
    public ResponseGetUser create(RequestCreateUser request, long clientId) {
        if (!clientRepository.existsById(clientId)) throw new ClientNotFoundException();
        if (userRepository.existsByEmail(request.email())) throw new UserIdentityExisted();
        User user = mapper.toEntity(request);
        user.setClientId(clientId);
        return mapper.toResponse(userRepository.save(user));
    }

    @Transactional
    @Override
    public ResponseGetUser update(RequestUpdateUser request, long clientId, long userId) {
        if (!clientRepository.existsById(clientId)) throw new ClientNotFoundException();
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        mapper.updateEntity(user, request);
        return mapper.toResponse(userRepository.save(user));
    }

    @Transactional
    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
