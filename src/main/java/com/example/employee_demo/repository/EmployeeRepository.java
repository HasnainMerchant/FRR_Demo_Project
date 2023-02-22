package com.example.employee_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.employee_demo.entity.EmployeeEntity;

//This will extend the CRUD Repository of the SpringJPA
//It contains all operations used in a database
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
	
	
	
}
