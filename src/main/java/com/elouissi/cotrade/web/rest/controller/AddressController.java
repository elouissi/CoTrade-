package com.elouissi.cotrade.web.rest.controller;

import com.elouissi.cotrade.domain.Address;

import com.elouissi.cotrade.service.AddressService;
import com.elouissi.cotrade.web.rest.VM.AddressVM;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/V1/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        return ResponseEntity.ok(addressService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable UUID id) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody AddressVM addressVM) {
        Address address = new Address();
        address.setTitle(addressVM.getTitle());



        return ResponseEntity.ok(addressService.save(address));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable UUID id, @RequestBody AddressVM addressVM) {
        Address address = new Address();
        address.setTitle(addressVM.getTitle());



        return ResponseEntity.ok(addressService.update(id, address));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        addressService.delete(id);
        return ResponseEntity.ok().build();
    }
}