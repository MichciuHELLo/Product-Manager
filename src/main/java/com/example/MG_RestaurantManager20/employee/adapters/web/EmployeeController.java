package com.example.MG_RestaurantManager20.employee.adapters.web;

import com.example.MG_RestaurantManager20.employee.domain.Employee;
import com.example.MG_RestaurantManager20.employee.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // add employee
    @PostMapping("/employee/add")
    public Employee addNewEmployee(@RequestBody Employee employee) { return employeeService.addNewEmployee(employee); }

    // get all employees
    @GetMapping("/employee/all")
    public List<Employee> getAllEmployees() { return employeeService.getAllEmployees(); }

    // get employee
    @GetMapping("/employee/{employeeId}")
    public Optional<Employee> getEmployee(@PathVariable("employeeId") Long employeeId) { return employeeService.getEmployeeById(employeeId); }

    // edit employee
    @PutMapping("employee/{employeeId}")
    public Employee updateEmployee(@PathVariable("employeeId") Long employeeId, @RequestBody Employee employee) { return employeeService.updateEmployee(employeeId, employee); }

    // delete employee
    @DeleteMapping("/employee/{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") Long employeeId) { employeeService.deleteEmployee(employeeId); }

    @DeleteMapping("employee/all")
    public void deleteAllEmployees() { employeeService.deleteAllEmployee(); }

}
