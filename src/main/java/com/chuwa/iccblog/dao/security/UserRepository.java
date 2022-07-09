package com.chuwa.iccblog.dao.security;

import com.chuwa.iccblog.entity.security.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	/**
	 * @param email
	 * @return Optional
	 */
	Optional<User> findByEmail(String email);

	Optional<User> findByAccountOrEmail(String account, String email);

	Optional<User> findByAccount(String username);

	Boolean existsByAccount(String user);

	Boolean existsByEmail(String email);
}
