package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class GuardianDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuardianDTO.class);
        GuardianDTO guardianDTO1 = new GuardianDTO();
        guardianDTO1.setId(1L);
        GuardianDTO guardianDTO2 = new GuardianDTO();
        assertThat(guardianDTO1).isNotEqualTo(guardianDTO2);
        guardianDTO2.setId(guardianDTO1.getId());
        assertThat(guardianDTO1).isEqualTo(guardianDTO2);
        guardianDTO2.setId(2L);
        assertThat(guardianDTO1).isNotEqualTo(guardianDTO2);
        guardianDTO1.setId(null);
        assertThat(guardianDTO1).isNotEqualTo(guardianDTO2);
    }
}
