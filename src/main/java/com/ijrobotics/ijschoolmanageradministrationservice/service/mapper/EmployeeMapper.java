package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {PersonMapper.class})
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {

    @Mapping(source = "person.id", target = "personId")
    EmployeeDTO toDto(Employee employee);

    @Mapping(source = "personId", target = "person")
    @Mapping(target = "teacher", ignore = true)
    Employee toEntity(EmployeeDTO employeeDTO);

    default Employee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
