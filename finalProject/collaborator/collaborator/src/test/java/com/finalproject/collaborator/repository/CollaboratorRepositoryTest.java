package com.finalproject.collaborator.repository;

import com.finalproject.collaborator.enums.Gender;
import com.finalproject.collaborator.model.Collaborator;
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
public class CollaboratorRepositoryTest {

    @Autowired
    private CollaboratorRepository repository;

    @Test
    void collaboratorRepository_GetAll_ReturnsMoreThanOneCollaborator() {
        Collaborator collaborator1 = new Collaborator();
        collaborator1.setFullName("John Doe");
        collaborator1.setEmail("john.doe@example.com");
        collaborator1.setGender(Gender.M);
        collaborator1.setBirthDate(LocalDate.now());
        collaborator1.setNif("123123123");
        collaborator1.setAddresses(null);
        Collaborator collaborator2 = new Collaborator();
        collaborator2.setFullName("John Doe");
        collaborator2.setEmail("john.doe@example.com");
        collaborator2.setGender(Gender.M);
        collaborator2.setBirthDate(LocalDate.now());
        collaborator2.setNif("123123123");
        collaborator2.setAddresses(null);

        repository.save(collaborator1);
        repository.save(collaborator2);
        List<Collaborator> collaborators = repository.findAll();

        assertThat(collaborators).isNotNull();
        assertThat(collaborators.size()).isEqualTo(2);

    }

    @Test
    void collaboratorRepository_GetById_ReturnsOneCollaborator() {
        Collaborator collaborator = new Collaborator();
        collaborator.setFullName("John Doe");
        collaborator.setEmail("john.doe@example.com");
        collaborator.setGender(Gender.M);
        collaborator.setBirthDate(LocalDate.now());
        collaborator.setNif("123123123");
        collaborator.setAddresses(null);

        Collaborator savedCollaborator = repository.save(collaborator);
        Optional<Collaborator> foundCollaborator = repository.findById(savedCollaborator.getId());

        assertThat(foundCollaborator).isNotNull();
    }

    @Test
    void collaboratorRepository_Add_ReturnsCollaboratorNotNull() {
        Collaborator collaborator = new Collaborator();
        collaborator.setFullName("John Doe");
        collaborator.setEmail("john.doe@example.com");
        collaborator.setGender(Gender.M);
        collaborator.setBirthDate(LocalDate.now());
        collaborator.setNif("123123123");
        collaborator.setAddresses(null);

        Collaborator savedCollaborator = repository.save(collaborator);
        Optional<Collaborator> fetchedCollaborator = repository.findById(savedCollaborator.getId());

        assertThat(fetchedCollaborator).isPresent();
        assertThat(repository.findById(fetchedCollaborator.get().getId())).isNotNull();
    }

    @Test
    void collaboratorRepository_Update_ReturnsCollaboratorNotNull() {
        Collaborator collaborator = new Collaborator();
        collaborator.setFullName("John Doe");
        collaborator.setEmail("john.doe@example.com");
        collaborator.setGender(Gender.M);
        collaborator.setBirthDate(LocalDate.now());
        collaborator.setNif("123123123");
        collaborator.setAddresses(null);

        Collaborator savedCollaborator = repository.save(collaborator);
        Optional<Collaborator> foundCollaborator = repository.findById(savedCollaborator.getId());
        foundCollaborator.get().setEmail("testupdate@email.com");
        Collaborator updatedCollaborator = repository.save(foundCollaborator.get());

        assertThat(updatedCollaborator.getEmail()).isEqualTo("testupdate@email.com");
    }

    @Test
    void collaboratorRepository_Delete_ReturnsOneCollaborator() {
        Collaborator collaborator = new Collaborator();
        collaborator.setFullName("John Doe");
        collaborator.setEmail("john.doe@example.com");
        collaborator.setGender(Gender.M);
        collaborator.setBirthDate(LocalDate.now());
        collaborator.setNif("123123123");
        collaborator.setAddresses(null);

        Collaborator savedCollaborator = repository.save(collaborator);
        repository.deleteById(savedCollaborator.getId());
        Optional<Collaborator> foundCollaborator = repository.findById(savedCollaborator.getId());

        assertThat(foundCollaborator).isEmpty();
    }

}
