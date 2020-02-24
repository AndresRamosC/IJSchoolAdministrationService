package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupNoticesMapperTest {

    private GroupNoticesMapper groupNoticesMapper;

    @BeforeEach
    public void setUp() {
        groupNoticesMapper = new GroupNoticesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(groupNoticesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(groupNoticesMapper.fromId(null)).isNull();
    }
}
