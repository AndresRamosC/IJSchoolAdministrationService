package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AttachmentsContentMapperTest {

    private AttachmentsContentMapper attachmentsContentMapper;

    @BeforeEach
    public void setUp() {
        attachmentsContentMapper = new AttachmentsContentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(attachmentsContentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(attachmentsContentMapper.fromId(null)).isNull();
    }
}
