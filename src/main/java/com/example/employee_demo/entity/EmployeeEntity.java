package com.example.employee_demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//This class will be Entity Class and as SpringBoot Applications starts it will look
//for Entity Class and It will find Employee with Entity Annotation and it will create table in database
@Entity
//Will Create Table in the database with name=Employee
@Table(name="Employee")
public class EmployeeEntity {
	//It will use the following elements as columns names
	
	//id will be primary key
	@Id
	@GeneratedValue()
	private int id;
	@Column(name="name", nullable = false)
	private String name;
	@Column(name="salary", nullable = false)
	private int salary;
}
