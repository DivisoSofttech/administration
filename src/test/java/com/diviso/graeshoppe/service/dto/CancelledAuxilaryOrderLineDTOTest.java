package com.diviso.graeshoppe.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class CancelledAuxilaryOrderLineDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CancelledAuxilaryOrderLineDTO.class);
        CancelledAuxilaryOrderLineDTO cancelledAuxilaryOrderLineDTO1 = new CancelledAuxilaryOrderLineDTO();
        cancelledAuxilaryOrderLineDTO1.setId(1L);
        CancelledAuxilaryOrderLineDTO cancelledAuxilaryOrderLineDTO2 = new CancelledAuxilaryOrderLineDTO();
        assertThat(cancelledAuxilaryOrderLineDTO1).isNotEqualTo(cancelledAuxilaryOrderLineDTO2);
        cancelledAuxilaryOrderLineDTO2.setId(cancelledAuxilaryOrderLineDTO1.getId());
        assertThat(cancelledAuxilaryOrderLineDTO1).isEqualTo(cancelledAuxilaryOrderLineDTO2);
        cancelledAuxilaryOrderLineDTO2.setId(2L);
        assertThat(cancelledAuxilaryOrderLineDTO1).isNotEqualTo(cancelledAuxilaryOrderLineDTO2);
        cancelledAuxilaryOrderLineDTO1.setId(null);
        assertThat(cancelledAuxilaryOrderLineDTO1).isNotEqualTo(cancelledAuxilaryOrderLineDTO2);
    }
}
