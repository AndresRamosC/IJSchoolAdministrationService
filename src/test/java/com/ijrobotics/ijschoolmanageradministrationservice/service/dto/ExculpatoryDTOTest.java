package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class ExculpatoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExculpatoryDTO.class);
        ExculpatoryDTO exculpatoryDTO1 = new ExculpatoryDTO();
        exculpatoryDTO1.setId(1L);
        ExculpatoryDTO exculpatoryDTO2 = new ExculpatoryDTO();
        assertThat(exculpatoryDTO1).isNotEqualTo(exculpatoryDTO2);
        exculpatoryDTO2.setId(exculpatoryDTO1.getId());
        assertThat(exculpatoryDTO1).isEqualTo(exculpatoryDTO2);
        exculpatoryDTO2.setId(2L);
        assertThat(exculpatoryDTO1).isNotEqualTo(exculpatoryDTO2);
        exculpatoryDTO1.setId(null);
        assertThat(exculpatoryDTO1).isNotEqualTo(exculpatoryDTO2);
    }
}
