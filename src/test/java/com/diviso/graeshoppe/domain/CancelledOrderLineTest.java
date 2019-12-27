package com.diviso.graeshoppe.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.diviso.graeshoppe.web.rest.TestUtil;

public class CancelledOrderLineTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CancelledOrderLine.class);
        CancelledOrderLine cancelledOrderLine1 = new CancelledOrderLine();
        cancelledOrderLine1.setId(1L);
        CancelledOrderLine cancelledOrderLine2 = new CancelledOrderLine();
        cancelledOrderLine2.setId(cancelledOrderLine1.getId());
        assertThat(cancelledOrderLine1).isEqualTo(cancelledOrderLine2);
        cancelledOrderLine2.setId(2L);
        assertThat(cancelledOrderLine1).isNotEqualTo(cancelledOrderLine2);
        cancelledOrderLine1.setId(null);
        assertThat(cancelledOrderLine1).isNotEqualTo(cancelledOrderLine2);
    }
}
