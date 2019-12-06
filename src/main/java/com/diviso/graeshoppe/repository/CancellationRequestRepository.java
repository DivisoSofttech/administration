package com.diviso.graeshoppe.repository;

import com.diviso.graeshoppe.domain.CancellationRequest;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CancellationRequest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CancellationRequestRepository extends JpaRepository<CancellationRequest, Long> {

	Optional<CancellationRequest>findByOrderId(String orederId);

}
