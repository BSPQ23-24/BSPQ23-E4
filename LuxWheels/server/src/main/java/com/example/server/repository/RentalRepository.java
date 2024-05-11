package com.example.server.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.server.entity.Rental;

public interface RentalRepository extends JpaRepository<Rental, Integer>{

	Optional<Rental> findById(Integer rentalID);
	//TODO List<Rental> getUserRentals(Integer userId);
	void deleteById(Integer rentalID);
}
