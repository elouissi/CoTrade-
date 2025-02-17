package com.elouissi.cotrade.web.rest.controller;

import com.elouissi.cotrade.domain.City;
import com.elouissi.cotrade.service.CityService;
import com.elouissi.cotrade.web.rest.VM.CityVM;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/V1/cities")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        return ResponseEntity.ok(cityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCity(@PathVariable UUID id) {
        return ResponseEntity.ok(cityService.findById(id));
    }

    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody CityVM cityVM) {
        City city = new City();
        city.setName(cityVM.getName());
        return ResponseEntity.ok(cityService.save(city));
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable UUID id, @RequestBody CityVM cityVM) {
        City city = new City();
        city.setName(cityVM.getName());
        return ResponseEntity.ok(cityService.update(id, city));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable UUID id) {
        cityService.delete(id);
        return ResponseEntity.ok().build();
    }
}