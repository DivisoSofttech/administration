package com.diviso.graeshoppe.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class CancelledAuxilaryOrderLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CancelledAuxilaryOrderLine.class);
        CancelledAuxilaryOrderLine cancelledAuxilaryOrderLine1 = new CancelledAuxilaryOrderLine();
        cancelledAuxilaryOrderLine1.setId(1L);
        CancelledAuxilaryOrderLine cancelledAuxilaryOrderLine2 = new CancelledAuxilaryOrderLine();
        cancelledAuxilaryOrderLine2.setId(cancelledAuxilaryOrderLine1.getId());
        assertThat(cancelledAuxilaryOrderLine1).isEqualTo(cancelledAuxilaryOrderLine2);
        cancelledAuxilaryOrderLine2.setId(2L);
        assertThat(cancelledAuxilaryOrderLine1).isNotEqualTo(cancelledAuxilaryOrderLine2);
        cancelledAuxilaryOrderLine1.setId(null);
        assertThat(cancelledAuxilaryOrderLine1).isNotEqualTo(cancelledAuxilaryOrderLine2);
    }
}
