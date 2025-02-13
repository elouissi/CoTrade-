package com.elouissi.cotrade.web.rest.VM;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyClockResponse {
    private String accessToken;
    private String refreshToken;
}
