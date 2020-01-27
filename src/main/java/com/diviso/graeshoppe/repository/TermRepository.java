package com.diviso.graeshoppe.repository;

import com.diviso.graeshoppe.domain.Term;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Term entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

}
