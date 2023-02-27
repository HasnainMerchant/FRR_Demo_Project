package com.example.employee_demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeDemoApplicationTests {
	
	@Test
	void contextLoads() {
		assert(true);
	}
	
	@Test 
	void applicationContextTest() {
		EmployeeDemoApplication.main(new String[] {});
		assert(true);
	}

}
