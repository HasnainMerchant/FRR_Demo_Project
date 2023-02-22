package com.example.employee_demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.employee_demo.entity.EmployeeEntity;
import com.example.employee_demo.repository.EmployeeRepository;
import com.example.employee_exceptions.EmployeeExistsException;

@Service
public class EmployeeService {
	
	//Reference Variable of SubjectRepository Interface
	//@Autowired annotation is used for automatic dependency injection
	@Autowired
	public EmployeeRepository employeeRepo;
	
	//We'll call the Repository class from SpringJPA to get data from database
	public List<EmployeeEntity> getAllEmployee(){
		return employeeRepo.findAll();
	}
	
	public Optional<EmployeeEntity> getEmployeeById(int id){
		return employeeRepo.findById(id);
	}

	public EmployeeEntity addEmployee(EmployeeEntity emp) throws EmployeeExistsException {
		Optional<EmployeeEntity> savedEmployee = employeeRepo.findById(emp.getId());
		if(savedEmployee.isPresent()) {
			throw new EmployeeExistsException("Employee Already Exists");
		}
		return employeeRepo.save(emp);
	}

	public EmployeeEntity updateEmployee(EmployeeEntity updatedEmp) {
		return employeeRepo.save(updatedEmp);
	}

	public void deleteEmployee(int id) {
		employeeRepo.deleteById(id);
	}
}
