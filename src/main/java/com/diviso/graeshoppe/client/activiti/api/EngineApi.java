/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (3.0.0-SNAPSHOT).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.diviso.graeshoppe.client.activiti.api;

import java.util.Map;
import com.diviso.graeshoppe.client.activiti.model.ProcessEngineInfoResponse;
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

@Api(value = "Engine", description = "the Engine API")
public interface EngineApi {

    @ApiOperation(value = "Get engine info", nickname = "getEngineInfo", notes = "", response = ProcessEngineInfoResponse.class, authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Engine", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Indicates the engine info is returned.", response = ProcessEngineInfoResponse.class) })
    @RequestMapping(value = "/management/engine",
        produces = "application/json", 
        method = RequestMethod.GET)
    ResponseEntity<ProcessEngineInfoResponse> getEngineInfo();


    @ApiOperation(value = "Get engine properties", nickname = "getProperties", notes = "", response = String.class, responseContainer = "Map", authorizations = {
        @Authorization(value = "basicAuth")
    }, tags={ "Engine", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Indicates the properties are returned.", response = Map.class, responseContainer = "Map") })
    @RequestMapping(value = "/management/properties",
        produces = "application/json", 
        method = RequestMethod.GET)
    ResponseEntity<Map<String, String>> getProperties();

}
