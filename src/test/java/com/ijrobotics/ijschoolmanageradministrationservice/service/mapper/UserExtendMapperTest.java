package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserExtendMapperTest {

    private UserExtendMapper userExtendMapper;

    @BeforeEach
    public void setUp() {
        userExtendMapper = new UserExtendMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userExtendMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userExtendMapper.fromId(null)).isNull();
    }
}
