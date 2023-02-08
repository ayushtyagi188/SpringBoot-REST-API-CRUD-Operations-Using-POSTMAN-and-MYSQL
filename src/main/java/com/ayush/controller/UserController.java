package com.ayush.controller;
import com.ayush.Exception.ResourceNotFoundException;
import com.ayush.model.User;
import com.ayush.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/employees")
public class UserController {

    @Autowired
    private UserRepo employeeRepository;

    @GetMapping
    public List<User> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @PostMapping
    public User createEmployee(@RequestBody User employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getEmployeeById(@PathVariable  long id){
        User employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" + id));
        return ResponseEntity.ok(employee);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateEmployee(@PathVariable long id,@RequestBody User employeeDetails) {
        User updateEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        updateEmployee.setFirstName(employeeDetails.getFirstName());
        updateEmployee.setLastName(employeeDetails.getLastName());
        updateEmployee.setEmailId(employeeDetails.getEmailId());

        employeeRepository.save(updateEmployee);

        return ResponseEntity.ok(updateEmployee);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id){

        User employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        employeeRepository.delete(employee);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
