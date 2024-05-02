package com.example.server.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.server.entity.Rental;

public interface RentalRepository extends JpaRepository<Rental, Integer>{

	Optional<Rental> findById(Integer rentalID);

	void deleteByRentalID(Integer rentalID);
}
