package com.example.employee_demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee_demo.entity.EmployeeEntity;
import com.example.employee_demo.service.EmployeeService;
import com.example.employee_exceptions.EmployeeExistsException;

//SpringBoot will Know that This is The RestController
//This will contain code required to perform operations in the database
//The controller will give response in JSON format to the browser
//We'll call Service Class From Here

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService empService;
	
	//POST Method to Add Single User
	@CrossOrigin("http:localhost:9191/employee")
	@PostMapping("/employee")
	 public EmployeeEntity addEmployee(@RequestBody EmployeeEntity emp) throws EmployeeExistsException {
		return empService.addEmployee(emp);
	 }
	
	//GET Method Used to map the url to /employees Function to get all employees
	@CrossOrigin("http:localhost:9191/employee")
	@GetMapping("/employee")
	public List<EmployeeEntity> getAllEmployee() {
		return empService.getAllEmployee();
	}
	@CrossOrigin("http:localhost:9191/employee/{id}")
	@GetMapping("employee/{id}")
	public ResponseEntity<EmployeeEntity> getEmployeeById(@PathVariable int id){
		return empService.getEmployeeById(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	@CrossOrigin("http:localhost:9191/update/{id}")
	//PUT(UPDATE) Method to Update Employee
	@PutMapping("/update/{id}")
	public ResponseEntity<EmployeeEntity> updateEmployee(@PathVariable int id, @RequestBody EmployeeEntity emp) {
		return empService.getEmployeeById(id)
				.map(savedEmployee -> {
					savedEmployee.setName(emp.getName());
					savedEmployee.setSalary(emp.getSalary());
					
					EmployeeEntity updatedEmp = empService.updateEmployee(savedEmployee);
					return new ResponseEntity<>(updatedEmp, HttpStatus.OK);
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	@CrossOrigin("http:localhost:9191/delete/{id}")
	//DELETE Method to Delete User
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
		empService.deleteEmployee(id);
		return new ResponseEntity<>("Employee Deleted Successfullt !", HttpStatus.OK);
	}
	

}
