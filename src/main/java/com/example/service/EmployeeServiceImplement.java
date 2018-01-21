package com.example.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.model.Employee;



@Service("employeeservice")
public class EmployeeServiceImplement implements EmployeeService{
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<Employee> employees;
	
	static{
		employees= populateDummyEmployees();
	}

	public List<Employee> findAllEmployees() {
		return employees;
	}
	
	public Employee findById(long id) {
		for(Employee Employee : employees){
			if(Employee.getId() == id){
				return Employee;
			}
		}
		return null;
	}
	
	public Employee findByName(String name) {
		for(Employee Employee : employees){
			if(Employee.getName().equalsIgnoreCase(name)){
				return Employee;
			}
		}
		return null;
	}
	
	public void saveEmployee(Employee employee) {
		employee.setId(counter.incrementAndGet());
		employees.add(employee);
	}

	public void updateEmployee(Employee employee) {
		int index = employees.indexOf(employee);
		employees.set(index, employee);
	}

	public void deleteEmployeeById(long id) {
		
		for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext(); ) {
		    Employee Employee = iterator.next();
		    if (Employee.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isEmployeeExist(Employee Employee) {
		return findByName(Employee.getName())!=null;
	}
	
	public void deleteAllEmployees(){
		employees.clear();
	}

	private static List<Employee> populateDummyEmployees(){
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(new Employee(counter.incrementAndGet(),"Sam",30, 70000));
		employees.add(new Employee(counter.incrementAndGet(),"Tom",40, 50000));
		employees.add(new Employee(counter.incrementAndGet(),"Jerome",45, 30000));
		employees.add(new Employee(counter.incrementAndGet(),"Silvia",50, 40000));
		return employees;
	}

}