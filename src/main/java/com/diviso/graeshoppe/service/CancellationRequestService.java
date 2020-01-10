package com.diviso.graeshoppe.service;

import com.diviso.graeshoppe.client.activiti.custom.InitiateCancelation;
import com.diviso.graeshoppe.client.activiti.model.DataResponse;
import com.diviso.graeshoppe.client.activiti.model.ProcessInstanceResponse;
import com.diviso.graeshoppe.service.dto.CancellationRequestDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import javax.validation.Valid;

/**
 * Service Interface for managing {@link com.diviso.graeshoppe.domain.CancellationRequest}.
 */
public interface CancellationRequestService {

    /**
     * Save a cancellationRequest.
     *
     * @param cancellationRequestDTO the entity to save.
     * @return the persisted entity.
     */
    CancellationRequestDTO save(CancellationRequestDTO cancellationRequestDTO);

    /**
     * Get all the cancellationRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CancellationRequestDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cancellationRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CancellationRequestDTO> findOne(Long id);

    /**
     * Delete the "id" cancellationRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the cancellationRequest corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CancellationRequestDTO> search(String query, Pageable pageable);



	public  ResponseEntity<DataResponse> getTasks(String name, String nameLike, String description, String priority,
			String minimumPriority, String maximumPriority, String assignee, String assigneeLike, String owner,
			String ownerLike, String unassigned, String delegationState, String candidateUser, String candidateGroup,
			String candidateGroups, String involvedUser, String taskDefinitionKey, String taskDefinitionKeyLike,
			String processInstanceId, String processInstanceBusinessKey, String processInstanceBusinessKeyLike,
			@Valid String processDefinitionId, @Valid String processDefinitionKey,
			@Valid String processDefinitionKeyLike, @Valid String processDefinitionName,
			@Valid String processDefinitionNameLike, @Valid String executionId, @Valid String createdOn,
			@Valid String createdBefore, @Valid String createdAfter, @Valid String dueOn, @Valid String dueBefore,
			@Valid String dueAfter, @Valid Boolean withoutDueDate, @Valid Boolean excludeSubTasks,
			@Valid Boolean active, @Valid Boolean includeTaskLocalVariables, @Valid Boolean includeProcessVariables,
			@Valid String tenantId, @Valid String tenantIdLike, @Valid Boolean withoutTenantId,
			@Valid String candidateOrAssigned, @Valid String category);

	void publishMesssage(String orderId);

	

}
