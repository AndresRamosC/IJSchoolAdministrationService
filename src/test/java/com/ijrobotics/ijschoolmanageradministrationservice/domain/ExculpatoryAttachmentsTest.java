package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class ExculpatoryAttachmentsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExculpatoryAttachments.class);
        ExculpatoryAttachments exculpatoryAttachments1 = new ExculpatoryAttachments();
        exculpatoryAttachments1.setId(1L);
        ExculpatoryAttachments exculpatoryAttachments2 = new ExculpatoryAttachments();
        exculpatoryAttachments2.setId(exculpatoryAttachments1.getId());
        assertThat(exculpatoryAttachments1).isEqualTo(exculpatoryAttachments2);
        exculpatoryAttachments2.setId(2L);
        assertThat(exculpatoryAttachments1).isNotEqualTo(exculpatoryAttachments2);
        exculpatoryAttachments1.setId(null);
        assertThat(exculpatoryAttachments1).isNotEqualTo(exculpatoryAttachments2);
    }
}
