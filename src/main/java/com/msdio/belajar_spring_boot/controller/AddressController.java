package com.msdio.belajar_spring_boot.controller;

import com.msdio.belajar_spring_boot.entity.User;
import com.msdio.belajar_spring_boot.model.AddressResponse;
import com.msdio.belajar_spring_boot.model.CreateAddressRequest;
import com.msdio.belajar_spring_boot.model.WebResponse;
import com.msdio.belajar_spring_boot.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping(
            path = "/api/contacts/{contactId}/addresses"
    )
    public WebResponse<AddressResponse> create(User user, @RequestBody CreateAddressRequest request, @PathVariable("contactId") UUID contactId){
        request.setContactId(contactId);
        AddressResponse addressResponse = addressService.create(user, request);

        return WebResponse.<AddressResponse>builder().data(addressResponse).build();
    }
}
