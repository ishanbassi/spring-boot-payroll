package com.example.payroll;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.GeneratedValue;


@Entity
@Data
public class Employee { 
    private @Id @GeneratedValue Long id;
    private String name;
    private String role;

    Employee() {}

    Employee(String name, String role){
        this.name = name;
        this.role  = role;
    }
}
