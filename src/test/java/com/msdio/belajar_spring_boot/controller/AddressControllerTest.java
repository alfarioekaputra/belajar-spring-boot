package com.msdio.belajar_spring_boot.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msdio.belajar_spring_boot.entity.Contact;
import com.msdio.belajar_spring_boot.entity.User;
import com.msdio.belajar_spring_boot.model.*;
import com.msdio.belajar_spring_boot.repository.AddressRepository;
import com.msdio.belajar_spring_boot.repository.ContactRepository;
import com.msdio.belajar_spring_boot.repository.UserRepository;
import com.msdio.belajar_spring_boot.security.BCrypt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected AddressRepository addressRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ContactRepository contactRepository;

    @Autowired
    protected ObjectMapper objectMapper;

    public UUID idContact;

    @BeforeEach
    void setUp(){
        addressRepository.deleteAll();
        contactRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        user.setName("Test");
        user.setToken("test");
        user.setTokenExpiredAt(System.currentTimeMillis() + 10000000000L);
        userRepository.save(user);

        Contact contact = new Contact();
        contact.setId(idContact);
        contact.setUser(user);
        contact.setFirstName("Mita");
        contact.setLastName("Yulia");
        contact.setEmail("mitayulia@mail.com");
        contact.setPhone("08123456");
        Contact contactData = contactRepository.save(contact);
        idContact = contactData.getId();
    }

    @Test
    void testCreateAddressSuccess() throws Exception{
        CreateAddressRequest addressRequest = new CreateAddressRequest();
        addressRequest.setContactId(idContact);
        addressRequest.setStreet("Jalan Sudirman");
        addressRequest.setCity("Jakarta Pusat");
        addressRequest.setProvince("DKI Jakarta");
        addressRequest.setCountry("Indonesia");
        addressRequest.setPostalCode("12345");

        mockMvc.perform(
                post("/api/contacts/" + idContact + "/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressRequest))
                        .header("X-API-TOKEN", "test")

        ).andExpectAll(
                status().isOk()

        ).andDo(result -> {
            WebResponse<AddressResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals("Jalan Sudirman", response.getData().getStreet());
            assertEquals("Jakarta Pusat", response.getData().getCity());
            assertEquals("DKI Jakarta", response.getData().getProvince());
            assertEquals("Indonesia", response.getData().getCountry());
            assertEquals("12345", response.getData().getPostalCode());

            assertTrue(addressRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void testCreateAddressFailed() throws Exception{
        CreateAddressRequest addressRequest = new CreateAddressRequest();
        addressRequest.setContactId(idContact);
        addressRequest.setStreet("Jalan Sudirman");
        addressRequest.setCity("Jakarta Pusat");
        addressRequest.setProvince("DKI Jakarta");
        addressRequest.setCountry("");
        addressRequest.setPostalCode("12345");

        mockMvc.perform(
                post("/api/contacts/" + idContact + "/addresses")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressRequest))
                        .header("X-API-TOKEN", "test")

        ).andExpectAll(
                status().isBadRequest()

        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }
}