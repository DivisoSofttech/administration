package com.diviso.graeshoppe.repository;

import com.diviso.graeshoppe.domain.RefoundDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RefoundDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefoundDetailsRepository extends JpaRepository<RefoundDetails, Long> {

}
