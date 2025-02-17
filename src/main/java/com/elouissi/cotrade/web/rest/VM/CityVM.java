package com.elouissi.cotrade.web.rest.VM;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class CityVM {
    private UUID id;
    private String name;
    private List<AddressVM> addresses;
}