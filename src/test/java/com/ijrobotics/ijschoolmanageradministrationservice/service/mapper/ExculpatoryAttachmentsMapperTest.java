package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExculpatoryAttachmentsMapperTest {

    private ExculpatoryAttachmentsMapper exculpatoryAttachmentsMapper;

    @BeforeEach
    public void setUp() {
        exculpatoryAttachmentsMapper = new ExculpatoryAttachmentsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(exculpatoryAttachmentsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(exculpatoryAttachmentsMapper.fromId(null)).isNull();
    }
}
