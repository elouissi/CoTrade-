package com.elouissi.cotrade.service;


import com.elouissi.cotrade.domain.Address;
import com.elouissi.cotrade.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final CityService cityService;

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public List<Address> findByCityId(UUID cityId) {
        return addressRepository.findByCityId(cityId);
    }

    public Address findById(UUID id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
    }

    public Address save(Address address) {
        address.setCity(cityService.findById(address.getCity().getId()));
        return addressRepository.save(address);
    }

    public Address update(UUID id, Address address) {
        Address existingAddress = findById(id);
        existingAddress.setTitle(address.getTitle());
        existingAddress.setCity(cityService.findById(address.getCity().getId()));
        return addressRepository.save(existingAddress);
    }

    public void delete(UUID id) {
        addressRepository.deleteById(id);
    }
}