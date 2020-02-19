package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.CompanyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {ContactMapper.class})
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {


    @Mapping(target = "removeContact", ignore = true)

    default Company fromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
