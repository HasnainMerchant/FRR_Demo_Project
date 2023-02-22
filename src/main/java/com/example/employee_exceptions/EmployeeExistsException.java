package com.example.employee_exceptions;

public class EmployeeExistsException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeExistsException(String str) {
		super(str);
	}

}
