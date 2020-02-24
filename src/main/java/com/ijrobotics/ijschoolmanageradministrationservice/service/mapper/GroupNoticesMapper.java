package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;


import com.ijrobotics.ijschoolmanageradministrationservice.domain.*;
import com.ijrobotics.ijschoolmanageradministrationservice.service.dto.GroupNoticesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GroupNotices} and its DTO {@link GroupNoticesDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClassGroupMapper.class})
public interface GroupNoticesMapper extends EntityMapper<GroupNoticesDTO, GroupNotices> {

    @Mapping(source = "classGroup.id", target = "classGroupId")
    GroupNoticesDTO toDto(GroupNotices groupNotices);

    @Mapping(source = "classGroupId", target = "classGroup")
    GroupNotices toEntity(GroupNoticesDTO groupNoticesDTO);

    default GroupNotices fromId(Long id) {
        if (id == null) {
            return null;
        }
        GroupNotices groupNotices = new GroupNotices();
        groupNotices.setId(id);
        return groupNotices;
    }
}
