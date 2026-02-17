package com.custom.Billing.Software.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@JsonIgnoreProperties(ignoreUnknown = false)
public class UserRequestDto {

    private Long userId;

    private String firstName;

    private String lastName;

    private String userName;

    private String password;

    private String mobileNumber;

    private String address;

    private String emailId;

    private String role;

//    private LocalDateTime CreatedAt;
//
//    private LocalDateTime UpdatedAt;


}
