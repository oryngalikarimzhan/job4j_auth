package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.domain.Employee;
import ru.job4j.domain.Person;
import ru.job4j.repository.EmployeeRepository;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeesController {
    @Autowired
    private RestTemplate rest;

    private final EmployeeRepository employees;

    public EmployeesController(EmployeeRepository employees) {
        this.employees = employees;
    }

    private static final String API = "http://localhost:8080/person/";

    private static final String API_ID = "http://localhost:8080/person/{id}";

    @GetMapping("/")
    public List<Employee> findAll() {
        return (List<Employee>) employees.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable int id) {
        var employee = this.employees.findById(id);
        return new ResponseEntity<Employee>(
                employee.orElse(new Employee()),
                employee.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return new ResponseEntity<Employee>(
                this.employees.save(employee),
                HttpStatus.CREATED
        );
    }
    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Employee employee) {
        this.employees.save(employee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Employee employee = new Employee();
        employee.setId(id);
        this.employees.delete(employee);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<Person> createAccount(@RequestBody Person person,
                                         @PathVariable int id) {
        Person rsl = rest.postForObject(API, person, Person.class);
        var optionalEmployee = this.employees.findById(id);
        Employee employee = null;
        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
            employee.addAccount(rsl);
        }
        this.employees.save(employee);
        return new ResponseEntity(rsl, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAccount(@RequestBody Person person) {
        rest.put(API, person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{eId}/{pId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int pId) {
        rest.delete(API_ID, pId);
        return ResponseEntity.ok().build();
    }
}
