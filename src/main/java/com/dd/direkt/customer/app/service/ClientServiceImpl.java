package com.dd.direkt.customer.app.service;

import com.dd.direkt.customer.app.dto.RequestCreateClient;
import com.dd.direkt.customer.app.dto.RequestUpdateClient;
import com.dd.direkt.customer.app.dto.ResponseGetClient;
import com.dd.direkt.customer.app.mapper.ClientMapper;
import com.dd.direkt.customer.domain.entity.Client;
import com.dd.direkt.customer.domain.exception.ClientNotFoundException;
import com.dd.direkt.customer.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
class ClientServiceImpl implements ClientService {
    private final ClientRepository repository;
    private final ClientMapper mapper;

    @Override
    public ResponseGetClient find(long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public Page<ResponseGetClient> findAll(Pageable pageable, String name) {
        Page<Client> page = repository.findByNameContainingIgnoreCase(pageable, name);
        return new PageImpl<>(
                page.getContent().stream().map(mapper::toResponse).collect(Collectors.toList()),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public ResponseGetClient create(RequestCreateClient request) {
        Client client = repository.save(mapper.toEntity(request));
        return mapper.toResponse(client);
    }

    @Override
    public ResponseGetClient update(RequestUpdateClient request, long id) {
        Client client = repository.findById(id).orElseThrow(ClientNotFoundException::new);
        mapper.updateEntity(client, request);
        return mapper.toResponse(repository.save(client));
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }
}
