package com.neosoft.service;

import java.util.List;
import java.util.Optional;

import com.neosoft.model.Employee;

public interface EmployeeService {

	public Integer registerEmployee(Employee employee);

	public Employee getOneEmployee(Integer empId);

	public Employee updateEmployee(Employee employee);

	public List<Employee> getAllEmployee();

	public void softDeleteEmployee(Integer empId);

	public void deleteOneEmployee(Integer empId);

	public List<Employee> findByFirstName(String firstName);
	
	public Optional<Employee> findByUserName(String userName);

	public List<Employee> findBySurName(String surName);

	public List<Employee> findByPincode(Integer pincode);

	public List<Employee> sortEmployeeByDob(boolean asc);

	public List<Employee> sortEmployeeByDoj(boolean asc);

}
