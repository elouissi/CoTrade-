package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.Address;
import com.elouissi.cotrade.repository.AddressRepository;
import com.elouissi.cotrade.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    private Address mockAddress;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockAddress = new Address();
        mockAddress.setId(UUID.randomUUID());
        mockAddress.setTitle("Home");
    }

    @Test
    void findById_ShouldReturnAddress() {
        when(addressRepository.findById(any(UUID.class))).thenReturn(Optional.of(mockAddress));

        Address result = addressService.findById(mockAddress.getId());

        assertEquals(mockAddress.getTitle(), result.getTitle());
        verify(addressRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void save_ShouldPersistAddress() {
        when(addressRepository.save(any(Address.class))).thenReturn(mockAddress);

        Address savedAddress = addressService.save(mockAddress);

        assertNotNull(savedAddress.getId());
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void update_ShouldModifyAddress() {
        Address updatedAddress = new Address();
        updatedAddress.setTitle("Office");

        when(addressRepository.findById(any(UUID.class))).thenReturn(Optional.of(mockAddress));
        when(addressRepository.save(any(Address.class))).thenReturn(mockAddress);

        Address result = addressService.update(UUID.randomUUID(), updatedAddress);

        assertEquals("Office", result.getTitle());
        verify(addressRepository, times(1)).save(any(Address.class));
    }
}