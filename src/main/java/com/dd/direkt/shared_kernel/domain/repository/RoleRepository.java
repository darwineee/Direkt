package com.dd.direkt.shared_kernel.domain.repository;

import com.dd.direkt.shared_kernel.domain.entity.Role;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface RoleRepository extends ListCrudRepository<Role, Long> {
    List<Role> findByRoleIn(List<String> role);
}