package com.neosoft.dao;



import org.springframework.data.repository.PagingAndSortingRepository;

import com.neosoft.model.Employee;

public interface EmployeeSortRepository extends PagingAndSortingRepository<Employee, Integer> {


}
