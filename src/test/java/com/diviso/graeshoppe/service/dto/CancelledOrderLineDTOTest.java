package com.diviso.graeshoppe.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class CancelledOrderLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CancelledOrderLineDTO.class);
        CancelledOrderLineDTO cancelledOrderLineDTO1 = new CancelledOrderLineDTO();
        cancelledOrderLineDTO1.setId(1L);
        CancelledOrderLineDTO cancelledOrderLineDTO2 = new CancelledOrderLineDTO();
        assertThat(cancelledOrderLineDTO1).isNotEqualTo(cancelledOrderLineDTO2);
        cancelledOrderLineDTO2.setId(cancelledOrderLineDTO1.getId());
        assertThat(cancelledOrderLineDTO1).isEqualTo(cancelledOrderLineDTO2);
        cancelledOrderLineDTO2.setId(2L);
        assertThat(cancelledOrderLineDTO1).isNotEqualTo(cancelledOrderLineDTO2);
        cancelledOrderLineDTO1.setId(null);
        assertThat(cancelledOrderLineDTO1).isNotEqualTo(cancelledOrderLineDTO2);
    }
}
