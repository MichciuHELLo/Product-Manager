package com.example.MG_RestaurantManager20.employee.domain;

import lombok.*;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Random;

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

    private String passwordSalt;
    private String passwordHash;

    private Boolean tempFile;

    public Employee(Long user_fk, String firstName, String surname, String email, LocalDate employeeSince, String password, Boolean tempFile) {
        this.user_fk = user_fk;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.employeeSince = employeeSince;
        this.tempFile = tempFile;

        this.passwordSalt = new Random().ints(97, 122 + 1)
                .limit(32)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt);
    }

    public boolean checkPassword(String password) {
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
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
