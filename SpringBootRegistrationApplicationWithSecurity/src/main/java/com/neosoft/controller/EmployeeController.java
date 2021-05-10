package com.neosoft.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neosoft.exception.EmployeeNotFoundException;
import com.neosoft.model.Employee;
import com.neosoft.model.UserRequest;
import com.neosoft.model.UserResponse;
import com.neosoft.service.EmployeeService;
import com.neosoft.util.JwtUtil;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService empService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil util;

	@PostMapping("/save")
	public ResponseEntity<String> saveEmployeeDetails(@Valid @RequestBody Employee employee) {

		ResponseEntity<String> resp = null;
		try {
			Integer empId = empService.registerEmployee(employee);
			resp = new ResponseEntity<String>("Employee " + empId + " created", HttpStatus.CREATED);
		} catch (Exception e) {
			resp = new ResponseEntity<String>("Unable to save employee", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody UserRequest userRequest) {

		// validate username and password
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userRequest.getUserName(), userRequest.getPassword()));

		String token = util.generateToken(userRequest.getUserName());
		return ResponseEntity.ok(token);

	}

	@GetMapping("/getOneEmployee/{empId}")
	public ResponseEntity<?> getOneEmployeeDetails(@PathVariable("empId") Integer empId) {

		ResponseEntity<?> res = null;
		try {
			Employee oneEmployee = empService.getOneEmployee(empId);
			res = new ResponseEntity<Employee>(oneEmployee, HttpStatus.OK);
		} catch (EmployeeNotFoundException emp) {
			throw emp;
		} catch (Exception e) {

			e.printStackTrace();
			res = new ResponseEntity<String>("Unable to get employee", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateEmployeeDetails(@Valid @RequestBody Employee employee) {

		ResponseEntity<?> res = null;
		try {
			Employee oneEmployee = empService.updateEmployee(employee);
			res = new ResponseEntity<Employee>(oneEmployee, HttpStatus.OK);
		} catch (EmployeeNotFoundException emp) {
			throw emp;
		} catch (Exception e) {

			e.printStackTrace();
			res = new ResponseEntity<String>("Unable to update employee", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}

	@GetMapping("/allEmployee")
	public ResponseEntity<?> getAllEmployeeDetails() {

		ResponseEntity<?> res = null;
		try {
			List<Employee> allEmployee = empService.getAllEmployee();
			res = new ResponseEntity<List<Employee>>(allEmployee, HttpStatus.OK);
		} catch (Exception e) {

			e.printStackTrace();
			res = new ResponseEntity<String>("No employee data available", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}

	@PatchMapping("/softDelete/{empId}")
	public ResponseEntity<String> softDeleteEmployee(@PathVariable Integer empId) {

		ResponseEntity<String> res = null;
		try {
			empService.softDeleteEmployee(empId);
			res = new ResponseEntity<String>("Employee soft deleted with Id:" + empId, HttpStatus.OK);
		} catch (EmployeeNotFoundException emp) {
			throw emp;
		} catch (Exception e) {

			e.printStackTrace();
			res = new ResponseEntity<String>("Unable to perform soft delete", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}

	@DeleteMapping("delete/{empId}")
	public ResponseEntity<String> deleteOneEmployee(@PathVariable Integer empId) {
		ResponseEntity<String> res = null;
		try {
			empService.deleteOneEmployee(empId);
			res = new ResponseEntity<String>("Employee deleted with Id:" + empId, HttpStatus.OK);
		} catch (EmployeeNotFoundException emp) {
			throw emp;
		} catch (Exception e) {

			e.printStackTrace();
			res = new ResponseEntity<String>("Unable to delete employee", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}

	@GetMapping("/getByFirstName/{firstName}")
	public ResponseEntity<?> searchByFirstName(@PathVariable String firstName) {
		ResponseEntity<?> res = null;
		try {
			List<Employee> employee = empService.findByFirstName(firstName);
			res = new ResponseEntity<List<Employee>>(employee, HttpStatus.OK);
		} catch (EmployeeNotFoundException emp) {
			throw emp;
		} catch (Exception e) {

			e.printStackTrace();
			res = new ResponseEntity<String>("Unable to get employee", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;

	}

	@GetMapping("/getBySurName/{surName}")
	public ResponseEntity<?> searchBySurName(@PathVariable String surName) {
		ResponseEntity<?> res = null;
		try {
			List<Employee> employee = empService.findBySurName(surName);
			res = new ResponseEntity<List<Employee>>(employee, HttpStatus.OK);
		} catch (EmployeeNotFoundException emp) {
			throw emp;
		} catch (Exception e) {

			e.printStackTrace();
			res = new ResponseEntity<String>("Unable to get employee", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;

	}

	@GetMapping("/getByPincode/{pincode}")
	public ResponseEntity<?> searchByPincode(@PathVariable Integer pincode) {
		ResponseEntity<?> res = null;
		try {
			List<Employee> employee = empService.findByPincode(pincode);
			res = new ResponseEntity<List<Employee>>(employee, HttpStatus.OK);
		} catch (EmployeeNotFoundException emp) {
			throw emp;
		} catch (Exception e) {

			e.printStackTrace();
			res = new ResponseEntity<String>("Unable to get employee", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}

	@GetMapping("/sortByDob/{asc}")
	public ResponseEntity<?> sortByDob(@PathVariable boolean asc) {
		ResponseEntity<?> res = null;
		try {
			List<Employee> listEmployee = empService.sortEmployeeByDob(asc);
			res = new ResponseEntity<List<Employee>>(listEmployee, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			res = new ResponseEntity<String>("Unable to sort employees", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}

	@GetMapping("/sortByDoj/{asc}")
	public ResponseEntity<?> sortByDoj(@PathVariable boolean asc) {
		ResponseEntity<?> res = null;
		try {
			List<Employee> listEmployee = empService.sortEmployeeByDoj(asc);
			res = new ResponseEntity<List<Employee>>(listEmployee, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			res = new ResponseEntity<String>("Unable to sort employees", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
}
