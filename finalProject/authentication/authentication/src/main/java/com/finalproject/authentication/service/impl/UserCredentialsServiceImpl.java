package com.finalproject.authentication.service.impl;

import com.finalproject.authentication.dto.UserCredentialsRequestDTO;
import com.finalproject.authentication.dto.UserCredentialsResponseDTO;
import com.finalproject.authentication.enums.ErrorResponseCode;
import com.finalproject.authentication.exception.ErrorMessage;
import com.finalproject.authentication.exception.RoleNotFoundException;
import com.finalproject.authentication.exception.UserCredentialsNotFound;
import com.finalproject.authentication.exception.UsernameAlreadyExistsException;
import com.finalproject.authentication.mapper.UserCredentialsMapper;
import com.finalproject.authentication.mapper.UserCredentialsRequestDTOMapper;
import com.finalproject.authentication.model.Role;
import com.finalproject.authentication.model.UserCredentials;
import com.finalproject.authentication.model.UserCredentialsRole;
import com.finalproject.authentication.repository.RoleRepository;
import com.finalproject.authentication.repository.UserCredentialsRepository;
import com.finalproject.authentication.service.UserCredentialsService;
import com.finalproject.authentication.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Page<UserCredentials> userCredentials = userCredentialsRepository.findAll(pageable);

        return userCredentials.map(userCredentialsMapper::toUserCredentialsResponseDTO);
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

        for (Long roleId : userCredentialsRequestDTO.getRoleIds()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RoleNotFoundException(ErrorResponseCode.ROLE_NOT_FOUND, 404,
                            ErrorMessage.ROLE_NOT_FOUND, roleId));
            UserCredentialsRole link = new UserCredentialsRole();
            link.setUserCredentials(userCredentials);
            link.setRole(role);
            userCredentials.getUserCredentialsRoles().add(link);
        }
    }

    public UserCredentialsResponseDTO create(UserCredentialsRequestDTO userCredentialsRequestDTO) throws RoleNotFoundException, UsernameAlreadyExistsException {
        if (userCredentialsRepository.findByUsername(userCredentialsRequestDTO.getUsername()).isPresent())
            throw new UsernameAlreadyExistsException(ErrorResponseCode.USER_ALREADY_EXISTS,
                    409, ErrorMessage.USER_ALREADY_EXISTS, userCredentialsRequestDTO.getUsername());

        UserCredentials userCredentials = userCredentialsRequestDTOMapper.toEntity(userCredentialsRequestDTO);
        setUserCredentialsRolesFromUserCredentialsRequestDTO(userCredentials, userCredentialsRequestDTO);
        userCredentials.setPassword(passwordUtils.hash(userCredentials.getPassword()));
        UserCredentials savedUserCredentials = userCredentialsRepository.save(userCredentials);
        return userCredentialsMapper.toUserCredentialsResponseDTO(savedUserCredentials);
    }

    public void attachRolesToExistingUser(UserCredentials existingUserCredentials, UserCredentialsRequestDTO userCredentialsRequestDTO) throws RoleNotFoundException {

        for (Long roleId : userCredentialsRequestDTO.getRoleIds()) {
            roleRepository.findById(roleId)
                    .orElseThrow(() -> new RoleNotFoundException(ErrorResponseCode.ROLE_NOT_FOUND,
                            404, ErrorMessage.ROLE_NOT_FOUND, roleId));
        }

        existingUserCredentials.getUserCredentialsRoles().clear();

        List<Role> roles = roleRepository.findAllById(userCredentialsRequestDTO.getRoleIds());

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

        if (!existing.getVersion().equals(userCredentialsRequestDTO.getVersion())) {
            throw new ObjectOptimisticLockingFailureException(UserCredentials.class, userCredentialsRequestDTO.getId());
        }

        existing.setUsername(userCredentialsRequestDTO.getUsername());
        existing.setPassword(passwordUtils.hash(userCredentialsRequestDTO.getPassword()));
        existing.setCorrelationId(userCredentialsRequestDTO.getCorrelationId());
        attachRolesToExistingUser(existing, userCredentialsRequestDTO);

        return userCredentialsMapper.toUserCredentialsResponseDTO(userCredentialsRepository.save(existing));
    }

    public void delete(Long id) throws UserCredentialsNotFound {
        userCredentialsRepository.findById(id).orElseThrow(() ->
                new UserCredentialsNotFound(ErrorResponseCode.USER_CREDENTIALS_NOT_FOUND,
                        404, ErrorMessage.USER_CREDENTIALS_NOT_FOUND, id));
        userCredentialsRepository.deleteById(id);
    }

}
