package com.diviso.graeshoppe.repository.search;

import com.diviso.graeshoppe.domain.TicketIdGenerator;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TicketIdGenerator} entity.
 */
public interface TicketIdGeneratorSearchRepository extends ElasticsearchRepository<TicketIdGenerator, Long> {
}
