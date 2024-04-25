package com.example.server.repository;
import java.util.Optional;

import com.example.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);

	void deleteByEmail(String email);

}
