package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AttachmentsMapperTest {

    private AttachmentsMapper attachmentsMapper;

    @BeforeEach
    public void setUp() {
        attachmentsMapper = new AttachmentsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(attachmentsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(attachmentsMapper.fromId(null)).isNull();
    }
}
