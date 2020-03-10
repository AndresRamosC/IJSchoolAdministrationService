package com.ijrobotics.ijschoolmanageradministrationservice.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ijrobotics.ijschoolmanageradministrationservice.web.rest.TestUtil;

public class AssignmentAssignedDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssignmentAssignedDTO.class);
        AssignmentAssignedDTO assignmentAssignedDTO1 = new AssignmentAssignedDTO();
        assignmentAssignedDTO1.setId(1L);
        AssignmentAssignedDTO assignmentAssignedDTO2 = new AssignmentAssignedDTO();
        assertThat(assignmentAssignedDTO1).isNotEqualTo(assignmentAssignedDTO2);
        assignmentAssignedDTO2.setId(assignmentAssignedDTO1.getId());
        assertThat(assignmentAssignedDTO1).isEqualTo(assignmentAssignedDTO2);
        assignmentAssignedDTO2.setId(2L);
        assertThat(assignmentAssignedDTO1).isNotEqualTo(assignmentAssignedDTO2);
        assignmentAssignedDTO1.setId(null);
        assertThat(assignmentAssignedDTO1).isNotEqualTo(assignmentAssignedDTO2);
    }
}
