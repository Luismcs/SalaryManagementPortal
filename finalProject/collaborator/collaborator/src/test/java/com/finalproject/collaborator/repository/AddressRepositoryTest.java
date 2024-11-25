package com.finalproject.collaborator.repository;

import com.finalproject.collaborator.enums.Gender;
import com.finalproject.collaborator.model.Address;
import com.finalproject.collaborator.model.Collaborator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    private Collaborator collaborator;
    private Address address1;
    private Address address2;

    @BeforeEach
    void init() {
        collaborator = new Collaborator();
        collaborator.setFullName("John Doe");
        collaborator.setEmail("john.doe@example.com");
        collaborator.setGender(Gender.M);
        collaborator.setBirthDate(LocalDate.now());
        collaborator.setNif("123123123");
        collaboratorRepository.save(collaborator);
        address1 = new Address();
        address1.setStreet("Rua nova");
        address1.setPostalCode("4450-450");
        address1.setCity("Porto");
        address1.setCountry("Portugal");
        address1.setCollaborator(collaborator);
        address2 = new Address();
        address2.setStreet("Rua nova");
        address2.setPostalCode("4450-450");
        address2.setCity("Porto");
        address2.setCountry("Portugal");
        address2.setCollaborator(collaborator);
    }

    @Test
    void addressRepository_GetAll_ReturnsMoreThanOneCollaborator() {

        //Arrange
        addressRepository.save(address1);
        addressRepository.save(address2);

        //Act
        List<Address> addresses = addressRepository.findAll();

        //Assert
        assertThat(addresses).isNotNull();
        assertThat(addresses.size()).isEqualTo(2);

    }

    @Test
    void collaboratorRepository_GetById_ReturnsOneCollaborator() {

        //Arrange
        Address savedAddress = addressRepository.save(address1);

        //Act
        Optional<Address> foundAddress = addressRepository.findById(savedAddress.getId());

        //Assert
        assertThat(foundAddress).isNotNull();
    }

    @Test
    void collaboratorRepository_Add_ReturnsCollaboratorNotNull() {

        //Arrange
        Address savedAddress = addressRepository.save(address1);

        //Act
        Optional<Address> fetchedCollaborator = addressRepository.findById(savedAddress.getId());

        //Assert
        assertThat(fetchedCollaborator).isPresent();
        assertThat(addressRepository.findById(fetchedCollaborator.get().getId())).isNotNull();
    }

    @Test
    void collaboratorRepository_Update_ReturnsCollaboratorNotNull() {

        //Arrange
        Address savedAddress = addressRepository.save(address1);

        //Act
        Optional<Address> foundAddress = addressRepository.findById(savedAddress.getId());
        foundAddress.get().setCity("Braga");
        Address updatedAddress = addressRepository.save(foundAddress.get());

        //Assert
        assertThat(updatedAddress.getCity()).isEqualTo("Braga");
    }

    @Test
    void collaboratorRepository_Delete_ReturnsOneCollaborator() {

        //Arrange
        Address savedAddress = addressRepository.save(address1);

        //Act
        addressRepository.deleteById(savedAddress.getId());
        Optional<Address> foundAddress = addressRepository.findById(savedAddress.getId());

        //Assert
        assertThat(foundAddress).isEmpty();
    }

}
