package com.diviso.graeshoppe.service.impl;


import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diviso.graeshoppe.client.activiti.api.FormsApi;
import com.diviso.graeshoppe.client.activiti.api.ProcessInstancesApi;
import com.diviso.graeshoppe.client.activiti.api.TasksApi;
import com.diviso.graeshoppe.client.activiti.custom.InitiateCancelation;
import com.diviso.graeshoppe.client.activiti.model.DataResponse;
import com.diviso.graeshoppe.client.activiti.model.ProcessInstanceCreateRequest;
import com.diviso.graeshoppe.client.activiti.model.ProcessInstanceResponse;
import com.diviso.graeshoppe.client.activiti.model.RestFormProperty;
import com.diviso.graeshoppe.client.activiti.model.RestVariable;
import com.diviso.graeshoppe.client.activiti.model.SubmitFormRequest;
import com.diviso.graeshoppe.client.activiti.model.TaskActionRequest;
import com.diviso.graeshoppe.client.activiti.model.TaskRequest;
import com.diviso.graeshoppe.domain.CancellationRequest;
import com.diviso.graeshoppe.domain.RefundDetails;
import com.diviso.graeshoppe.repository.CancellationRequestRepository;
import com.diviso.graeshoppe.repository.search.CancellationRequestSearchRepository;
import com.diviso.graeshoppe.service.CancellationRequestService;
import com.diviso.graeshoppe.service.dto.CancellationRequestDTO;
import com.diviso.graeshoppe.service.mapper.CancellationRequestMapper;

/**
 * Service Implementation for managing {@link CancellationRequest}.
 */
@Service
@Transactional
public class CancellationRequestServiceImpl implements CancellationRequestService {

	@Autowired
	TasksApi tasksApi;
	
	@Autowired
	FormsApi formsApi;
	
	
	
	
	
	
	@Autowired
	ProcessInstancesApi	processInstanceApi;
    private final Logger log = LoggerFactory.getLogger(CancellationRequestServiceImpl.class);

    private final CancellationRequestRepository cancellationRequestRepository;

    private final CancellationRequestMapper cancellationRequestMapper;

    private final CancellationRequestSearchRepository cancellationRequestSearchRepository;

    public CancellationRequestServiceImpl(CancellationRequestRepository cancellationRequestRepository, CancellationRequestMapper cancellationRequestMapper, CancellationRequestSearchRepository cancellationRequestSearchRepository) {
        this.cancellationRequestRepository = cancellationRequestRepository;
        this.cancellationRequestMapper = cancellationRequestMapper;
        this.cancellationRequestSearchRepository = cancellationRequestSearchRepository;
    }

    /**
     * Save a cancellationRequest.
     *
     * @param cancellationRequestDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CancellationRequestDTO save(CancellationRequestDTO cancellationRequestDTO) {
        log.debug("Request to save CancellationRequest : {}", cancellationRequestDTO);
        
        /*initiating workflow and setting processInstance id into the reference variable */
        String processInstanceId= initiate(); 
        cancellationRequestDTO.setReference(processInstanceId);
        
       
        CancellationRequest cancellationRequest = cancellationRequestMapper.toEntity(cancellationRequestDTO);
        cancellationRequest = cancellationRequestRepository.save(cancellationRequest);
        CancellationRequestDTO result = cancellationRequestMapper.toDto(cancellationRequest);
        cancellationRequestSearchRepository.save(cancellationRequest);
        
        
        /*initiating workflow and setting processInstance id into the reference variable */

        ResponseEntity<DataResponse> dataResponse=tasksApi.getTasks(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, processInstanceId, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        List<LinkedHashMap<String, String>> myTasks = (List<LinkedHashMap<String, String>>)  dataResponse.getBody().getData();
    	
		log.debug(" \n \n XXXXXXXXXXXXXXXXXXXXProcessInstanceIDXXXXXXXXXXXXXXXXXXX"+processInstanceId);

		log.debug(" \n \n XXXXXXXXXXXXXXXXXXXXtaskidXXXXXXXXXXXXXXXXXXX"+myTasks.get(0).get("id"));
		
		String taskId=myTasks.get(0).get("id");     
		initiateCancelation(result, taskId);
		
        return result;
    }

