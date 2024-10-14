package com.dd.direkt.management.app.service;

import com.dd.direkt.customer.domain.exception.ClientNotFoundException;
import com.dd.direkt.customer.domain.repository.ClientRepository;
import com.dd.direkt.management.app.dto.RequestCreateUser;
import com.dd.direkt.management.app.dto.RequestUpdateUser;
import com.dd.direkt.management.app.dto.ResponseGetUser;
import com.dd.direkt.management.app.mapper.UserMapper;
import com.dd.direkt.management.domain.entity.User;
import com.dd.direkt.management.domain.exception.UserNotFoundException;
import com.dd.direkt.management.domain.exception.UserIdentityExisted;
import com.dd.direkt.management.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final UserMapper mapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ResponseGetUser find(long id) {
        return userRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Page<ResponseGetUser> findAll(long clientId, String name, String email, Pageable pageable) {
        Page<User> page = userRepository.findAllPaging(clientId, email, name, pageable);
        return new PageImpl<>(
                page.getContent().stream().map(mapper::toResponse).toList(),
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
