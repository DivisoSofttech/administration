package com.diviso.graeshoppe.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class SubTermTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubTerm.class);
        SubTerm subTerm1 = new SubTerm();
        subTerm1.setId(1L);
        SubTerm subTerm2 = new SubTerm();
        subTerm2.setId(subTerm1.getId());
        assertThat(subTerm1).isEqualTo(subTerm2);
        subTerm2.setId(2L);
        assertThat(subTerm1).isNotEqualTo(subTerm2);
        subTerm1.setId(null);
        assertThat(subTerm1).isNotEqualTo(subTerm2);
    }
}
