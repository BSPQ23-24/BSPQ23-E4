package com.example.server.repository;

import java.util.List;
import java.util.Optional;

import com.example.server.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.server.entity.Rental;

public interface RentalRepository extends JpaRepository<Rental, Integer>{

	List<Rental> findRentalsByCar(Car car);
}
