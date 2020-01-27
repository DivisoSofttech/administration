package com.diviso.graeshoppe.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class TermDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TermDTO.class);
        TermDTO termDTO1 = new TermDTO();
        termDTO1.setId(1L);
        TermDTO termDTO2 = new TermDTO();
        assertThat(termDTO1).isNotEqualTo(termDTO2);
        termDTO2.setId(termDTO1.getId());
        assertThat(termDTO1).isEqualTo(termDTO2);
        termDTO2.setId(2L);
        assertThat(termDTO1).isNotEqualTo(termDTO2);
        termDTO1.setId(null);
        assertThat(termDTO1).isNotEqualTo(termDTO2);
    }
}
