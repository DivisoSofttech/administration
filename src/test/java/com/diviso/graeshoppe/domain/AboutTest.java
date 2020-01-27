package com.diviso.graeshoppe.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class AboutTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(About.class);
        About about1 = new About();
        about1.setId(1L);
        About about2 = new About();
        about2.setId(about1.getId());
        assertThat(about1).isEqualTo(about2);
        about2.setId(2L);
        assertThat(about1).isNotEqualTo(about2);
        about1.setId(null);
        assertThat(about1).isNotEqualTo(about2);
    }
}
