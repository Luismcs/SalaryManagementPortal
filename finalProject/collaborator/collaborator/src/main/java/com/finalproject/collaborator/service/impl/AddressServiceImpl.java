package com.finalproject.collaborator.service.impl;

import com.finalproject.collaborator.dto.AddressDTO;
import com.finalproject.collaborator.enums.ErrorResponseCode;
import com.finalproject.collaborator.exception.AddressNotFoundException;
import com.finalproject.collaborator.exception.CollaboratorNotFoundException;
import com.finalproject.collaborator.exception.ErrorMessage;
import com.finalproject.collaborator.mapper.AddressMapper;
import com.finalproject.collaborator.model.Address;
import com.finalproject.collaborator.model.Collaborator;
import com.finalproject.collaborator.repository.AddressRepository;
import com.finalproject.collaborator.repository.CollaboratorRepository;
import com.finalproject.collaborator.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final CollaboratorRepository collaboratorRepository;

    public AddressServiceImpl(AddressRepository addressRepository,
                              AddressMapper addressMapper,
                              CollaboratorRepository collaboratorRepository) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.collaboratorRepository = collaboratorRepository;
    }

    public Page<AddressDTO> getAll(Pageable pageable) {
        Page<Address> collaborators = addressRepository.findAll(pageable);

        return collaborators.map(addressMapper::toDTO);
    }

    public AddressDTO getById(Long id) throws AddressNotFoundException {
        Address address =
                addressRepository.findById(id).orElseThrow(() ->
                        new AddressNotFoundException(ErrorResponseCode.ADDRESS_NOT_FOUND,
                                404, ErrorMessage.ADDRESS_NOT_FOUND, id));

        log.info("Address with id {} returned successfully", id);

        return addressMapper.toDTO(address);
    }

    public AddressDTO addAddress(AddressDTO addressDto) throws CollaboratorNotFoundException {
        Address address = addressMapper.toEntity(addressDto);
        Collaborator collaborator =
                collaboratorRepository.findById(addressDto.getCollaboratorId()).orElseThrow(() ->
                        new CollaboratorNotFoundException(ErrorResponseCode.COLLABORATOR_NOT_FOUND,
                                404, ErrorMessage.COLLABORATOR_NOT_FOUND, addressDto.getCollaboratorId()));

        address.setCollaborator(collaborator);

        return addressMapper.toDTO(addressRepository.save(address));
    }

    public AddressDTO updateAddress(AddressDTO addressDto) throws AddressNotFoundException,
            CollaboratorNotFoundException {
        Address existingAddress = addressRepository.findById(addressDto.getId()).orElseThrow(() ->
                new AddressNotFoundException(ErrorResponseCode.ADDRESS_NOT_FOUND,
                        404, ErrorMessage.ADDRESS_NOT_FOUND, addressDto.getId()));
        Collaborator collaborator = collaboratorRepository.findById(addressDto.getCollaboratorId()).orElseThrow(() -> new
                CollaboratorNotFoundException(ErrorResponseCode.COLLABORATOR_NOT_FOUND,
                404, ErrorMessage.COLLABORATOR_NOT_FOUND, addressDto.getCollaboratorId()));

        addressMapper.updateEntityFromDTO(addressDto, existingAddress);
        existingAddress.setCollaborator(collaborator);
        System.out.println(addressDto.getVersion());
        System.out.println(existingAddress.getVersion());
        Address updatedAddress = addressRepository.save(existingAddress);
        return addressMapper.toDTO(updatedAddress);
    }

    public void deleteAddress(Long id) throws AddressNotFoundException {
        addressRepository.findById(id).orElseThrow(() ->
                new AddressNotFoundException(ErrorResponseCode.ADDRESS_NOT_FOUND,
                        404, ErrorMessage.ADDRESS_NOT_FOUND, id));

        addressRepository.deleteById(id);
        log.info("Address with id {} deleted successfully", id);
    }

}
