package com.example.server.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.server.entity.Rental;
import com.example.server.entity.Car;

import com.example.server.repository.RentalRepository;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;


    public Rental createRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public List<Rental> getAllRental() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Integer id) {
        return rentalRepository.findById(id).orElse(null);
    }

    public Rental updateRental(Integer id, Rental rental) {
        rental.setRentalID(id);
        return rentalRepository.save(rental);
    }

    public void deleteRental(Integer id) {
        rentalRepository.deleteByRentalID(id);
    }
}
