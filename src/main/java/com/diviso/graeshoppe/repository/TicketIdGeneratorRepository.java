package com.diviso.graeshoppe.repository;

import com.diviso.graeshoppe.domain.TicketIdGenerator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TicketIdGenerator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketIdGeneratorRepository extends JpaRepository<TicketIdGenerator, Long> {

}
