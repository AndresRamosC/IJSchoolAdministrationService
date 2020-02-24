package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExculpatoryMapperTest {

    private ExculpatoryMapper exculpatoryMapper;

    @BeforeEach
    public void setUp() {
        exculpatoryMapper = new ExculpatoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(exculpatoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(exculpatoryMapper.fromId(null)).isNull();
    }
}
