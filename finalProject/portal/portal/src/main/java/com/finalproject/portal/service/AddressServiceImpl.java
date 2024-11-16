package com.finalproject.portal.service;

import com.finalproject.portal.client.collaboratorClients.AddressClient;
import com.finalproject.portal.dto.AddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl {

    private final AddressClient addressClient;

    public AddressServiceImpl(AddressClient addressClient) {
        this.addressClient = addressClient;
    }

    public ResponseEntity<Page<AddressDTO>> getAll(Pageable pageable) {
        return addressClient.getAll(pageable);
    }

    public ResponseEntity<AddressDTO> getById(long id) {
        return addressClient.getById(id);
    }

    public ResponseEntity<AddressDTO> create(AddressDTO addressDTO) {
        return addressClient.create(addressDTO);
    }

    public ResponseEntity<AddressDTO> update(AddressDTO addressDTO) {
        return addressClient.update(addressDTO);
    }

    public ResponseEntity<Void> delete(long id) {
        addressClient.delete(id);
        return ResponseEntity.ok().build();
    }

}
