package com.example.server.repository;

import java.util.List;
import java.util.Optional;

import com.example.server.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.server.entity.Rental;
import org.springframework.data.jpa.repository.Query;

public interface RentalRepository extends JpaRepository<Rental, Integer>{

	Optional<Rental> findById(Integer rentalID);

	void deleteById(Integer rentalID);

	@Query("SELECT r.car FROM Rental r WHERE r.user.email = :email")
	List<Car> findAllCarsByUserEmail(String email);
	List<Rental> findRentalsByCar(Car car);
}
