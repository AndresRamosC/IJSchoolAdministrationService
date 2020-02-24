package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class ExculpatoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Exculpatory.class);
        Exculpatory exculpatory1 = new Exculpatory();
        exculpatory1.setId(1L);
        Exculpatory exculpatory2 = new Exculpatory();
        exculpatory2.setId(exculpatory1.getId());
        assertThat(exculpatory1).isEqualTo(exculpatory2);
        exculpatory2.setId(2L);
        assertThat(exculpatory1).isNotEqualTo(exculpatory2);
        exculpatory1.setId(null);
        assertThat(exculpatory1).isNotEqualTo(exculpatory2);
    }
}
