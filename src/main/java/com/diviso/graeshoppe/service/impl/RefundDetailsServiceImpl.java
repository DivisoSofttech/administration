package com.diviso.graeshoppe.service.impl;

import com.diviso.graeshoppe.service.CancellationRequestService;
import com.diviso.graeshoppe.service.KafkaMessagingService;
import com.diviso.graeshoppe.service.RefundDetailsService;
import com.diviso.graeshoppe.client.activiti.api.TasksApi;
import com.diviso.graeshoppe.client.activiti.model.DataResponse;
import com.diviso.graeshoppe.client.activiti.model.RestVariable;
import com.diviso.graeshoppe.client.activiti.model.TaskActionRequest;
import com.diviso.graeshoppe.domain.RefundDetails;
import com.diviso.graeshoppe.repository.RefundDetailsRepository;
import com.diviso.graeshoppe.repository.RefundDetailsRepository;
import com.diviso.graeshoppe.repository.search.RefundDetailsSearchRepository;
import com.diviso.graeshoppe.repository.search.RefundDetailsSearchRepository;
import com.diviso.graeshoppe.service.dto.RefundDetailsDTO;
import com.diviso.graeshoppe.service.mapper.RefundDetailsMapper;
import com.diviso.graeshoppe.service.mapper.RefundDetailsMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link RefoundDetails}.
 */
@Service
@Transactional
public class RefundDetailsServiceImpl implements RefundDetailsService {

	private final Logger log = LoggerFactory.getLogger(RefundDetailsServiceImpl.class);

	private final RefundDetailsRepository refundDetailsRepository;

	private final RefundDetailsMapper refundDetailsMapper;

	private final RefundDetailsSearchRepository refundDetailsSearchRepository;

	@Autowired
	private KafkaMessagingService kafkaMessagingService;
	@Autowired
	CancellationRequestServiceImpl cancellationRequestServiceImpl;

	@Autowired
	TasksApi tasksApi;

	public RefundDetailsServiceImpl(RefundDetailsRepository refundDetailsRepository,
			RefundDetailsMapper refundDetailsMapper, RefundDetailsSearchRepository refundDetailsSearchRepository) {
		this.refundDetailsRepository = refundDetailsRepository;
		this.refundDetailsMapper = refundDetailsMapper;
		this.refundDetailsSearchRepository = refundDetailsSearchRepository;
	}

	/**
	 * Save a refoundDetails.
	 *
	 * @param refoundDetailsDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public RefundDetailsDTO save(RefundDetailsDTO refundDetailsDTO) {
		log.debug("Request to save RefoundDetails : {}", refundDetailsDTO);
		RefundDetails refundDetails = refundDetailsMapper.toEntity(refundDetailsDTO);
		refundDetails = refundDetailsRepository.save(refundDetails);
		RefundDetailsDTO result = refundDetailsMapper.toDto(refundDetails);
		refundDetailsSearchRepository.save(refundDetails);

		return result;
	}

	/**
	 * Get all the refoundDetails.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<RefundDetailsDTO> findAll(Pageable pageable) {
		log.debug("Request to get all RefundDetails");
		return refundDetailsRepository.findAll(pageable).map(refundDetailsMapper::toDto);
	}

	/**
	 * Get one refoundDetails by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<RefundDetailsDTO> findOne(Long id) {
		log.debug("Request to get RefundDetails : {}", id);
		return refundDetailsRepository.findById(id).map(refundDetailsMapper::toDto);
	}

	/**
	 * Delete the refoundDetails by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete RefundDetails : {}", id);
		refundDetailsRepository.deleteById(id);
		refundDetailsSearchRepository.deleteById(id);
	}

	/**
	 * Search for the refoundDetails corresponding to the query.
	 *
	 * @param query    the query of the search.
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<RefundDetailsDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of RefundDetails for query {}", query);
		return refundDetailsSearchRepository.search(queryStringQuery(query), pageable).map(refundDetailsMapper::toDto);
	}

	//////////////////////////// activiti////////////////////////////////////

	@Override
	public RefundDetailsDTO save(RefundDetailsDTO refundDetailsDTO, String orderId) {
		log.debug("Request to save RefundDetails : {}", refundDetailsDTO);
		RefundDetails refundDetails = refundDetailsMapper.toEntity(refundDetailsDTO);
		refundDetails = refundDetailsRepository.save(refundDetails);
		RefundDetailsDTO result = refundDetailsMapper.toDto(refundDetails);
		refundDetailsSearchRepository.save(refundDetails);

		String processInstanceId = this.cancellationRequestServiceImpl.updateCancellationRequest(orderId,
				refundDetails);
		log.debug("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT processinstance id =" + processInstanceId);
		initiateRefund(processInstanceId);
		publishRefundDetails(refundDetailsDTO, orderId);
		return result;
	}

	public void publishRefundDetails(RefundDetailsDTO refundDetailsDTO, String orderId) {
		com.diviso.graeshoppe.avro.RefundDetails refundDetails = com.diviso.graeshoppe.avro.RefundDetails.newBuilder()
				.setOrderId(orderId).setRefundId(refundDetailsDTO.getRefundId()).setStatus(refundDetailsDTO.getStatus())
				.build();
		try {
			kafkaMessagingService.publishrefundDetails(refundDetails);
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void initiateRefund(String processInstanceId) {

		TaskActionRequest taskActionRequest = new TaskActionRequest();

		List<RestVariable> restVariables = new ArrayList<RestVariable>();

		RestVariable variable = new RestVariable();
		taskActionRequest.setVariables(restVariables);
		taskActionRequest.setAction("complete");
		DataResponse dataResponse = tasksApi.getTasks(null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, processInstanceId, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)
				.getBody();

		List<LinkedHashMap<Object, Object>> tasks = (List<LinkedHashMap<Object, Object>>) dataResponse.getData();

		log.debug(" \n \n got tasks are " + tasks.get(0).get("id") + "\n \n");

		String taskId = (String) tasks.get(0).get("id");

		tasksApi.executeTaskAction(taskId, taskActionRequest);

	}

}
