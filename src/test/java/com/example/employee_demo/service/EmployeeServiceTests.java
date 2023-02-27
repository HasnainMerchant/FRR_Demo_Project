package com.example.employee_demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.EnumerableAssert;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.employee_demo.entity.EmployeeEntity;
import com.example.employee_demo.repository.EmployeeRepository;
import com.example.employee_exceptions.EmployeeExistsException;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTests {
	
	//Create Mock of EmployeeRepository
	@Mock
	private EmployeeRepository employeeRepo;
	
	//Create an EmployeeService instance using the mock EmployeeRepository instance
	@InjectMocks
	private EmployeeService employeeService;
	
	private EmployeeEntity employee;
	
	@BeforeEach
	public void setup() {
		//employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
		employee = EmployeeEntity.builder()
				.id(10)
				.name("Hasnain")
				.salary(2000)
				.build();
	}
	
	//Junit tests for SaveEmployee method
	@DisplayName("JUnit test for saveEmployee Method")
	@Test
	void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() throws EmployeeExistsException {
		//given - precondition or setup
		given(employeeRepo.save(employee)).willReturn(employee);
		
		System.out.println(employeeRepo);
		System.out.println(employeeService);
		
		//when - action or behaviour that we are going to test
		EmployeeEntity savedEmployee = employeeService.addEmployee(employee);
		
		System.out.println(savedEmployee);
		
		//then - verify the output
		assertThat(savedEmployee).isNotNull();
	}
	
	//JUnit test for getAllEmployee method
	@DisplayName("JUnit Test For getAllEmployee Method")
	@Test
	void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList() {
		//given - precondition or Setup
		EmployeeEntity employee1 = EmployeeEntity.builder()
				.id(3)
				.name("Hasnain Merchant")
				.salary(4000)
				.build();
		
		given(employeeRepo.findAll()).willReturn(List.of(employee, employee1));
		
		//when - action or behaviour that we are going to test
		List<EmployeeEntity> employeeList = employeeService.getAllEmployee();
		
		//then - verify the output
		assertThat(employeeList).isNotNull();
		assertThat(employeeList.size()).isEqualTo(2);
	}
	
	//Junit test for getAllEmployees method
	@DisplayName("Junit Test for getAllEmployees method (negative scenario)")
	@Test
	void givenEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeeList() {
		//given - precondition or setup
		EmployeeEntity employee1 = EmployeeEntity.builder()
				.id(5)
				.name("Hasnain")
				.salary(5000)
				.build();
		
		given(employeeRepo.findAll()).willReturn(Collections.emptyList());
		
		//when - action or the behaviour that we are going to test
		List<EmployeeEntity> employeeList = employeeService.getAllEmployee();
		
		//then - verify the output
		assertThat(employeeList).isEmpty();
		assertThat(employeeList.size()).isZero();
	}
	
	//JUnit test for updateEmployee method
	@DisplayName("JUnit test for updateEmployee method")
	@Test
	void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
		//given - precondition or setup
		given(employeeRepo.save(employee)).willReturn(employee);
		employee.setName("Ramesh");
		employee.setSalary(25000);
		
		//when - action or the behaviour that we are going to test
		EmployeeEntity updatedEmployee = employeeService.updateEmployee(employee);
		
		//then - verify the output
		assertThat(updatedEmployee.getName()).isEqualTo("Ramesh");
		assertThat(updatedEmployee.getSalary()).isEqualTo(25000);
	}
	
	// JUnit test for deleteEmployee method
    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    void givenEmployeeId_whenDeleteEmployee_thenNothing(){
        // given - precondition or setup
        int employeeId = 10;

        willDoNothing().given(employeeRepo).deleteById(employeeId);

        // when -  action or the behaviour that we are going test
        employeeService.deleteEmployee(employeeId);

        // then - verify the output
        verify(employeeRepo, times(1)).deleteById(employeeId);
    }
}
