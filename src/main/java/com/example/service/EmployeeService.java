package com.example.service;

//import java.util.Hashtable;
//import org.springframework.stereotype.Service;
//
//import com.example.model.Employee;
//
//@Service
//public class EmployeeService {
//  Hashtable <String, Employee> employees = new Hashtable<String, Employee>();
//  public EmployeeService () {
//	  Employee one = new Employee();
//	  one.setAge(21);
//	  one.setFirstName("Vincent");
//	  one.setLastName("Mugambi");
//	  one.setId("1");
//	  employees.put("1", one);
//	  
//	  Employee two = new Employee();
//	  two.setAge(21);
//	  two.setFirstName("Janet");
//	  two.setLastName("Wanjiku");
//	  two.setId("2");
//	  employees.put("2", two); 
//  }
//  
//  public Employee getEmployee (String id) {
//	  if (employees.containsKey(id)) {
//		 return employees.get(id);
//	  } else return null;
//  }
//  
//  public Hashtable<String, Employee> getAll() {
//	  return employees;
//  }
//}

import java.util.List;

import com.example.model.Employee;

public interface EmployeeService {
	
	Employee findById(long id);
	
	Employee findByName(String name);
	
	void saveEmployee(Employee employee);
	
	void updateEmployee(Employee employee);
	
	void deleteEmployeeById(long id);

	List<Employee> findAllEmployees();
	
	void deleteAllEmployees();
	
	boolean isEmployeeExist(Employee employee);
	
}