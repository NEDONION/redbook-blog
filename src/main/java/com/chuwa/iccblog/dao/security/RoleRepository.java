package com.chuwa.iccblog.dao.security;

import com.chuwa.iccblog.entity.security.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);
}

