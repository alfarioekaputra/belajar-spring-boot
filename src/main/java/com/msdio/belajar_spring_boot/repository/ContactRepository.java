package com.msdio.belajar_spring_boot.repository;

import com.msdio.belajar_spring_boot.entity.Contact;
import com.msdio.belajar_spring_boot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID>, JpaSpecificationExecutor<Contact
        > {

    Optional<Contact> findFirstByUserAndId(User user, UUID id);
}
