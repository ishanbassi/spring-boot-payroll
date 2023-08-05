package com.example.payroll;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static
org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController

public class EmployeeController {
    private final EmployeeRepository repository;

    EmployeeController(EmployeeRepository repository){
        this.repository = repository;
    }
    
    @GetMapping("/employees")
    
    CollectionModel<EntityModel<Employee>> all() {

      
      List<EntityModel<Employee>> employees =  repository
      .findAll()
      .stream()
      .map(em -> EntityModel.of(em,
          linkTo(methodOn(EmployeeController.class).one(em.getId())).withSelfRel(),
          linkTo(methodOn(EmployeeController.class).all()).withRel("employees"))
      )
      .collect(Collectors.toList());
      

      return CollectionModel.of(employees, 
        linkTo(methodOn(EmployeeController.class).all()).withSelfRel()
      );
      
    
    }
    
    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable Long id) {
      Employee employee  = repository.findById(id)
      .orElseThrow( () -> new EmployeeNotFoundException(id));

      return  EntityModel.of(
        employee,
        linkTo(methodOn(EmployeeController.class).all()).withRel("employees"),
        linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel()
        
        
      );
      
    }

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
            
            System.out.println("hiiii");
            return repository.save(newEmployee);
    }
    
   

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
      
      return repository.findById(id)
        .map(employee -> {
          employee.setName(newEmployee.getName());
          employee.setRole(newEmployee.getRole());
          return repository.save(employee);
        })
        .orElseGet(() -> {
          newEmployee.setId(id);
          return repository.save(newEmployee);
        });
    }

    
    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
       repository.deleteById(id);
    }
}

