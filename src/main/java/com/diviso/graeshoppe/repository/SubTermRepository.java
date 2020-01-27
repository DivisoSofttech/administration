package com.diviso.graeshoppe.repository;

import com.diviso.graeshoppe.domain.SubTerm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SubTerm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubTermRepository extends JpaRepository<SubTerm, Long> {

}
