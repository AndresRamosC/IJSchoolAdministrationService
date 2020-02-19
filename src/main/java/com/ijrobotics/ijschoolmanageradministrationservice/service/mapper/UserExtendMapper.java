package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.UserExtendDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserExtend} and its DTO {@link UserExtendDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserExtendMapper extends EntityMapper<UserExtendDTO, UserExtend> {


    @Mapping(target = "person", ignore = true)
    UserExtend toEntity(UserExtendDTO userExtendDTO);

    default UserExtend fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserExtend userExtend = new UserExtend();
        userExtend.setId(id);
        return userExtend;
    }
}
