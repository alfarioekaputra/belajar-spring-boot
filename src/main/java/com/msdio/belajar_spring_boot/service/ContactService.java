package com.msdio.belajar_spring_boot.service;

import com.msdio.belajar_spring_boot.entity.Contact;
import com.msdio.belajar_spring_boot.entity.User;
import com.msdio.belajar_spring_boot.model.ContactResponse;
import com.msdio.belajar_spring_boot.model.CreateContactRequest;
import com.msdio.belajar_spring_boot.model.SearchContactRequest;
import com.msdio.belajar_spring_boot.model.UpdateContactRequest;
import com.msdio.belajar_spring_boot.repository.ContactRepository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public ContactResponse create(User user, CreateContactRequest request){
        validationService.validate(request);

        Contact contact = new Contact();
        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());

        Contact save = contactRepository.save(contact);

        return toContactResponse(save);

    }

    @Transactional(readOnly = true)
    public ContactResponse get(User user, UUID id){
        Contact contact = contactRepository.findFirstByUserAndId(user, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        return toContactResponse(contact);
    }

    @Transactional
    public ContactResponse update(User user, UpdateContactRequest request){
        validationService.validate(request);

        Contact contact = contactRepository.findFirstByUserAndId(user, request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact Not Found"));

        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contactRepository.save(contact);

        return toContactResponse(contact);

    }

    @Transactional
    public void delete(User user, UUID contactId){
        Contact contact = contactRepository.findFirstByUserAndId(user, contactId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact Not Found"));

        contactRepository.delete(contact);

    }

    @Transactional(readOnly = true)
    public Page<ContactResponse> search(User user, SearchContactRequest request){
        Specification<Contact> specification = (root, query, builder) -> {
            List<Predicate> precidates = new ArrayList<>();
            precidates.add(builder.equal(root.get("user"), user));
            if(Objects.nonNull(request.getName())){
                precidates.add(builder.or(
                        builder.like(root.get("firstName"), "%"+request.getName()+"%"),
                        builder.like(root.get("lastName"), "%"+request.getName()+"%")
                ));
            }

            if(Objects.nonNull(request.getEmail())){
                precidates.add(builder.or(
                        builder.like(root.get("email"), "%"+request.getEmail()+"%")
                ));
            }

            if(Objects.nonNull(request.getPhone())){
                precidates.add(builder.or(
                        builder.like(root.get("phone"), "%"+request.getPhone()+"%")
                ));
            }

            assert query != null;
            return query.where(precidates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Contact> contacts = contactRepository.findAll(specification, pageable);

        List<ContactResponse> contactResponses = contacts.getContent().stream()
                .map(this::toContactResponse).toList();

        return new PageImpl<>(contactResponses,pageable, contacts.getTotalElements());
    }

    private ContactResponse toContactResponse(Contact contact){
        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .build();
    }
}
