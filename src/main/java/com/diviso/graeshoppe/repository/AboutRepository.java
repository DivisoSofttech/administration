package com.diviso.graeshoppe.repository;

import com.diviso.graeshoppe.domain.About;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the About entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AboutRepository extends JpaRepository<About, Long> {

}
