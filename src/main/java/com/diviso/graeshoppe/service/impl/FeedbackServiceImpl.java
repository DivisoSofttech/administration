package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.EmailService;
import com.diviso.graeshoppe.service.FeedbackService;
import com.diviso.graeshoppe.service.TicketIdGeneratorService;
import com.diviso.graeshoppe.domain.Feedback;
import com.diviso.graeshoppe.repository.FeedbackRepository;
import com.diviso.graeshoppe.repository.search.FeedbackSearchRepository;
import com.diviso.graeshoppe.service.dto.FeedbackDTO;
import com.diviso.graeshoppe.service.dto.TicketIdGeneratorDTO;
import com.diviso.graeshoppe.service.mapper.FeedbackMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Feedback}.
 */
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

	private final Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);

	private final FeedbackRepository feedbackRepository;

	private final FeedbackMapper feedbackMapper;

	private final FeedbackSearchRepository feedbackSearchRepository;

	private final EmailService emailService;

	@Autowired
	private TicketIdGeneratorService idGeneratorService;

	public FeedbackServiceImpl(FeedbackRepository feedbackRepository, FeedbackMapper feedbackMapper,
			FeedbackSearchRepository feedbackSearchRepository, EmailService emailService) {
		this.feedbackRepository = feedbackRepository;
		this.feedbackMapper = feedbackMapper;
		this.feedbackSearchRepository = feedbackSearchRepository;
		this.emailService = emailService;
	}

	/**
	 * Save a feedback.
	 *
	 * @param feedbackDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public FeedbackDTO save(FeedbackDTO feedbackDTO) {
		log.debug("Request to save Feedback : {}", feedbackDTO);
		TicketIdGeneratorDTO ticketId = idGeneratorService.save(new TicketIdGeneratorDTO());
		feedbackDTO.setTicketId("TK-" + ticketId.getId());
		Feedback feedback = feedbackMapper.toEntity(feedbackDTO);

		feedback = feedbackRepository.save(feedback);
		emailService.sendEmail(feedbackDTO.getCustomerEmail(), feedbackDTO.getSubject(), feedbackDTO.getQuery());
		FeedbackDTO result = feedbackMapper.toDto(feedback);
		feedbackSearchRepository.save(feedback);
		return result;
	}

	/**
	 * Get all the feedbacks.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<FeedbackDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Feedbacks");
		return feedbackRepository.findAll(pageable).map(feedbackMapper::toDto);
	}

	/**
	 * Get one feedback by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<FeedbackDTO> findOne(Long id) {
		log.debug("Request to get Feedback : {}", id);
		return feedbackRepository.findById(id).map(feedbackMapper::toDto);
	}

	/**
	 * Delete the feedback by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Feedback : {}", id);
		feedbackRepository.deleteById(id);
		feedbackSearchRepository.deleteById(id);
	}

	/**
	 * Search for the feedback corresponding to the query.
	 *
	 * @param query    the query of the search.
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<FeedbackDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Feedbacks for query {}", query);
		return feedbackSearchRepository.search(queryStringQuery(query), pageable).map(feedbackMapper::toDto);
	}
}
