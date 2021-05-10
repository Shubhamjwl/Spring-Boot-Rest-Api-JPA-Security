package com.neosoft.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.neosoft.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

	@Query("from Employee  WHERE employeeId=?1 And activeFlag=1")
	public Optional<Employee> findOneEmployee(@Param("empId") Integer empId);

	@Query("from Employee  WHERE activeFlag=1")
	public List<Employee> findAllEmployee();
	
	public List<Employee> findByFirstName(String firstName);
	
	public List<Employee>  findBySurName(String surName);
	
	public List<Employee>   findByPincode(Integer pincode);
	
	public Optional<Employee>  findByUserName(String userName);
}
