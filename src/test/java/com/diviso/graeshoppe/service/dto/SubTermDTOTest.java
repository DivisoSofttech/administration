package com.diviso.graeshoppe.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class SubTermDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubTermDTO.class);
        SubTermDTO subTermDTO1 = new SubTermDTO();
        subTermDTO1.setId(1L);
        SubTermDTO subTermDTO2 = new SubTermDTO();
        assertThat(subTermDTO1).isNotEqualTo(subTermDTO2);
        subTermDTO2.setId(subTermDTO1.getId());
        assertThat(subTermDTO1).isEqualTo(subTermDTO2);
        subTermDTO2.setId(2L);
        assertThat(subTermDTO1).isNotEqualTo(subTermDTO2);
        subTermDTO1.setId(null);
        assertThat(subTermDTO1).isNotEqualTo(subTermDTO2);
    }
}
