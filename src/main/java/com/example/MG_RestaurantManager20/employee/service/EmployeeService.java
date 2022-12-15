package com.example.MG_RestaurantManager20.employee.service;

import com.example.MG_RestaurantManager20.employee.domain.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface EmployeeService {

    Employee addNewEmployee(Employee employee);

    List<Employee> getEmployeesByUserSessionId(Long userId);

    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Long employeeId);

    Optional<Employee> getEmployeeByEmail(String email);

    Employee updateEmployee(Long employeeId, Employee employee);

    void deleteEmployee(Long employeeId);

    void deleteAllEmployee();

    void deleteEmployees(Set<Employee> employees);

}
