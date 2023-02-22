package com.example.employee_demo.controller;

import com.example.employee_demo.entity.EmployeeEntity;
import com.example.employee_demo.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
class EmployeeControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmployeeService employeeService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
		
		//given - precondition or setup
		EmployeeEntity employee = EmployeeEntity.builder()
				.id(4)
				.name("Hasnain")
				.salary(3000)
				.build();
		given(employeeService.addEmployee(any(EmployeeEntity.class)))
		.willAnswer((invocation) -> invocation.getArgument(0));
		
		//when - action or behaviour that we are going to test
		ResultActions response = mockMvc.perform(post("http://localhost:9191/employee")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(employee)));
		
		//then - verify the result or output using assert statements
		response.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", is(employee.getName())))
		.andExpect(jsonPath("$.salary", is(employee.getSalary())));
	}
	
	//JUNIT test for Get ALL Employees REST API
	@Test
	void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
        // given - precondition or setup
        List<EmployeeEntity> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(EmployeeEntity.builder().name("Hasnain").salary(4000).build());
        listOfEmployees.add(EmployeeEntity.builder().name("Merchant").salary(765000).build());
        given(employeeService.getAllEmployee()).willReturn(listOfEmployees);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("https://localhost:9191/employee"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));

    }

    // positive scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
   void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{
        // given - precondition or setup
        int employeeId = 100;
        EmployeeEntity employee = EmployeeEntity.builder()
                .name("Hasnain")
                .salary(4000)
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("http://locahost:9191/employee/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(employee.getName())))
                .andExpect(jsonPath("$.salary", is(employee.getSalary())));

    }

    // negative scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        int employeeId = 100;
        EmployeeEntity employee = EmployeeEntity.builder()
                .name("Abdul")
                .salary(8900)
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("http://locahost:9191/employee/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }
    // JUnit test for update employee REST API - positive scenario
        @Test
        void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception{
            // given - precondition or setup
            int employeeId = 19;
            EmployeeEntity savedEmployee = EmployeeEntity.builder()
                    .name("Ramesh")
                    .salary(7000)
                    .build();

            EmployeeEntity updatedEmployee = EmployeeEntity.builder()
                    .name("Ram")
                    .salary(1000)
                    .build();
            given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
            given(employeeService.updateEmployee(any(EmployeeEntity.class)))
                    .willAnswer((invocation)-> invocation.getArgument(0));

            // when -  action or the behaviour that we are going test
            ResultActions response = mockMvc.perform(put("http://locahost:9191/update/{id}", employeeId)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(updatedEmployee)));


            // then - verify the output
            response.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.name", is(updatedEmployee.getName())))
                    .andExpect(jsonPath("$.salary", is(updatedEmployee.getSalary())));
        }

    // JUnit test for update employee REST API - negative scenario
    @Test
    void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception{
        // given - precondition or setup
        int employeeId = 1;
        EmployeeEntity savedEmployee = EmployeeEntity.builder()
                .name("Ramesh")
                .salary(700)
                .build();

        EmployeeEntity updatedEmployee = EmployeeEntity.builder()
                .name("Ram")
                .salary(500)
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        given(employeeService.updateEmployee(any(EmployeeEntity.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("http://localhost:9191/update/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

// JUnit test for delete employee REST API
    @Test
    void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception{
        // given - precondition or setup
        int employeeId = 1;
        willDoNothing().given(employeeService).deleteEmployee(employeeId);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("http://localhost:9191/delete/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
