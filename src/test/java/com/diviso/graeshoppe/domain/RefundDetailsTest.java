package com.diviso.graeshoppe.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class RefundDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefundDetails.class);
        RefundDetails refundDetails1 = new RefundDetails();
        refundDetails1.setId(1L);
        RefundDetails refundDetails2 = new RefundDetails();
        refundDetails2.setId(refundDetails1.getId());
        assertThat(refundDetails1).isEqualTo(refundDetails2);
        refundDetails2.setId(2L);
        assertThat(refundDetails1).isNotEqualTo(refundDetails2);
        refundDetails1.setId(null);
        assertThat(refundDetails1).isNotEqualTo(refundDetails2);
    }
}
