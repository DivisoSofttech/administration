/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (3.0.0-SNAPSHOT).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.diviso.graeshoppe.client.activiti.api;

import com.diviso.graeshoppe.client.activiti.model.DataResponse;
import com.diviso.graeshoppe.client.activiti.model.ModelRequest;
import com.diviso.graeshoppe.client.activiti.model.ModelResponse;
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

@Api(value = "Models", description = "the Models API")
public interface ModelsApi {

    @ApiOperation(value = "Create a model", nickname = "createModel", notes = "All request values are optional. For example, you can only include the name attribute in the request body JSON-object, only setting the name of the model, leaving all other fields null.", response = ModelResponse.class, authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Models", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Indicates the model was created.", response = ModelResponse.class) })
    @RequestMapping(value = "/repository/models",
        produces = "application/json", 
        method = RequestMethod.POST)
    ResponseEntity<ModelResponse> createModel(@ApiParam(value = ""  )  @Valid @RequestBody ModelRequest modelRequest);


    @ApiOperation(value = "Delete a model", nickname = "deleteModel", notes = "", authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Models", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Indicates the model was found and has been deleted. Response-body is intentionally empty."),
        @ApiResponse(code = 404, message = "Indicates the requested model was not found.") })
    @RequestMapping(value = "/repository/models/{modelId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteModel(@ApiParam(value = "The id of the model to delete.",required=true) @PathVariable("modelId") String modelId);


    @ApiOperation(value = "Get a model", nickname = "getModel", notes = "", response = ModelResponse.class, authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Models", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Indicates the model was found and returned.", response = ModelResponse.class),
        @ApiResponse(code = 404, message = "Indicates the requested model was not found.") })
    @RequestMapping(value = "/repository/models/{modelId}",
        produces = "application/json", 
        method = RequestMethod.GET)
    ResponseEntity<ModelResponse> getModel(@ApiParam(value = "The id of the model to get.",required=true) @PathVariable("modelId") String modelId);


    @ApiOperation(value = "Get a list of models", nickname = "getModels", notes = "", response = DataResponse.class, authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Models", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Indicates request was successful and the models are returned", response = DataResponse.class),
        @ApiResponse(code = 400, message = "Indicates a parameter was passed in the wrong format. The status-message contains additional information.") })
    @RequestMapping(value = "/repository/models",
        produces = "application/json", 
        method = RequestMethod.GET)
    ResponseEntity<DataResponse> getModels(@ApiParam(value = "Only return models with the given version.") @Valid @RequestParam(value = "id", required = false) String id,@ApiParam(value = "Only return models with the given category.") @Valid @RequestParam(value = "category", required = false) String category,@ApiParam(value = "Only return models with a category like the given name.") @Valid @RequestParam(value = "categoryLike", required = false) String categoryLike,@ApiParam(value = "Only return models which don�t have the given category.") @Valid @RequestParam(value = "categoryNotEquals", required = false) String categoryNotEquals,@ApiParam(value = "Only return models with the given name.") @Valid @RequestParam(value = "name", required = false) String name,@ApiParam(value = "Only return models with a name like the given name.") @Valid @RequestParam(value = "nameLike", required = false) String nameLike,@ApiParam(value = "Only return models with the given key.") @Valid @RequestParam(value = "key", required = false) String key,@ApiParam(value = "Only return models with the given category.") @Valid @RequestParam(value = "deploymentId", required = false) String deploymentId,@ApiParam(value = "Only return models with the given version.") @Valid @RequestParam(value = "version", required = false) Integer version,@ApiParam(value = "If true, only return models which are the latest version. Best used in combination with key. If false is passed in as value, this is ignored and all versions are returned.") @Valid @RequestParam(value = "latestVersion", required = false) Boolean latestVersion,@ApiParam(value = "If true, only deployed models are returned. If false, only undeployed models are returned (deploymentId is null).") @Valid @RequestParam(value = "deployed", required = false) Boolean deployed,@ApiParam(value = "Only return models with the given tenantId.") @Valid @RequestParam(value = "tenantId", required = false) String tenantId,@ApiParam(value = "Only return models with a tenantId like the given value.") @Valid @RequestParam(value = "tenantIdLike", required = false) String tenantIdLike,@ApiParam(value = "If true, only returns models without a tenantId set. If false, the withoutTenantId parameter is ignored.") @Valid @RequestParam(value = "withoutTenantId", required = false) Boolean withoutTenantId,@ApiParam(value = "Property to sort on, to be used together with the order.", allowableValues = "id, category, createTime, key, lastUpdateTime, name, version, tenantId") @Valid @RequestParam(value = "sort", required = false) String sort);


    @ApiOperation(value = "Update a model", nickname = "updateModel", notes = "All request values are optional. For example, you can only include the name attribute in the request body JSON-object, only updating the name of the model, leaving all other fields unaffected. When an attribute is explicitly included and is set to null, the model-value will be updated to null. Example: ```JSON  {\"metaInfo\" : null}``` will clear the metaInfo of the model).", response = ModelResponse.class, authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Models", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Indicates the model was found and updated.", response = ModelResponse.class),
        @ApiResponse(code = 404, message = "Indicates the requested model was not found.") })
    @RequestMapping(value = "/repository/models/{modelId}",
        produces = "application/json", 
        method = RequestMethod.PUT)
    ResponseEntity<ModelResponse> updateModel(@ApiParam(value = "",required=true) @PathVariable("modelId") String modelId,@ApiParam(value = ""  )  @Valid @RequestBody ModelRequest modelRequest);

}
