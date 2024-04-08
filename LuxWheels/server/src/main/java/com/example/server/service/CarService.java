package com.example.server.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.server.entity.Car;
import com.example.server.repository.CarRepository;


@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getUserByLicensePlate(Integer lp) {
        return carRepository.findByLicenseplate(lp).orElse(null);
    }

    public Car updateCar(Integer lp, Car car) {
        car.setLicenseplate(lp); //I think this fails due to the lack of getters and setters in Car class
        return carRepository.save(car);
    }

    public void deleteUser(Integer lp) {
        carRepository.deleteByLicenseplate(lp);
    }
}