package com.finalproject.collaborator.service;

import com.finalproject.collaborator.dto.AddressDTO;
import com.finalproject.collaborator.enums.ErrorResponseCode;
import com.finalproject.collaborator.exception.AddressNotFoundException;
import com.finalproject.collaborator.exception.CollaboratorNotFoundException;
import com.finalproject.collaborator.exception.ErrorMessage;
import com.finalproject.collaborator.model.Address;
import com.finalproject.collaborator.model.Collaborator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AddressService {

    Page<AddressDTO> getAll(Pageable pageable);

    AddressDTO getById(Long id) throws AddressNotFoundException;

    AddressDTO add(AddressDTO addressDto) throws CollaboratorNotFoundException;

    AddressDTO update(AddressDTO addressDto) throws AddressNotFoundException, CollaboratorNotFoundException;

    void delete(Long id) throws AddressNotFoundException;

}
