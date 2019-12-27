package com.diviso.graeshoppe.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class RefoundDetailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefoundDetailsDTO.class);
        RefoundDetailsDTO refoundDetailsDTO1 = new RefoundDetailsDTO();
        refoundDetailsDTO1.setId(1L);
        RefoundDetailsDTO refoundDetailsDTO2 = new RefoundDetailsDTO();
        assertThat(refoundDetailsDTO1).isNotEqualTo(refoundDetailsDTO2);
        refoundDetailsDTO2.setId(refoundDetailsDTO1.getId());
        assertThat(refoundDetailsDTO1).isEqualTo(refoundDetailsDTO2);
        refoundDetailsDTO2.setId(2L);
        assertThat(refoundDetailsDTO1).isNotEqualTo(refoundDetailsDTO2);
        refoundDetailsDTO1.setId(null);
        assertThat(refoundDetailsDTO1).isNotEqualTo(refoundDetailsDTO2);
    }
}
