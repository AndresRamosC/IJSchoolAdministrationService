package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class ExculpatoryAttContentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExculpatoryAttContent.class);
        ExculpatoryAttContent exculpatoryAttContent1 = new ExculpatoryAttContent();
        exculpatoryAttContent1.setId(1L);
        ExculpatoryAttContent exculpatoryAttContent2 = new ExculpatoryAttContent();
        exculpatoryAttContent2.setId(exculpatoryAttContent1.getId());
        assertThat(exculpatoryAttContent1).isEqualTo(exculpatoryAttContent2);
        exculpatoryAttContent2.setId(2L);
        assertThat(exculpatoryAttContent1).isNotEqualTo(exculpatoryAttContent2);
        exculpatoryAttContent1.setId(null);
        assertThat(exculpatoryAttContent1).isNotEqualTo(exculpatoryAttContent2);
    }
}