    /**
     * Get all the cancellationRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CancellationRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CancellationRequests");
        return cancellationRequestRepository.findAll(pageable)
            .map(cancellationRequestMapper::toDto);
    }


    /**
     * Get one cancellationRequest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CancellationRequestDTO> findOne(Long id) {
        log.debug("Request to get CancellationRequest : {}", id);
        return cancellationRequestRepository.findById(id)
            .map(cancellationRequestMapper::toDto);
    }

    /**
     * Delete the cancellationRequest by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CancellationRequest : {}", id);
        cancellationRequestRepository.deleteById(id);
        cancellationRequestSearchRepository.deleteById(id);
    }

    /**
     * Search for the cancellationRequest corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CancellationRequestDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CancellationRequests for query {}", query);
        return cancellationRequestSearchRepository.search(queryStringQuery(query), pageable)
            .map(cancellationRequestMapper::toDto);
    }


	//////////////////////////////activiti//////////////////////////////
	


	
	public String initiate() {
	
		ProcessInstanceCreateRequest processInstanceCreateRequest=new ProcessInstanceCreateRequest();
   		List<RestVariable> variables=new ArrayList<RestVariable>();
   		

   		processInstanceCreateRequest.setProcessDefinitionId("oderCancel:3:60004");
   		log.info("*****************************************************"+processInstanceCreateRequest.getProcessDefinitionId());
   		RestVariable cancellationRequestRestVariable=new RestVariable();
   		cancellationRequestRestVariable.setName("cancellationRequestRestVariable");
   		cancellationRequestRestVariable.setType("string");
   		cancellationRequestRestVariable.setValue("cancellationRequestRestVariable");
   		variables.add(cancellationRequestRestVariable);   	
   		
   		
   		log.info("*****************************************************"+variables.size());
   		processInstanceCreateRequest.setVariables(variables);
   		log.info("*****************************************************"+processInstanceCreateRequest.getVariables());
   		
   		ResponseEntity<ProcessInstanceResponse> processInstanceResponse = processInstanceApi
				.createProcessInstance(processInstanceCreateRequest);
		String processInstanceId = processInstanceResponse.getBody().getId();
		String processDefinitionId = processInstanceCreateRequest.getProcessDefinitionId();
		log.info("++++++++++++++++processDefinitionId++++++++++++++++++"+ processDefinitionId);
		log.info("++++++++++++++++ProcessInstanceId is+++++++++++++ " + processInstanceId);
		
   		processInstanceApi.createProcessInstance(processInstanceCreateRequest);
   		
		
		return processInstanceId;
		
	}
	
	public void initiateCancelation(CancellationRequestDTO cancellationRequestDTO ,String taskId) {
		
		log.debug("###########################################taskid="+taskId);
		log.debug("###########################################cancellationRequestDTO="+cancellationRequestDTO);


		TaskActionRequest taskActionRequest=new TaskActionRequest();
		
	
		
		
		List<RestVariable> restVariables=new ArrayList<RestVariable>();
		
		RestVariable variable=new RestVariable();
		variable.setName("cancellation");
		variable.setValue(cancellationRequestDTO);
		restVariables.add(variable);
		taskActionRequest.setVariables(restVariables);
		taskActionRequest.setAction("complete");
		

		
		 tasksApi.executeTaskAction(taskId, taskActionRequest);
		
		
		
		
	}

	@Override
	public ResponseEntity<DataResponse> getTasks(String name, String nameLike, String description, String priority,
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
			@Valid String candidateOrAssigned, @Valid String category) {
		// TODO Auto-generated method stub
		
		log.debug("########################################### processs instance id="+processInstanceId);

		
		return tasksApi.getTasks(name, nameLike, description, priority, minimumPriority, maximumPriority, assignee, assigneeLike, owner, ownerLike, unassigned, delegationState, candidateUser, candidateGroup, candidateGroups, involvedUser, taskDefinitionKey, taskDefinitionKeyLike, processInstanceId, processInstanceBusinessKey, processInstanceBusinessKeyLike, processDefinitionId, processDefinitionKey, processDefinitionKeyLike, processDefinitionName, processDefinitionNameLike, executionId, createdOn, createdBefore, createdAfter, dueOn, dueBefore, dueAfter, withoutDueDate, excludeSubTasks, active, includeTaskLocalVariables, includeProcessVariables, tenantId, tenantIdLike, withoutTenantId, candidateOrAssigned, category);
	}

	

	

	
/*method to update cancellation request with r id */
	
	public String updateCancellationRequest(String orederId,RefundDetails refundDetails) {
		
		CancellationRequest cancellationRequest=this.cancellationRequestRepository.findByOrderId(orederId).get();
		cancellationRequest.setRefundDetails(refundDetails);
		cancellationRequest.setStatus("accepted");
		cancellationRequestRepository.save(cancellationRequest);
		 cancellationRequestSearchRepository.save(cancellationRequest);
		return cancellationRequest.getReference();
		
	}
	
	
	
}
