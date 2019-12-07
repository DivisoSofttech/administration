package com.diviso.graeshoppe.web.rest;
import com.diviso.graeshoppe.client.activiti.custom.InitiateCancelation;
import com.diviso.graeshoppe.client.activiti.model.DataResponse;
import com.diviso.graeshoppe.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.service.CancellationRequestService;
import com.diviso.graeshoppe.service.dto.CancellationRequestDTO;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CancellationRequest.
 */
@RestController
@RequestMapping("/api")
public class CancellationRequestResource {

    private final Logger log = LoggerFactory.getLogger(CancellationRequestResource.class);

    private static final String ENTITY_NAME = "administrationCancellationRequest";

    private final CancellationRequestService cancellationRequestService;

    public CancellationRequestResource(CancellationRequestService cancellationRequestService) {
        this.cancellationRequestService = cancellationRequestService;
    }

    /**
     * POST  /cancellation-requests : Create a new cancellationRequest.
     *
     * @param cancellationRequestDTO the cancellationRequestDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cancellationRequestDTO, or with status 400 (Bad Request) if the cancellationRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cancellation-requests")
    public ResponseEntity<CancellationRequestDTO> createCancellationRequest(@RequestBody CancellationRequestDTO cancellationRequestDTO) throws URISyntaxException {
        log.debug("REST request to save CancellationRequest : {}", cancellationRequestDTO);
        if (cancellationRequestDTO.getId() != null) {
            throw new BadRequestAlertException("A new cancellationRequest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CancellationRequestDTO result = cancellationRequestService.save(cancellationRequestDTO);
        return ResponseEntity.created(new URI("/api/cancellation-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cancellation-requests : Updates an existing cancellationRequest.
     *
     * @param cancellationRequestDTO the cancellationRequestDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cancellationRequestDTO,
     * or with status 400 (Bad Request) if the cancellationRequestDTO is not valid,
     * or with status 500 (Internal Server Error) if the cancellationRequestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cancellation-requests")
    public ResponseEntity<CancellationRequestDTO> updateCancellationRequest(@RequestBody CancellationRequestDTO cancellationRequestDTO) throws URISyntaxException {
        log.debug("REST request to update CancellationRequest : {}", cancellationRequestDTO);
        if (cancellationRequestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CancellationRequestDTO result = cancellationRequestService.save(cancellationRequestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cancellationRequestDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cancellation-requests : get all the cancellationRequests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cancellationRequests in body
     */
    @GetMapping("/cancellation-requests")
    public ResponseEntity<List<CancellationRequestDTO>> getAllCancellationRequests(Pageable pageable) {
        log.debug("REST request to get a page of CancellationRequests");
        Page<CancellationRequestDTO> page = cancellationRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cancellation-requests");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /cancellation-requests/:id : get the "id" cancellationRequest.
     *
     * @param id the id of the cancellationRequestDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cancellationRequestDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cancellation-requests/{id}")
    public ResponseEntity<CancellationRequestDTO> getCancellationRequest(@PathVariable Long id) {
        log.debug("REST request to get CancellationRequest : {}", id);
        Optional<CancellationRequestDTO> cancellationRequestDTO = cancellationRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cancellationRequestDTO);
    }

