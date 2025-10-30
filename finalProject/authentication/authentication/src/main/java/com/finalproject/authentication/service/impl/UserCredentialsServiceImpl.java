package com.finalproject.authentication.service.impl;

import com.finalproject.authentication.dto.RoleDTO;
import com.finalproject.authentication.dto.UserCredentialsDTO;
import com.finalproject.authentication.enums.ErrorResponseCode;
import com.finalproject.authentication.exception.ErrorMessage;
import com.finalproject.authentication.exception.UserCredentialsNotFound;
import com.finalproject.authentication.mapper.RoleMapper;
import com.finalproject.authentication.mapper.UserCredentialsMapper;
import com.finalproject.authentication.model.Role;
import com.finalproject.authentication.model.UserCredentials;
import com.finalproject.authentication.model.UserCredentialsRole;
import com.finalproject.authentication.repository.RoleRepository;
import com.finalproject.authentication.repository.UserCredentialsRepository;
import com.finalproject.authentication.service.UserCredentialsService;
import com.finalproject.authentication.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserCredentialsServiceImpl implements UserCredentialsService {

    private final UserCredentialsRepository userCredentialsRepository;
    private final UserCredentialsMapper userCredentialsMapper;
    private final RoleRepository roleRepository;
    private final PasswordUtils passwordUtils;
    private final RoleMapper roleMapper;

    public UserCredentialsServiceImpl(UserCredentialsRepository userCredentialsRepository,
                                      UserCredentialsMapper userCredentialsMapper, RoleRepository roleRepository,
                                      PasswordUtils passwordUtils, RoleMapper roleMapper) {
        this.userCredentialsRepository = userCredentialsRepository;
        this.userCredentialsMapper = userCredentialsMapper;
        this.roleRepository = roleRepository;
        this.passwordUtils = passwordUtils;
        this.roleMapper = roleMapper;
    }

    public boolean existsByUsername(String username) {
        return userCredentialsRepository.existsByUsername(username);
    }

    public Page<UserCredentialsDTO> getAll(Pageable pageable) {
        Page<UserCredentials> collaborators = userCredentialsRepository.findAll(pageable);

        return collaborators.map(userCredentialsMapper::toDTO);
    }

    public UserCredentialsDTO getById(Long id) throws UserCredentialsNotFound {
        UserCredentials userCredentials =
                userCredentialsRepository.findById(id).orElseThrow(() ->
                        new UserCredentialsNotFound(ErrorResponseCode.USER_CREDENTIALS_NOT_FOUND,
                                404, ErrorMessage.USER_CREDENTIALS_NOT_FOUND, id));
        UserCredentialsDTO userCredentialsDTO = userCredentialsMapper.toDTO(userCredentials);

        setUserCredentialsDTORoles(userCredentials, userCredentialsDTO);

        log.info("UserCredentials {} returned successfully", id);

        return userCredentialsDTO;
    }

    public void setUserCredentialsDTORoles(UserCredentials userCredentials, UserCredentialsDTO userCredentialsDTO) {
        List<RoleDTO> roleDTOS = new ArrayList<>();

        for (UserCredentialsRole userCredentialsRole : userCredentials.getUserCredentialsRoles()) {
            roleDTOS.add(roleMapper.toDTO(userCredentialsRole.getRole()));
        }

        userCredentialsDTO.setRoles(roleDTOS);
    }

    public UserCredentialsDTO create(UserCredentialsDTO userCredentialsDTO) {
        UserCredentials userCredentials = userCredentialsMapper.toEntity(userCredentialsDTO);

        setUserCredentialsRoles(userCredentialsDTO, userCredentials);
        userCredentials.setPassword(passwordUtils.hash(userCredentials.getPassword()));
        UserCredentials savedUserCredentials = userCredentialsRepository.save(userCredentials);
        log.info("User credentials with id {} created successfully", savedUserCredentials.getId());

        return userCredentialsMapper.toDTO(savedUserCredentials);
    }

    public void setUserCredentialsRoles(UserCredentialsDTO userCredentialsDTO, UserCredentials userCredentials) {
        Set<UserCredentialsRole> userCredentialsRoleSet = new HashSet<>();

        for (RoleDTO roleDTO : userCredentialsDTO.getRoles()) {
            Role role = roleRepository.findByName(roleDTO.getName());
            UserCredentialsRole userCredentialsRole = new UserCredentialsRole();
            userCredentialsRole.setRole(role);
            userCredentialsRole.setUserCredentials(userCredentials);
            userCredentialsRoleSet.add(userCredentialsRole);
        }

        userCredentials.setUserCredentialsRoles(userCredentialsRoleSet);
    }

    public UserCredentialsDTO update(UserCredentialsDTO userCredentialsDTO) throws UserCredentialsNotFound {
        UserCredentials receivedUserCredentials = userCredentialsMapper.toEntity(userCredentialsDTO);

        userCredentialsRepository.findById(userCredentialsDTO.getId()).orElseThrow(() ->
                new UserCredentialsNotFound(ErrorResponseCode.USER_CREDENTIALS_NOT_FOUND,
                        404, ErrorMessage.USER_CREDENTIALS_NOT_FOUND, userCredentialsDTO.getId()));
        setUserCredentialsRoles(userCredentialsDTO, receivedUserCredentials);
        userCredentialsRepository.save(receivedUserCredentials);

        return userCredentialsDTO;
    }

    public void delete(Long id) throws UserCredentialsNotFound {
        userCredentialsRepository.findById(id).orElseThrow(() ->
                new UserCredentialsNotFound(ErrorResponseCode.USER_CREDENTIALS_NOT_FOUND,
                        404, ErrorMessage.USER_CREDENTIALS_NOT_FOUND, id));
        userCredentialsRepository.deleteById(id);

        log.info("User credentials with id {} deleted successfully", id);

    }

}
