package com.elouissi.cotrade.web.rest.VM;

import lombok.Data;
import java.util.UUID;

@Data
public class AddressVM {
    private UUID id;
    private String title;
    private UUID cityId;
}