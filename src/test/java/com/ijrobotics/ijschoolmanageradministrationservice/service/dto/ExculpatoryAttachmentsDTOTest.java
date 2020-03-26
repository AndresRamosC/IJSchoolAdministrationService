package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class ExculpatoryAttachmentsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExculpatoryAttachmentsDTO.class);
        ExculpatoryAttachmentsDTO exculpatoryAttachmentsDTO1 = new ExculpatoryAttachmentsDTO();
        exculpatoryAttachmentsDTO1.setId(1L);
        ExculpatoryAttachmentsDTO exculpatoryAttachmentsDTO2 = new ExculpatoryAttachmentsDTO();
        assertThat(exculpatoryAttachmentsDTO1).isNotEqualTo(exculpatoryAttachmentsDTO2);
        exculpatoryAttachmentsDTO2.setId(exculpatoryAttachmentsDTO1.getId());
        assertThat(exculpatoryAttachmentsDTO1).isEqualTo(exculpatoryAttachmentsDTO2);
        exculpatoryAttachmentsDTO2.setId(2L);
        assertThat(exculpatoryAttachmentsDTO1).isNotEqualTo(exculpatoryAttachmentsDTO2);
        exculpatoryAttachmentsDTO1.setId(null);
        assertThat(exculpatoryAttachmentsDTO1).isNotEqualTo(exculpatoryAttachmentsDTO2);
    }
}
