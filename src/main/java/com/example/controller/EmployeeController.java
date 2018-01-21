package com.example.controller;
//package controller;
//
//import java.util.Hashtable;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import model.Employee;
//import service.EmployeeService;
//
//@RestController
//public class EmployeeController {
//  @Autowired EmployeeService es;
//  
//  @RequestMapping("/all")
//  public Hashtable<String, Employee> getAll(){
//	  return es.getAll();
//  }
//  
//  @RequestMapping("{id}")
//  public Employee getEmployee(@PathVariable("id") String id) {
//	  return es.getEmployee(id);
//  }
//}
import java.util.List;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.model.Employee;
import com.example.service.EmployeeService;

import com.example.utils.ErrorType;
 
@RestController
@RequestMapping("/api")
public class EmployeeController {
 
    public static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
 
    @Autowired
    EmployeeService userService; //Service which will do all data retrieval/manipulation work
 
    // -------------------Retrieve All Users---------------------------------------------
 
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> listAllUsers() {
        List<Employee> employees = userService.findAllEmployees();
        if (employees.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }
 
    // -------------------Retrieve Single User------------------------------------------
 
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        logger.info("Fetching User with id {}", id);
        Employee employee = userService.findById(id);
        if (employee == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity(new ErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }
 
    // -------------------Create a User-------------------------------------------
 
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody Employee employee, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", employee);
 
        if (userService.isEmployeeExist(employee)) {
            logger.error("Unable to create. A User with name {} already exist", employee.getName());
            return new ResponseEntity(new ErrorType("Unable to create. A User with name " + 
            employee.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        userService.saveEmployee(employee);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(employee.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 
    // ------------------- Update a User ------------------------------------------------
 
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody Employee employee) {
        logger.info("Updating User with id {}", id);
 
        Employee currentEmployee = userService.findById(id);
 
        if (currentEmployee == null) {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity(new ErrorType("Unable to upate. User with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
 
        currentEmployee.setName(employee.getName());
        currentEmployee.setAge(employee.getAge());
        currentEmployee.setSalary(employee.getSalary());
 
        userService.updateEmployee(currentEmployee);
        return new ResponseEntity<Employee>(currentEmployee, HttpStatus.OK);
    }
 
    // ------------------- Delete a User-----------------------------------------
 
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting User with id {}", id);
 
        Employee employee = userService.findById(id);
        if (employee == null) {
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity(new ErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userService.deleteEmployeeById(id);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }
 
    // ------------------- Delete All Users-----------------------------
 
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<Employee> deleteAllUsers() {
        logger.info("Deleting All Users");
 
        userService.deleteAllEmployees();
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }
 
}
