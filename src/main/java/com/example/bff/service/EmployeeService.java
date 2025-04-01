package com.example.bff.service;

import com.example.bff.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {

    private final RestTemplate restTemplate;

    @Value("${backend.api.url}")
    private String backendApiUrl;

    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<EmployeeDTO> getEmployees() {
        String url = UriComponentsBuilder.fromHttpUrl(backendApiUrl)
                .path("/api/v1/employees")
                .toUriString();

        EmployeeDTO[] response = restTemplate.getForObject(url, EmployeeDTO[].class);
        return Arrays.asList(response);
    }

    public EmployeeDTO getEmployeeById(Long id) {
        String url = UriComponentsBuilder.fromHttpUrl(backendApiUrl)
                .path("/api/v1/employees/{id}")
                .buildAndExpand(id)
                .toUriString();

        return restTemplate.getForObject(url, EmployeeDTO.class);
    }

    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        String url = UriComponentsBuilder.fromHttpUrl(backendApiUrl)
                .path("/api/v1/employees/add")
                .toUriString();

        return restTemplate.postForObject(url, employeeDTO, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        String url = UriComponentsBuilder.fromHttpUrl(backendApiUrl)
                .path("/api/v1/employees/update")
                .toUriString();

        restTemplate.put(url, employeeDTO);
        return employeeDTO;
    }

    public boolean deleteEmployee(Long id) {
        String url = UriComponentsBuilder.fromHttpUrl(backendApiUrl)
                .path("/api/v1/employees/delete/{id}")
                .buildAndExpand(id)
                .toUriString();

        restTemplate.delete(url);
        return true;
    }
}
