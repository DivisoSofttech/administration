package com.diviso.graeshoppe.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class RefundDetailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RefundDetailsDTO.class);
        RefundDetailsDTO refundDetailsDTO1 = new RefundDetailsDTO();
        refundDetailsDTO1.setId(1L);
        RefundDetailsDTO refundDetailsDTO2 = new RefundDetailsDTO();
        assertThat(refundDetailsDTO1).isNotEqualTo(refundDetailsDTO2);
        refundDetailsDTO2.setId(refundDetailsDTO1.getId());
        assertThat(refundDetailsDTO1).isEqualTo(refundDetailsDTO2);
        refundDetailsDTO2.setId(2L);
        assertThat(refundDetailsDTO1).isNotEqualTo(refundDetailsDTO2);
        refundDetailsDTO1.setId(null);
        assertThat(refundDetailsDTO1).isNotEqualTo(refundDetailsDTO2);
    }
}
