package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GuardianMapperTest {

    private GuardianMapper guardianMapper;

    @BeforeEach
    public void setUp() {
        guardianMapper = new GuardianMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(guardianMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(guardianMapper.fromId(null)).isNull();
    }
}
