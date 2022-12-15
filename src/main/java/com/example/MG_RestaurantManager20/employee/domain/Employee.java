package com.example.MG_RestaurantManager20.employee.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_fk;

    private String firstName;

    private String surname;

    private String email;

    private LocalDate employeeSince;

    private String password;

    private Boolean tempFile;

    public Employee(Long user_fk, String firstName, String surname, String email, LocalDate employeeSince, String password, Boolean tempFile) {
        this.user_fk = user_fk;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.employeeSince = employeeSince;
        this.password = password;
        this.tempFile = tempFile;
    }

    public Employee(String firstName, String surname, String email, LocalDate employeeSince) {
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.employeeSince = employeeSince;
    }

    public Employee(Long id) {
        this.id = id;
    }
}
