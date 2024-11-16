package com.finalproject.portal.service;

import com.finalproject.portal.client.salaryManagementClients.ComponentTypeClient;
import com.finalproject.portal.dto.ComponentTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ComponentTypeServiceImpl {

    private final ComponentTypeClient componentTypeClient;

    public ComponentTypeServiceImpl(ComponentTypeClient componentTypeClient) {
        this.componentTypeClient = componentTypeClient;
    }

    public ResponseEntity<Page<ComponentTypeDTO>> getAll(Pageable pageable) {
        return componentTypeClient.getAll(pageable);
    }

    public ResponseEntity<ComponentTypeDTO> getById(long id) {
        return componentTypeClient.getById(id);
    }

    public ResponseEntity<ComponentTypeDTO> create(ComponentTypeDTO componentTypeDTO) {
        return componentTypeClient.create(componentTypeDTO);
    }

    public ResponseEntity<ComponentTypeDTO> update(ComponentTypeDTO componentTypeDTO) {
        return componentTypeClient.update(componentTypeDTO);
    }

    public ResponseEntity<Void> delete(long id) {
        componentTypeClient.delete(id);
        return ResponseEntity.ok().build();
    }

}
