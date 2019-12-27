package com.diviso.graeshoppe.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class RefoundDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefoundDetails.class);
        RefoundDetails refoundDetails1 = new RefoundDetails();
        refoundDetails1.setId(1L);
        RefoundDetails refoundDetails2 = new RefoundDetails();
        refoundDetails2.setId(refoundDetails1.getId());
        assertThat(refoundDetails1).isEqualTo(refoundDetails2);
        refoundDetails2.setId(2L);
        assertThat(refoundDetails1).isNotEqualTo(refoundDetails2);
        refoundDetails1.setId(null);
        assertThat(refoundDetails1).isNotEqualTo(refoundDetails2);
    }
}
