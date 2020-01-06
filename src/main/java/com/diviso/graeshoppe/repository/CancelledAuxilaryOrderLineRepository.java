package com.diviso.graeshoppe.repository;

import com.diviso.graeshoppe.domain.CancelledAuxilaryOrderLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CancelledAuxilaryOrderLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CancelledAuxilaryOrderLineRepository extends JpaRepository<CancelledAuxilaryOrderLine, Long> {

}
