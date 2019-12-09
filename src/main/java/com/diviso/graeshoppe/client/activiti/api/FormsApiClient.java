package com.diviso.graeshoppe.client.activiti.api;

import org.springframework.cloud.openfeign.FeignClient;
import com.diviso.graeshoppe.client.activiti.ClientConfiguration;

@FeignClient(name="${activiti.name:activiti}", url="${activiti.url:http://localhost:8080/activiti-rest/service}", configuration = ClientConfiguration.class)
public interface FormsApiClient extends FormsApi {
}