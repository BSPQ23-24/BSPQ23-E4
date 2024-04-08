package com.example.server.repository;
import java.util.Optional;

import com.example.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findById(Integer id);

	void deleteById(Integer id);

}
