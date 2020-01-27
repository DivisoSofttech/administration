package com.diviso.graeshoppe.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class AboutDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AboutDTO.class);
        AboutDTO aboutDTO1 = new AboutDTO();
        aboutDTO1.setId(1L);
        AboutDTO aboutDTO2 = new AboutDTO();
        assertThat(aboutDTO1).isNotEqualTo(aboutDTO2);
        aboutDTO2.setId(aboutDTO1.getId());
        assertThat(aboutDTO1).isEqualTo(aboutDTO2);
        aboutDTO2.setId(2L);
        assertThat(aboutDTO1).isNotEqualTo(aboutDTO2);
        aboutDTO1.setId(null);
        assertThat(aboutDTO1).isNotEqualTo(aboutDTO2);
    }
}
