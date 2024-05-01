package com.example.server.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.server.entity.Car;


public interface CarRepository extends JpaRepository<Car, Integer>{
	

}