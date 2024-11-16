package com.finalproject.portal.service;

import com.finalproject.portal.client.salaryManagementClients.ComponentTypeSubtypeClient;
import com.finalproject.portal.dto.ComponentTypeSubtypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ComponentTypeSubtypeServiceImpl {

    private final ComponentTypeSubtypeClient componentTypeSubtypeClient;

    public ComponentTypeSubtypeServiceImpl(ComponentTypeSubtypeClient componentTypeSubtypeClient) {
        this.componentTypeSubtypeClient = componentTypeSubtypeClient;
    }

    public ResponseEntity<Page<ComponentTypeSubtypeDTO>> getAll(Pageable pageable) {
        return componentTypeSubtypeClient.getAll(pageable);
    }

    public ResponseEntity<ComponentTypeSubtypeDTO> getById(Long id) {
        return componentTypeSubtypeClient.getById(id);
    }

    public ResponseEntity<ComponentTypeSubtypeDTO> create(ComponentTypeSubtypeDTO componentTypeSubtypeDTO) {
        return componentTypeSubtypeClient.create(componentTypeSubtypeDTO);
    }

    public ResponseEntity<ComponentTypeSubtypeDTO> update(ComponentTypeSubtypeDTO componentTypeSubtypeDTO) {
        return componentTypeSubtypeClient.update(componentTypeSubtypeDTO);
    }

    public ResponseEntity<Void> delete(Long id) {
        componentTypeSubtypeClient.delete(id);
        return ResponseEntity.ok().build();
    }

}
