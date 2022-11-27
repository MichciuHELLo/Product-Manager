package com.example.MG_RestaurantManager20.employee.service.impl;

import com.example.MG_RestaurantManager20.employee.adapters.database.EmployeeRepository;
import com.example.MG_RestaurantManager20.employee.domain.Employee;
import com.example.MG_RestaurantManager20.employee.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    final private EmployeeRepository employeeRepository;

    @Override
    public Employee addNewEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    @Override
    public Optional<Employee> findProductByEmail(String email) {
        return employeeRepository.findProductByEmail(email);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Long employeeId, Employee employee) {

        employeeRepository.findById(employeeId).orElseThrow(() -> {
            throw new IllegalStateException("Employee with this ID: '" + employeeId + "' doesn't exists.");
        });

        employeeRepository.updateEmployeeById(employee.getFirstName(), employee.getSurname(), employee.getEmail(), employeeId);

        // TODO make an update
        System.out.println("ZMIENIONE!");
        System.out.printf("Employee: %d - %s %s %s%n", employeeId, employee.getFirstName(), employee.getSurname(), employee.getEmail());
        return employee;
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public void deleteAllEmployee() {
        employeeRepository.deleteAll();
    }

    @Override
    public void deleteEmployees(Set<Employee> employees) {
        var idList = employees.stream()
                .map(Employee::getId).collect(Collectors.toList());

        employeeRepository.deleteAllById(idList);
    }

}
