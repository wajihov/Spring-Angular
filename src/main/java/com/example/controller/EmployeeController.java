package com.example.controller;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Employee;
import com.example.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/employees")
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @PostMapping("/employees")
    public Employee save(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee Not Existe with id : " + id));
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable("id") Long id) {
        Employee employeeCurrent = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Existe with id : " + id));
        if (employee.getFirstName() != null)
            employeeCurrent.setFirstName(employee.getFirstName());
        if (employee.getLastName() != null)
            employeeCurrent.setLastName(employee.getLastName());
        if (employee.getEmailId() != null)
            employeeCurrent.setEmailId(employee.getEmailId());
        Employee updateEmployee = employeeRepository.save(employeeCurrent);
        return ResponseEntity.ok(updateEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Employee Not Existe with id : " + id));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Delete", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }


}
