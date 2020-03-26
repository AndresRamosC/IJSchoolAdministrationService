package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class ExculpatoryAttContentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExculpatoryAttContentDTO.class);
        ExculpatoryAttContentDTO exculpatoryAttContentDTO1 = new ExculpatoryAttContentDTO();
        exculpatoryAttContentDTO1.setId(1L);
        ExculpatoryAttContentDTO exculpatoryAttContentDTO2 = new ExculpatoryAttContentDTO();
        assertThat(exculpatoryAttContentDTO1).isNotEqualTo(exculpatoryAttContentDTO2);
        exculpatoryAttContentDTO2.setId(exculpatoryAttContentDTO1.getId());
        assertThat(exculpatoryAttContentDTO1).isEqualTo(exculpatoryAttContentDTO2);
        exculpatoryAttContentDTO2.setId(2L);
        assertThat(exculpatoryAttContentDTO1).isNotEqualTo(exculpatoryAttContentDTO2);
        exculpatoryAttContentDTO1.setId(null);
        assertThat(exculpatoryAttContentDTO1).isNotEqualTo(exculpatoryAttContentDTO2);
    }
}
