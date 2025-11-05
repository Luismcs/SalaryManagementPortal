package com.finalproject.authentication.service.impl;

import com.finalproject.authentication.dto.RoleDTO;
import com.finalproject.authentication.dto.UserCredentialsDTO;
import com.finalproject.authentication.dto.UserCredentialsRequestDTO;
import com.finalproject.authentication.dto.UserCredentialsResponseDTO;
import com.finalproject.authentication.enums.ErrorResponseCode;
import com.finalproject.authentication.exception.ErrorMessage;
import com.finalproject.authentication.exception.RoleNotFoundException;
import com.finalproject.authentication.exception.UserCredentialsNotFound;
import com.finalproject.authentication.mapper.RoleMapper;
import com.finalproject.authentication.mapper.UserCredentialsMapper;
import com.finalproject.authentication.mapper.UserCredentialsRequestDTOMapper;
import com.finalproject.authentication.model.Role;
import com.finalproject.authentication.model.UserCredentials;
import com.finalproject.authentication.model.UserCredentialsRole;
import com.finalproject.authentication.repository.RoleRepository;
import com.finalproject.authentication.repository.UserCredentialsRepository;
import com.finalproject.authentication.service.UserCredentialsService;
import com.finalproject.authentication.utils.PasswordUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserCredentialsServiceImpl implements UserCredentialsService {

    private final UserCredentialsRepository userCredentialsRepository;
    private final UserCredentialsMapper userCredentialsMapper;
    private final UserCredentialsRequestDTOMapper userCredentialsRequestDTOMapper;
    private final RoleRepository roleRepository;
    private final PasswordUtils passwordUtils;

    public UserCredentialsServiceImpl(UserCredentialsRepository userCredentialsRepository,
                                      UserCredentialsMapper userCredentialsMapper,
                                      UserCredentialsRequestDTOMapper userCredentialsRequestDTOMapper,
                                      RoleRepository roleRepository,
                                      PasswordUtils passwordUtils) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.userCredentialsMapper = userCredentialsMapper;
        this.userCredentialsRequestDTOMapper = userCredentialsRequestDTOMapper;
        this.roleRepository = roleRepository;
        this.passwordUtils = passwordUtils;
    }

    public boolean existsByUsername(String username) {
        return userCredentialsRepository.existsByUsername(username);
    }

    public Page<UserCredentialsResponseDTO> getAll(Pageable pageable) {
        Page<UserCredentials> collaborators = userCredentialsRepository.findAll(pageable);

        return collaborators.map(userCredentialsMapper::toUserCredentialsResponseDTO);
    }

    public UserCredentialsResponseDTO getById(Long id) throws UserCredentialsNotFound {
        UserCredentials userCredentials =
                userCredentialsRepository.findById(id).orElseThrow(() ->
                        new UserCredentialsNotFound(ErrorResponseCode.USER_CREDENTIALS_NOT_FOUND,
                                404, ErrorMessage.USER_CREDENTIALS_NOT_FOUND, id));

        return userCredentialsMapper.toUserCredentialsResponseDTO(userCredentials);
    }

    private void setUserCredentialsRolesFromUserCredentialsRequestDTO(UserCredentials userCredentials,
                                                                      UserCredentialsRequestDTO
                                                                              userCredentialsRequestDTO)
            throws RoleNotFoundException {

        for (Long roleId : userCredentialsRequestDTO.getRoles()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RoleNotFoundException(ErrorResponseCode.ROLE_NOT_FOUND, 404,
                            ErrorMessage.ROLE_NOT_FOUND, roleId));
            UserCredentialsRole link = new UserCredentialsRole();
            link.setUserCredentials(userCredentials);
            link.setRole(role);
            userCredentials.getUserCredentialsRoles().add(link);
        }
    }

    public UserCredentialsResponseDTO create(UserCredentialsRequestDTO userCredentialsRequestDTO) throws RoleNotFoundException {
        UserCredentials userCredentials = userCredentialsRequestDTOMapper.toEntity(userCredentialsRequestDTO);
        setUserCredentialsRolesFromUserCredentialsRequestDTO(userCredentials, userCredentialsRequestDTO);
        userCredentials.setPassword(passwordUtils.hash(userCredentials.getPassword()));
        UserCredentials savedUserCredentials = userCredentialsRepository.save(userCredentials);
        return userCredentialsMapper.toUserCredentialsResponseDTO(savedUserCredentials);
    }

    public void attachRolesToExistingUser(UserCredentials existingUserCredentials, UserCredentialsRequestDTO userCredentialsRequestDTO) throws RoleNotFoundException {

        for (Long roleId : userCredentialsRequestDTO.getRoles()) {
            roleRepository.findById(roleId)
                    .orElseThrow(() -> new RoleNotFoundException(ErrorResponseCode.ROLE_NOT_FOUND,
                            404, ErrorMessage.ROLE_NOT_FOUND, roleId));
        }

        existingUserCredentials.getUserCredentialsRoles().clear();

        List<Role> roles = roleRepository.findAllById(userCredentialsRequestDTO.getRoles());

        for (Role role : roles) {
            UserCredentialsRole link = new UserCredentialsRole();
            link.setUserCredentials(existingUserCredentials);
            link.setRole(role);
            existingUserCredentials.getUserCredentialsRoles().add(link);
        }
    }

    public UserCredentialsResponseDTO update(UserCredentialsRequestDTO userCredentialsRequestDTO)
            throws UserCredentialsNotFound, RoleNotFoundException {

        UserCredentials existing = userCredentialsRepository.findById(userCredentialsRequestDTO.getId())
                .orElseThrow(() -> new UserCredentialsNotFound(ErrorResponseCode.USER_CREDENTIALS_NOT_FOUND,
                        404, ErrorMessage.ROLE_NOT_FOUND, userCredentialsRequestDTO.getId()));

        existing.setUsername(userCredentialsRequestDTO.getUsername());
        existing.setPassword(userCredentialsRequestDTO.getPassword());
        existing.setCorrelationId(userCredentialsRequestDTO.getCorrelationId());
        attachRolesToExistingUser(existing, userCredentialsRequestDTO);

        return userCredentialsMapper.toUserCredentialsResponseDTO(userCredentialsRepository.save(existing));
    }

    /*@Transactional
    public UserCredentialsResponseDTO update(UserCredentialsRequestDTO dto)
            throws UserCredentialsNotFound {

        UserCredentials existing = userCredentialsRepository.findById(dto.getId())
                .orElseThrow(() -> new UserCredentialsNotFound(ErrorResponseCode.USER_CREDENTIALS_NOT_FOUND,
                        404, ErrorMessage.ROLE_NOT_FOUND, dto.getId()));

        // --- Basic fields ---
        existing.setUsername(dto.getUsername());
        existing.setPassword(dto.getPassword());
        existing.setCorrelationId(dto.getCorrelationId());

        // --- Roles update ---
        if (dto.getRoles() != null) {
            // Remove existing roles
            existing.getUserCredentialsRoles().clear();

            // Fetch new roles
            List<Role> roles = roleRepository.findAllById(dto.getRoles());

            // Create and attach new UserCredentialsRole entries
            for (Role role : roles) {
                UserCredentialsRole link = new UserCredentialsRole();
                link.setUserCredentials(existing);
                link.setRole(role);
                existing.getUserCredentialsRoles().add(link);
            }
        }

        UserCredentials savedUserCredentials = userCredentialsRepository.save(existing);
        return userCredentialsMapper.toUserCredentialsResponseDTO(savedUserCredentials);
    }*/

    public void delete(Long id) throws UserCredentialsNotFound {
        userCredentialsRepository.findById(id).orElseThrow(() ->
                new UserCredentialsNotFound(ErrorResponseCode.USER_CREDENTIALS_NOT_FOUND,
                        404, ErrorMessage.USER_CREDENTIALS_NOT_FOUND, id));
        userCredentialsRepository.deleteById(id);

        log.info("User credentials with id {} deleted successfully", id);

    }

}
