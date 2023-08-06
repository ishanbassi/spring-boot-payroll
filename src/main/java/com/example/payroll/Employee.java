package com.example.payroll;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.GeneratedValue;


@Entity
@Data
public class Employee { 
    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    private String role;

    Employee() {}

    Employee(String firstName , String lastName, String role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.role  = role;
    }
    public String getName() {
        return this.firstName + " " + this.lastName;
    }
    public void setName(String name) {
        String[] parts = name.split(" ");
        this.firstName  = parts[0];
        this.lastName = parts[1];
    }

    @Override
    public String toString() {
      return "Employee{" + "id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName
          + '\'' + ", role='" + this.role + '\'' + '}';
    }
}
