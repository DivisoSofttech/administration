package com.diviso.graeshoppe.repository;

import com.diviso.graeshoppe.domain.CancelledOrderLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CancelledOrderLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CancelledOrderLineRepository extends JpaRepository<CancelledOrderLine, Long> {

}
