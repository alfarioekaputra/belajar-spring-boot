package com.msdio.belajar_spring_boot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {

    private UUID id;

    private UUID contactId;

    private String street;

    private String city;

    private String province;

    private String country;

    private String postalCode;
}
