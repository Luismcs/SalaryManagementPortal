package com.finalproject.collaborator.service;

import com.finalproject.collaborator.dto.AddressDTO;
import com.finalproject.collaborator.enums.Gender;
import com.finalproject.collaborator.exception.AddressNotFoundException;
import com.finalproject.collaborator.exception.CollaboratorNotFoundException;
import com.finalproject.collaborator.mapper.AddressMapper;
import com.finalproject.collaborator.model.Address;
import com.finalproject.collaborator.model.Collaborator;
import com.finalproject.collaborator.repository.AddressRepository;
import com.finalproject.collaborator.repository.CollaboratorRepository;
import com.finalproject.collaborator.service.impl.AddressServiceImpl;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CollaboratorRepository collaboratorRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Collaborator collaborator;
    private Address address;
    private AddressDTO addressDTO;
    private Long addressId;

    @BeforeEach
    public void init() {
        collaborator = new Collaborator();
        collaborator.setId(1L);
        collaborator.setFullName("John Doe");
        collaborator.setEmail("john.doe@example.com");
        collaborator.setGender(Gender.M);
        collaborator.setBirthDate(LocalDate.now());
        collaborator.setNif("123123123");
        address = new Address();
        address.setStreet("Rua nova");
        address.setPostalCode("4450-450");
        address.setCity("Porto");
        address.setCountry("Portugal");
        address.setCollaborator(collaborator);
        addressDTO = new AddressDTO();
        addressDTO.setStreet("Rua nova");
        addressDTO.setPostalCode("4450-450");
        addressDTO.setCity("Porto");
        addressDTO.setCountry("Portugal");
        addressDTO.setCollaboratorId(collaborator.getId());
        addressId = 1L;

    }


    @Test
    void addressService_getAll_returnsAddressDtoPage() {

        //Arrange
        Pageable pageable = PageRequest.of(0, 2);
        List<Address> addressList = List.of(address);
        Page<Address> addressPage = new PageImpl<>(addressList);

        //Action
        when(addressRepository.findAll(Mockito.any(Pageable.class))).thenReturn(addressPage);
        when(addressMapper.toDTO(address)).thenReturn(addressDTO);

        Page<AddressDTO> result = addressService.getAll(pageable);

        //Assert
        assertThat(result).isNotNull();

    }

    @Test
    void addressService_getById_returnsAddressDto() throws AddressNotFoundException {

        //Act
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(address));
        when(addressMapper.toDTO(address)).thenReturn(addressDTO);
        AddressDTO foundAddressDto = addressService.getById(addressId);

        //Assert
        assertThat(foundAddressDto).isNotNull();
    }

    @Test
    void addressService_create_returnsAddressDto() throws CollaboratorNotFoundException {

        //Act
        when(addressMapper.toEntity(addressDTO)).thenReturn(address);
        when(collaboratorRepository.findById(collaborator.getId())).thenReturn(Optional.of(collaborator));
        when(addressMapper.toDTO(addressRepository.save(address))).thenReturn(addressDTO);
        AddressDTO savedAddress = addressService.addAddress(addressDTO);

        //Assert
        assertThat(savedAddress).isNotNull();

    }

    @Test
    void addressService_update_returnsAddressDto() throws CollaboratorNotFoundException, AddressNotFoundException {

        //Act
        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));
        when(collaboratorRepository.findById(collaborator.getId())).thenReturn(Optional.of(collaborator));
        when(addressMapper.toEntity(addressDTO)).thenReturn(address);
        when(addressMapper.toDTO(addressRepository.save(address))).thenReturn(addressDTO);
        AddressDTO savedAddress = addressService.updateAddress(addressDTO);

        //Assert
        assertThat(savedAddress).isNotNull();

    }

    @Test
    void addressService_delete_returnsNothing() {

        //Act
        when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));
        doNothing().when(addressRepository).deleteById(address.getId());

        //Assert
        assertAll(() -> addressService.deleteAddress(address.getId()));
    }

}
