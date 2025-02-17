package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.City;
import com.elouissi.cotrade.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public City findById(UUID id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found"));
    }

    public City save(City city) {
        return cityRepository.save(city);
    }

    public City update(UUID id, City city) {
        City existingCity = findById(id);
        existingCity.setName(city.getName());
        return cityRepository.save(existingCity);
    }

    public void delete(UUID id) {
        cityRepository.deleteById(id);
    }
}