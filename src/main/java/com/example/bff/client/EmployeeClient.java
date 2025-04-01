package com.example.bff.client;

import com.example.bff.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class EmployeeClient {

    private final RestTemplate restTemplate;
    private String backendApiUrl;

    public EmployeeClient(RestTemplate restTemplate, @Value("${backend.api.url}") String backendApiUrl) {
        this.restTemplate = restTemplate;
        this.backendApiUrl = backendApiUrl;
    }

    public List<EmployeeDTO> getAllEmployees() {
        ResponseEntity<List> responseEntity = restTemplate.exchange(
                backendApiUrl + "/api/v1/employees",
                HttpMethod.GET,
                null,
                List.class
        );
        return responseEntity.getBody();
    }

    public EmployeeDTO getEmployeeById(Long id) {
        ResponseEntity<EmployeeDTO> responseEntity = restTemplate.exchange(
                backendApiUrl + "/api/v1/employees/" + id,
                HttpMethod.GET,
                null,
                EmployeeDTO.class
        );
        return responseEntity.getBody();
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<EmployeeDTO> requestEntity = new HttpEntity<>(employeeDTO, headers);
        ResponseEntity<EmployeeDTO> responseEntity = restTemplate.exchange(
                backendApiUrl + "/api/v1/employees/add",
                HttpMethod.POST,
                requestEntity,
                EmployeeDTO.class
        );
        return responseEntity.getBody();
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        HttpEntity<EmployeeDTO> requestEntity = new HttpEntity<>(employeeDTO);
        ResponseEntity<EmployeeDTO> responseEntity = restTemplate.exchange(
                backendApiUrl + "/api/v1/employees/update",
                HttpMethod.PUT,
                requestEntity,
                EmployeeDTO.class
        );
        return responseEntity.getBody();
    }

    public void deleteEmployee(Long id) {
        restTemplate.exchange(
                backendApiUrl + "/api/v1/employees/delete/" + id,
                HttpMethod.DELETE,
                null,
                Void.class
        );
    }
}
