package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceMapperTest {

    private AttendanceMapper attendanceMapper;

    @BeforeEach
    public void setUp() {
        attendanceMapper = new AttendanceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(attendanceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(attendanceMapper.fromId(null)).isNull();
    }
}
