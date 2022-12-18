package com.example.MG_RestaurantManager20.employee.adapters.database;

import com.example.MG_RestaurantManager20.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.email = ?1")
    Optional<Employee> getEmployeeByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE e.user_fk = ?1")
    List<Employee> getEmployeesByUserSessionId(Long userId);

    @Modifying
    @Query("update Employee e set e.firstName = ?1, e.surname = ?2, e.email = ?3 where e.id = ?4")
    void updateEmployeeById(String firstname, String surname, String email, Long employeeId);

    @Modifying
    @Query("UPDATE Employee e SET e.password = ?2, e.tempFile = false WHERE e.email = ?1")
    void changePassword(String email, String newPassword);

}
