package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class AttendanceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendanceDTO.class);
        AttendanceDTO attendanceDTO1 = new AttendanceDTO();
        attendanceDTO1.setId(1L);
        AttendanceDTO attendanceDTO2 = new AttendanceDTO();
        assertThat(attendanceDTO1).isNotEqualTo(attendanceDTO2);
        attendanceDTO2.setId(attendanceDTO1.getId());
        assertThat(attendanceDTO1).isEqualTo(attendanceDTO2);
        attendanceDTO2.setId(2L);
        assertThat(attendanceDTO1).isNotEqualTo(attendanceDTO2);
        attendanceDTO1.setId(null);
        assertThat(attendanceDTO1).isNotEqualTo(attendanceDTO2);
    }
}
