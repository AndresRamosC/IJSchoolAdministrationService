package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExculpatoryAttContentMapperTest {

    private ExculpatoryAttContentMapper exculpatoryAttContentMapper;

    @BeforeEach
    public void setUp() {
        exculpatoryAttContentMapper = new ExculpatoryAttContentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(exculpatoryAttContentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(exculpatoryAttContentMapper.fromId(null)).isNull();
    }
}
