package com.custom.Billing.Software.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = false)
public class LoginRequestDto {

    private String userName;
    private String password;
}
