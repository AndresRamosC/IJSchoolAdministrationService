package com.ijrobotics.ijschoolmanageradministrationservice.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class AssignmentAssignedTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssignmentAssigned.class);
        AssignmentAssigned assignmentAssigned1 = new AssignmentAssigned();
        assignmentAssigned1.setId(1L);
        AssignmentAssigned assignmentAssigned2 = new AssignmentAssigned();
        assignmentAssigned2.setId(assignmentAssigned1.getId());
        assertThat(assignmentAssigned1).isEqualTo(assignmentAssigned2);
        assignmentAssigned2.setId(2L);
        assertThat(assignmentAssigned1).isNotEqualTo(assignmentAssigned2);
        assignmentAssigned1.setId(null);
        assertThat(assignmentAssigned1).isNotEqualTo(assignmentAssigned2);
    }
}
