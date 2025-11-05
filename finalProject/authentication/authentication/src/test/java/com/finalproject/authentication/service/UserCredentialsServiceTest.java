package com.finalproject.authentication.service;

import com.finalproject.authentication.dto.JWTResponseDTO;
import com.finalproject.authentication.dto.RoleDTO;
import com.finalproject.authentication.dto.UserCredentialsDTO;
import com.finalproject.authentication.dto.UserCredentialsResponseDTO;
import com.finalproject.authentication.exception.BadCredentialsException;
import com.finalproject.authentication.exception.UserCredentialsNotFound;
import com.finalproject.authentication.mapper.RoleMapper;
import com.finalproject.authentication.mapper.UserCredentialsMapper;
import com.finalproject.authentication.model.Role;
import com.finalproject.authentication.model.UserCredentials;
import com.finalproject.authentication.model.UserCredentialsRole;
import com.finalproject.authentication.repository.RoleRepository;
import com.finalproject.authentication.repository.UserCredentialsRepository;
import com.finalproject.authentication.service.impl.UserCredentialsServiceImpl;
import com.finalproject.authentication.utils.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserCredentialsServiceTest {

    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    @Mock
    private PasswordUtils passwordUtils;

    @Mock
    private UserCredentialsMapper userCredentialsMapper;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private UserCredentialsServiceImpl userCredentialsService;

    private UserCredentials userCredentials;
    private UserCredentialsDTO userCredentialsDTO;
    private String password;
    private RoleDTO roleDTO = new RoleDTO();
    private Role role = new Role();
    private Long userCredentialsId;

    @BeforeEach
    void init() {
        password = "$2a$10$TSwblC5u/WXhazgbTgNO/uUAsKKlDfecvupV41KvQ8vEwlyvEBe1.";
        userCredentialsId = 1L;
        userCredentials = new UserCredentials();
        userCredentials.setId(1L);
        userCredentials.setUsername("johndoe");
        userCredentials.setPassword("$2a$10$TSwblC5u/WXhazgbTgNO/uUAsKKlDfecvupV41KvQ8vEwlyvEBe1.");
        userCredentials.setCorrelationId("1");
        userCredentialsDTO = new UserCredentialsDTO();
        userCredentialsDTO.setUsername("johndoe");
        userCredentialsDTO.setPassword("$2a$10$TSwblC5u/WXhazgbTgNO/uUAsKKlDfecvupV41KvQ8vEwlyvEBe1.");
        userCredentialsDTO.setCorrelationId("1");
        roleDTO.setName("ADMIN");
        role.setName("ADMIN");
        userCredentialsDTO.setRoles(List.of(roleDTO));
        UserCredentialsRole userCredentialsRole = new UserCredentialsRole();
        userCredentialsRole.setRole(role);
        userCredentialsRole.setUserCredentials(userCredentials);
        Set<UserCredentialsRole> credentialsRoleSet = new HashSet<>();
        credentialsRoleSet.add(userCredentialsRole);
        userCredentials.setUserCredentialsRoles(credentialsRoleSet);
    }

    @Test
    void userCredentialsService_getAll_returnsUserCredentialsResponseDTOPage() {

        Pageable pageable = PageRequest.of(0, 2);
        List<UserCredentials> userCredentialsList = List.of(userCredentials);
        Page<UserCredentials> userCredentialsPage = new PageImpl<>(userCredentialsList);

        //Act
        when(userCredentialsRepository.findAll(Mockito.any(Pageable.class))).thenReturn(userCredentialsPage);
        when(userCredentialsMapper.toDTO(userCredentials)).thenReturn(userCredentialsDTO);
        Page<UserCredentialsResponseDTO> result = userCredentialsService.getAll(pageable);

        //Assert
        assertThat(result).isNotNull();

    }

    @Test
    void userCredentialsService_getById_returnsOneUserCredentialsDto() throws UserCredentialsNotFound {

        //Act
        when(userCredentialsRepository.findById(userCredentials.getId())).thenReturn(Optional.ofNullable(userCredentials));
        when(userCredentialsMapper.toDTO(userCredentials)).thenReturn(userCredentialsDTO);
        UserCredentialsResponseDTO userCredentialsResponseDTO1 = userCredentialsService.getById(userCredentials.getId());

        //Assert
        assertThat(userCredentialsResponseDTO1).isNotNull();
        assertThat(userCredentialsResponseDTO1.getUsername()).isEqualTo(userCredentials.getUsername());

    }

    @Test
    void userCredentialsService_create_returnsOneUserCredentialsDto() {

        //Act
        when(userCredentialsMapper.toEntity(userCredentialsDTO)).thenReturn(userCredentials);
        when(passwordUtils.hash(userCredentials.getPassword())).thenReturn(password);
        when(userCredentialsRepository.save(userCredentials)).thenReturn(userCredentials);
        when(userCredentialsMapper.toDTO(userCredentials)).thenReturn(userCredentialsDTO);
        UserCredentialsDTO userCredentialsDTO1 = userCredentialsService.create(userCredentialsDTO);

        //Assert
        assertThat(userCredentialsDTO1).isNotNull();
        assertThat(userCredentialsDTO1.getUsername()).isEqualTo(userCredentials.getUsername());

    }

    @Test
    void userCredentialsService_delete_returnsNothing() {

        when(userCredentialsRepository.findById(userCredentials.getId())).thenReturn(Optional.ofNullable(userCredentials));
        doNothing().when(userCredentialsRepository).deleteById(userCredentials.getId());

        //Assert
        assertAll(() -> userCredentialsService.delete(userCredentialsId));

    }

}
