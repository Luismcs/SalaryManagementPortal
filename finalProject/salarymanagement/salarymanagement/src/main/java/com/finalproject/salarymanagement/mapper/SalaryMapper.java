package com.finalproject.salarymanagement.mapper;

import com.finalproject.salarymanagement.dto.SalaryDTO;
import com.finalproject.salarymanagement.model.Salary;
import com.finalproject.salarymanagement.repository.ComponentTypeRepository;
import com.finalproject.salarymanagement.repository.ComponentTypeSubtypeRepository;
import org.mapstruct.*;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = {SalaryComponentMapper.class})
public interface SalaryMapper {

    @Mapping(target = "salaryComponents", source = "salaryComponents")
    Salary toEntity(SalaryDTO salaryDTO, @Context ComponentTypeRepository componentTypeRepository, @Context ComponentTypeSubtypeRepository componentTypeSubtypeRepository);

    SalaryDTO toDto(Salary salaryDTO);

    @AfterMapping
    default void setSalary(@MappingTarget Salary salary) {
        Optional.ofNullable(salary.getSalaryComponents())
                .ifPresent(salaryComponents -> salaryComponents.forEach(salaryComponent ->
                        salaryComponent.setSalary(salary)
                ));
    }

}
