package com.finalproject.authentication.repository;

import com.finalproject.authentication.model.RefreshToken;
import com.finalproject.authentication.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;
    private Role role1;
    private Role role2;

    @BeforeEach
    void init() {
        role1 = new Role();
        role1.setName("ADMIN");
        role2 = new Role();
        role2.setName("USER");
    }

    @Test
    void roleRepository_getAll_returnsModeThanOneRole() {

        roleRepository.save(role1);
        roleRepository.save(role2);
        List<Role> refreshTokens = roleRepository.findAll();

        assertThat(refreshTokens).isNotNull();
        assertThat(refreshTokens.size()).isEqualTo(2);

    }

    @Test
    void roleRepository_getById_returnsOneRole() {

        Role savedRole = roleRepository.save(role1);
        Optional<Role> foundRole = roleRepository.findById(savedRole.getId());

        assertThat(foundRole).isNotNull();
    }

    @Test
    void roleRepository_create_returnsOneRole() {

        Role savedRole = roleRepository.save(role1);
        Optional<Role> fetchedRole = roleRepository.findById(savedRole.getId());

        assertThat(fetchedRole).isPresent();
        assertThat(roleRepository.findById(fetchedRole.get().getId())).isNotNull();
    }

    @Test
    void roleRepository_update_returnsOneRole() {

        Role savedRole = roleRepository.save(role1);
        Optional<Role> foundRole = roleRepository.findById(savedRole.getId());
        foundRole.get().setName("HR");
        Role updatedRole = roleRepository.save(foundRole.get());

        assertThat(updatedRole.getName()).isEqualTo("HR");
    }

    @Test
    void roleRepository_delete_returnsNothing() {

        Role savedRole = roleRepository.save(role1);
        roleRepository.deleteById(savedRole.getId());
        Optional<Role> foundRole = roleRepository.findById(savedRole.getId());

        assertThat(foundRole).isEmpty();
    }

}