    /**
     * DELETE  /cancellation-requests/:id : delete the "id" cancellationRequest.
     *
     * @param id the id of the cancellationRequestDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cancellation-requests/{id}")
    public ResponseEntity<Void> deleteCancellationRequest(@PathVariable Long id) {
        log.debug("REST request to delete CancellationRequest : {}", id);
        cancellationRequestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cancellation-requests?query=:query : search for the cancellationRequest corresponding
     * to the query.
     *
     * @param query the query of the cancellationRequest search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/cancellation-requests")
    public ResponseEntity<List<CancellationRequestDTO>> searchCancellationRequests(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CancellationRequests for query {}", query);
        Page<CancellationRequestDTO> page = cancellationRequestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/cancellation-requests");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    ///////////////////////////////activity////////////////////////////////
    
    
//    @PostMapping("/initiate")
//	public ResponseEntity<DataResponse> initiate(@RequestBody CancellationRequestDTO cancellationRequestDTO) throws URISyntaxException{
//		
//		String processInstanceId = cancellationRequestService.initiate(cancellationRequestDTO);
//		
//		createCancellationRequest(cancellationRequestDTO);
//		
//		DataResponse dataResponse= getTasks(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,null, null, null, processInstanceId, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null).getBody();
//		
//		
//		
//		List<LinkedHashMap<String, String>> myTasks = (List<LinkedHashMap<String, String>>)  dataResponse.getData();
//		
//
//		log.debug(" \n \n XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+myTasks.get(0).get("id"));
//		
//		String taskId=myTasks.get(0).get("id");
//		
//		initiateCancelation(cancellationRequestDTO,taskId);
//		
//		return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
//		
//	}
//	
//	@PostMapping("/initiateCancelation/{taskId}")
//	public void initiateCancelation(@RequestBody CancellationRequestDTO cancellationRequestDTO,@PathVariable String taskId){
//		
//		
//		cancellationRequestService.initiateCancelation(cancellationRequestDTO,taskId);
//		
//	}
	
	@GetMapping("/tasks")
	public ResponseEntity<DataResponse> getTasks(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "nameLike", required = false) String nameLike,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "priority", required = false) String priority,
			@RequestParam(value = "minimumPriority", required = false) String minimumPriority,
			@RequestParam(value = "maximumPriority", required = false) String maximumPriority,
			@RequestParam(value = "assignee", required = false) String assignee,
			@RequestParam(value = "assigneeLike", required = false) String assigneeLike,
			@RequestParam(value = "owner", required = false) String owner,
			@RequestParam(value = "ownerLike", required = false) String ownerLike,
			@RequestParam(value = "unassigned", required = false) String unassigned,
			@RequestParam(value = "delegationState", required = false) String delegationState,
			@RequestParam(value = "candidateUser", required = false) String candidateUser,
			@RequestParam(value = "candidateGroup", required = false) String candidateGroup,
			@RequestParam(value = "candidateGroups", required = false) String candidateGroups,
			@RequestParam(value = "involvedUser", required = false) String involvedUser,
			@RequestParam(value = "taskDefinitionKey", required = false) String taskDefinitionKey,
			@RequestParam(value = "taskDefinitionKeyLike", required = false) String taskDefinitionKeyLike,
			@RequestParam(value = "processInstanceId", required = false) String processInstanceId,
			@RequestParam(value = "processInstanceBusinessKey", required = false) String processInstanceBusinessKey,
			@RequestParam(value = "processInstanceBusinessKeyLike", required = false) String processInstanceBusinessKeyLike,
			@ApiParam(value = "Only return tasks which are part of a process instance which has a process definition with the given id.") @Valid @RequestParam(value = "processDefinitionId", required = false) String processDefinitionId,
			@ApiParam(value = "Only return tasks which are part of a process instance which has a process definition with the given key.") @Valid @RequestParam(value = "processDefinitionKey", required = false) String processDefinitionKey,
			@ApiParam(value = "Only return tasks which are part of a process instance which has a process definition with a key like the given value.") @Valid @RequestParam(value = "processDefinitionKeyLike", required = false) String processDefinitionKeyLike,
			@ApiParam(value = "Only return tasks which are part of a process instance which has a process definition with the given name.") @Valid @RequestParam(value = "processDefinitionName", required = false) String processDefinitionName,
			@ApiParam(value = "Only return tasks which are part of a process instance which has a process definition with a name like the given value.") @Valid @RequestParam(value = "processDefinitionNameLike", required = false) String processDefinitionNameLike,
			@ApiParam(value = "Only return tasks which are part of the execution with the given id.") @Valid @RequestParam(value = "executionId", required = false) String executionId,
			@ApiParam(value = "Only return tasks which are created on the given date.") @Valid @RequestParam(value = "createdOn", required = false) String createdOn,
			@ApiParam(value = "Only return tasks which are created before the given date.") @Valid @RequestParam(value = "createdBefore", required = false) String createdBefore,
			@ApiParam(value = "Only return tasks which are created after the given date.") @Valid @RequestParam(value = "createdAfter", required = false) String createdAfter,
			@ApiParam(value = "Only return tasks which are due on the given date.") @Valid @RequestParam(value = "dueOn", required = false) String dueOn,
			@ApiParam(value = "Only return tasks which are due before the given date.") @Valid @RequestParam(value = "dueBefore", required = false) String dueBefore,
			@ApiParam(value = "Only return tasks which are due after the given date.") @Valid @RequestParam(value = "dueAfter", required = false) String dueAfter,
			@ApiParam(value = "Only return tasks which don�t have a due date. The property is ignored if the value is false.") @Valid @RequestParam(value = "withoutDueDate", required = false) Boolean withoutDueDate,
			@ApiParam(value = "Only return tasks that are not a subtask of another task.") @Valid @RequestParam(value = "excludeSubTasks", required = false) Boolean excludeSubTasks,
			@ApiParam(value = "If true, only return tasks that are not suspended (either part of a process that is not suspended or not part of a process at all). If false, only tasks that are part of suspended process instances are returned.") @Valid @RequestParam(value = "active", required = false) Boolean active,
			@ApiParam(value = "Indication to include task local variables in the result.") @Valid @RequestParam(value = "includeTaskLocalVariables", required = false) Boolean includeTaskLocalVariables,
			@ApiParam(value = "Indication to include process variables in the result.") @Valid @RequestParam(value = "includeProcessVariables", required = false) Boolean includeProcessVariables,
			@ApiParam(value = "Only return tasks with the given tenantId.") @Valid @RequestParam(value = "tenantId", required = false) String tenantId,
			@ApiParam(value = "Only return tasks with a tenantId like the given value.") @Valid @RequestParam(value = "tenantIdLike", required = false) String tenantIdLike,
			@ApiParam(value = "If true, only returns tasks without a tenantId set. If false, the withoutTenantId parameter is ignored.") @Valid @RequestParam(value = "withoutTenantId", required = false) Boolean withoutTenantId,
			@ApiParam(value = "Select tasks that has been claimed or assigned to user or waiting to claim by user (candidate user or groups).") @Valid @RequestParam(value = "candidateOrAssigned", required = false) String candidateOrAssigned,
			@ApiParam(value = "Select tasks with the given category. Note that this is the task category, not the category of the process definition (namespace within the BPMN Xml). ") @Valid @RequestParam(value = "category", required = false) String category) {

		return cancellationRequestService.getTasks(name, nameLike, description, priority, minimumPriority, maximumPriority,
				assignee, assigneeLike, owner, ownerLike, unassigned, delegationState, candidateUser, candidateGroup,
				candidateGroups, involvedUser, taskDefinitionKey, taskDefinitionKeyLike, processInstanceId,
				processInstanceBusinessKey, processInstanceBusinessKeyLike, processDefinitionId, processDefinitionKey,
				processDefinitionKeyLike, processDefinitionName, processDefinitionNameLike, executionId, createdOn,
				createdBefore, createdAfter, dueOn, dueBefore, dueAfter, withoutDueDate, excludeSubTasks, active,
				includeTaskLocalVariables, includeProcessVariables, tenantId, tenantIdLike, withoutTenantId,
				candidateOrAssigned, category);
	}
	
	
	
	

}
