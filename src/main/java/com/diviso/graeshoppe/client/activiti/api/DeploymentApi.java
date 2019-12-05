/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (3.0.0-SNAPSHOT).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.diviso.graeshoppe.client.activiti.api;

import com.diviso.graeshoppe.client.activiti.model.DataResponse;
import com.diviso.graeshoppe.client.activiti.model.DeploymentResourceResponse;
import com.diviso.graeshoppe.client.activiti.model.DeploymentResponse;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2019-12-04T10:49:10.436+05:30[Asia/Calcutta]")

@Api(value = "Deployment", description = "the Deployment API")
public interface DeploymentApi {

    @ApiOperation(value = "Delete a deployment", nickname = "deleteDeployment", notes = "", authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Deployment", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Indicates the deployment was found and has been deleted. Response-body is intentionally empty."),
        @ApiResponse(code = 404, message = "Indicates the requested deployment was not found.") })
    @RequestMapping(value = "/repository/deployments/{deploymentId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteDeployment(@ApiParam(value = "The id of the deployment to delete.",required=true) @PathVariable("deploymentId") String deploymentId,@ApiParam(value = "") @Valid @RequestParam(value = "cascade", required = false) Boolean cascade);


    @ApiOperation(value = "Get a deployment", nickname = "getDeployment", notes = "", response = DeploymentResponse.class, authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Deployment", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Indicates the deployment was found and returned.", response = DeploymentResponse.class),
        @ApiResponse(code = 404, message = "Indicates the requested deployment was not found.") })
    @RequestMapping(value = "/repository/deployments/{deploymentId}",
        produces = "application/json", 
        method = RequestMethod.GET)
    ResponseEntity<DeploymentResponse> getDeployment(@ApiParam(value = "The id of the deployment to get.",required=true) @PathVariable("deploymentId") String deploymentId);


    @ApiOperation(value = "Get a deployment resource", nickname = "getDeploymentResource", notes = "Replace ** by ResourceId", response = DeploymentResourceResponse.class, authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Deployment", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Indicates both deployment and resource have been found and the resource has been returned.", response = DeploymentResourceResponse.class),
        @ApiResponse(code = 404, message = "Indicates the requested deployment was not found or there is no resource with the given id present in the deployment. The status-description contains additional information.") })
    @RequestMapping(value = "/repository/deployments/{deploymentId}/resources/**",
        produces = "application/json", 
        method = RequestMethod.GET)
    ResponseEntity<DeploymentResourceResponse> getDeploymentResource(@ApiParam(value = "The id of the deployment the requested resource is part of.",required=true) @PathVariable("deploymentId") String deploymentId);


    @ApiOperation(value = "Get a deployment resource content", nickname = "getDeploymentResourceData", notes = "The response body will contain the binary resource-content for the requested resource. The response content-type will be the same as the type returned in the resources mimeType property. Also, a content-disposition header is set, allowing browsers to download the file instead of displaying it.", response = byte[].class, responseContainer = "List", authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Deployment", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Indicates both deployment and resource have been found and the resource data has been returned.", response = byte[].class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Indicates the requested deployment was not found or there is no resource with the given id present in the deployment. The status-description contains additional information.") })
    @RequestMapping(value = "/repository/deployments/{deploymentId}/resourcedata/{resourceId}",
        produces = "*/*", 
        method = RequestMethod.GET)
    ResponseEntity<List<byte[]>> getDeploymentResourceData(@ApiParam(value = "The id of the deployment the requested resource is part of.",required=true) @PathVariable("deploymentId") String deploymentId,@ApiParam(value = "The id of the resource to get the data for. Make sure you URL-encode the resourceId in case it contains forward slashes. Eg: use diagrams%2Fmy-process.bpmn20.xml instead of diagrams/Fmy-process.bpmn20.xml.",required=true) @PathVariable("resourceId") String resourceId);


    @ApiOperation(value = "List resources in a deployment", nickname = "getDeploymentResources", notes = "The dataUrl property in the resulting JSON for a single resource contains the actual URL to use for retrieving the binary resource.", response = DeploymentResourceResponse.class, responseContainer = "List", authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Deployment", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Indicates the deployment was found and the resource list has been returned.", response = DeploymentResourceResponse.class, responseContainer = "List"),
        @ApiResponse(code = 404, message = "Indicates the requested deployment was not found.") })
    @RequestMapping(value = "/repository/deployments/{deploymentId}/resources",
        produces = "application/json", 
        method = RequestMethod.GET)
    ResponseEntity<List<DeploymentResourceResponse>> getDeploymentResources(@ApiParam(value = "The id of the deployment to get the resources for.",required=true) @PathVariable("deploymentId") String deploymentId);


    @ApiOperation(value = "List of Deployments", nickname = "getDeployments", notes = "", response = DataResponse.class, authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Deployment", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Indicates the request was successful.", response = DataResponse.class) })
    @RequestMapping(value = "/repository/deployments",
        produces = "application/json", 
        method = RequestMethod.GET)
    ResponseEntity<DataResponse> getDeployments(@ApiParam(value = "Only return deployments with the given name.") @Valid @RequestParam(value = "name", required = false) String name,@ApiParam(value = "Only return deployments with a name like the given name.") @Valid @RequestParam(value = "nameLike", required = false) String nameLike,@ApiParam(value = "Only return deployments with the given category.") @Valid @RequestParam(value = "category", required = false) String category,@ApiParam(value = "Only return deployments which don�t have the given category.") @Valid @RequestParam(value = "categoryNotEquals", required = false) String categoryNotEquals,@ApiParam(value = "Only return deployments with the given tenantId.") @Valid @RequestParam(value = "tenantId", required = false) String tenantId,@ApiParam(value = "Only return deployments with a tenantId like the given value.") @Valid @RequestParam(value = "tenantIdLike", required = false) String tenantIdLike,@ApiParam(value = "If true, only returns deployments without a tenantId set. If false, the withoutTenantId parameter is ignored.") @Valid @RequestParam(value = "withoutTenantId", required = false) String withoutTenantId,@ApiParam(value = "Property to sort on, to be used together with the order.", allowableValues = "id, name, deployTime, tenantId") @Valid @RequestParam(value = "sort", required = false) String sort);


    @ApiOperation(value = "Create a new deployment", nickname = "uploadDeployment", notes = "The request body should contain data of type multipart/form-data. There should be exactly one file in the request, any additional files will be ignored. If multiple resources need to be deployed in a single deployment, compress the resources in a zip and make sure the file-name ends with .bar or .zip.  An additional parameter (form-field) can be passed in the request body with name tenantId. The value of this field will be used as the id of the tenant this deployment is done in.", response = DeploymentResponse.class, authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Deployment", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Indicates the deployment was created.", response = DeploymentResponse.class),
        @ApiResponse(code = 400, message = "Indicates there was no content present in the request body or the content mime-type is not supported for deployment. The status-description contains additional information.") })
    @RequestMapping(value = "/repository/deployments",
        produces = "application/json", 
        method = RequestMethod.POST)
    ResponseEntity<DeploymentResponse> uploadDeployment(@ApiParam(value = "") @Valid @RequestParam(value = "tenantId", required = false) String tenantId);

}
