package com.msdio.belajar_spring_boot.service;

import com.msdio.belajar_spring_boot.entity.Address;
import com.msdio.belajar_spring_boot.entity.Contact;
import com.msdio.belajar_spring_boot.entity.User;
import com.msdio.belajar_spring_boot.model.AddressResponse;
import com.msdio.belajar_spring_boot.model.ContactResponse;
import com.msdio.belajar_spring_boot.model.CreateAddressRequest;
import com.msdio.belajar_spring_boot.repository.AddressRepository;
import com.msdio.belajar_spring_boot.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AddressService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public AddressResponse create(User user, CreateAddressRequest request){
        validationService.validate(request);

        Contact contact = contactRepository.findFirstByUserAndId(user, request.getContactId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact is not found"));


        Address address = new Address();
        address.setContact(contact);
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());
        addressRepository.save(address);

        return toAddressResponse(address);
    }

    private AddressResponse toAddressResponse(Address address){
        return AddressResponse.builder()
                .id(address.getId())
                .contactId(address.getContact().getId())
                .street(address.getStreet())
                .city(address.getCity())
                .province(address.getProvince())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .build();
    }
}
