package com.neosoft.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neosoft.controller.EmployeeController;
import com.neosoft.dao.EmployeeRepository;
import com.neosoft.model.Employee;
import com.neosoft.service.EmployeeService;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private EmployeeRepository repo;

	@MockBean
	private EmployeeService service;

	ObjectMapper objMap = new ObjectMapper();

	public static List<Employee> setUp() throws ParseException {
		Employee emp = new Employee();
		emp.setEmployeeId(1);
		emp.setFirstName("shubh");
		emp.setSurName("jaiswal");
		emp.setEmployeeDob(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1994-05-05 00:00:00"));
		emp.setEmployeeDoj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-04-12 00:00:00"));
		emp.setMobileNumber(9479605143l);
		emp.setCountry("india");
		emp.setState("M.P");
		emp.setCity("khandwa");
		emp.setPincode(450001);
		emp.setActiveFlag(1);

		Employee emp1 = new Employee();
		emp1.setEmployeeId(2);
		emp1.setFirstName("vishal");
		emp1.setSurName("jaiswal");
		emp1.setEmployeeDob(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1994-05-05 00:00:00"));
		emp1.setEmployeeDoj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-04-12 00:00:00"));
		emp1.setMobileNumber(9479605143l);
		emp1.setCountry("india");
		emp1.setState("M.P");
		emp1.setCity("harsud");
		emp1.setPincode(450116);
		emp1.setActiveFlag(1);

		Employee emp2 = new Employee();
		emp2.setEmployeeId(1);
		emp2.setFirstName("shubham");
		emp2.setSurName("jaiswal");
		emp2.setEmployeeDob(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1994-05-05 00:00:00"));
		emp2.setEmployeeDoj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-04-12 00:00:00"));
		emp2.setMobileNumber(9479605143l);
		emp2.setCountry("india");
		emp2.setState("M.P");
		emp2.setCity("Harsud");
		emp2.setPincode(450116);
		emp2.setActiveFlag(1);

		List<Employee> listEmp = new ArrayList<>();
		listEmp.add(emp);
		listEmp.add(emp1);
		listEmp.add(emp2);

		return listEmp;
	}

	@Test
	public void saveEmployeeDetailsTest() throws Exception {

		Mockito.when(service.registerEmployee(EmployeeControllerTest.setUp().get(0))).thenReturn(1);

		String payload = objMap.writeValueAsString(EmployeeControllerTest.setUp().get(0));
		MvcResult result = mockMvc
				.perform(post("/employee/save").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated()).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(201, status);
	}

	@Test
	public void getAllEmployeeDetailsTest() throws Exception {
		List<Employee> listEmp = EmployeeControllerTest.setUp();
		String response = objMap.writeValueAsString(listEmp);
		Mockito.when(service.getAllEmployee()).thenReturn(listEmp);
		MvcResult result = mockMvc.perform(get("/employee/allEmployee").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		String empRes = result.getResponse().getContentAsString();
		assertEquals(response, empRes);
		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	public void getOneEmployeeDetailsTest() throws Exception {
		String response = objMap.writeValueAsString(EmployeeControllerTest.setUp().get(0));
		Mockito.when(service.getOneEmployee(1)).thenReturn(EmployeeControllerTest.setUp().get(0));
		MvcResult result = mockMvc
				.perform(get("/employee/getOneEmployee/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		assertEquals(response, result.getResponse().getContentAsString());
		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	public void updateEmployeeDetailsTest() throws Exception {

		Mockito.when(service.updateEmployee(EmployeeControllerTest.setUp().get(2)))
				.thenReturn(EmployeeControllerTest.setUp().get(2));
		String payload = objMap.writeValueAsString(EmployeeControllerTest.setUp().get(2));
		MvcResult result = mockMvc
				.perform(put("/employee/update").content(payload).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		int resposne = result.getResponse().getStatus();
		assertEquals(200, resposne);

	}

	@Test
	public void deleteOneEmployeeTest() throws Exception {

		MvcResult result = mockMvc.perform(delete("/employee/delete/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		int resposne = result.getResponse().getStatus();
		assertEquals(200, resposne);
		assertEquals("Employee deleted with Id:1", result.getResponse().getContentAsString());

	}

	@Test
	public void searchByFirstNameTest() throws Exception {
		Employee emp = new Employee();
		emp.setEmployeeId(1);
		emp.setFirstName("shubh");
		emp.setSurName("jaiswal");
		emp.setEmployeeDob(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1994-05-05 00:00:00"));
		emp.setEmployeeDoj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-04-12 00:00:00"));
		emp.setMobileNumber(9479605143l);
		emp.setCountry("india");
		emp.setState("M.P");
		emp.setCity("khandwa");
		emp.setPincode(450001);
		emp.setActiveFlag(1);
		List<Employee> list = new ArrayList<Employee>();
		list.add(emp);
		String response = objMap.writeValueAsString(list);
		Mockito.when(service.findByFirstName("shubh")).thenReturn(list);
		MvcResult result = mockMvc
				.perform(get("/employee/getByFirstName/shubh").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		assertEquals(response, result.getResponse().getContentAsString());
		assertEquals(200, result.getResponse().getStatus());
	}

	@Test
	public void searchBySurNameTest() throws Exception {
		Employee emp = new Employee();
		emp.setEmployeeId(1);
		emp.setFirstName("shubh");
		emp.setSurName("jaiswal");
		emp.setEmployeeDob(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1994-05-05 00:00:00"));
		emp.setEmployeeDoj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-04-12 00:00:00"));
		emp.setMobileNumber(9479605143l);
		emp.setCountry("india");
		emp.setState("M.P");
		emp.setCity("khandwa");
		emp.setPincode(450001);
		emp.setActiveFlag(1);

		Employee emp1 = new Employee();
		emp1.setEmployeeId(2);
		emp1.setFirstName("vishal");
		emp1.setSurName("jaiswal");
		emp1.setEmployeeDob(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1986-06-19 00:00:00"));
		emp1.setEmployeeDoj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-09-22 00:00:00"));
		emp1.setMobileNumber(9479605143l);
		emp1.setCountry("india");
		emp1.setState("M.P");
		emp1.setCity("harsud");
		emp1.setPincode(450116);
		emp1.setActiveFlag(1);

		List<Employee> list = new ArrayList<Employee>();
		list.add(emp);
		list.add(emp1);
		String response = objMap.writeValueAsString(list);
		Mockito.when(service.findBySurName("jaiswal")).thenReturn(list);
		MvcResult result = mockMvc
				.perform(get("/employee/getBySurName/jaiswal").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		assertEquals(response, result.getResponse().getContentAsString());
		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	public void searchByPincodeTest() throws Exception {
		Employee emp = new Employee();
		emp.setEmployeeId(1);
		emp.setFirstName("shubh");
		emp.setSurName("jaiswal");
		emp.setEmployeeDob(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1994-05-05 00:00:00"));
		emp.setEmployeeDoj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-04-12 00:00:00"));
		emp.setMobileNumber(9479605143l);
		emp.setCountry("india");
		emp.setState("M.P");
		emp.setCity("khandwa");
		emp.setPincode(450001);
		emp.setActiveFlag(1);

		Employee emp1 = new Employee();
		emp1.setEmployeeId(2);
		emp1.setFirstName("vishal");
		emp1.setSurName("jaiswal");
		emp1.setEmployeeDob(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1986-06-19 00:00:00"));
		emp1.setEmployeeDoj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-09-22 00:00:00"));
		emp1.setMobileNumber(9479605143l);
		emp1.setCountry("india");
		emp1.setState("M.P");
		emp1.setCity("harsud");
		emp1.setPincode(450116);
		emp1.setActiveFlag(1);

		List<Employee> list = new ArrayList<Employee>();
		list.add(emp);
		list.add(emp1);
		String response = objMap.writeValueAsString(list);
		Mockito.when(service.findByPincode(450116)).thenReturn(list);
		MvcResult result = mockMvc
				.perform(get("/employee/getByPincode/450116").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		assertEquals(response, result.getResponse().getContentAsString());
		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	public void sortVyDobTest() throws Exception {
		Employee emp1 = new Employee();
		emp1.setEmployeeId(2);
		emp1.setFirstName("vishal");
		emp1.setSurName("jaiswal");
		emp1.setEmployeeDob(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1986-06-19 00:00:00"));
		emp1.setEmployeeDoj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-09-22 00:00:00"));
		emp1.setMobileNumber(9479605143l);
		emp1.setCountry("india");
		emp1.setState("M.P");
		emp1.setCity("harsud");
		emp1.setPincode(450116);
		emp1.setActiveFlag(1);

		Employee emp = new Employee();
		emp.setEmployeeId(1);
		emp.setFirstName("shubh");
		emp.setSurName("jaiswal");
		emp.setEmployeeDob(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1994-05-05 00:00:00"));
		emp.setEmployeeDoj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-04-12 00:00:00"));
		emp.setMobileNumber(9479605143l);
		emp.setCountry("india");
		emp.setState("M.P");
		emp.setCity("khandwa");
		emp.setPincode(450001);
		emp.setActiveFlag(1);

		List<Employee> list = new ArrayList<Employee>();
		list.add(emp);
		list.add(emp1);
		String response = objMap.writeValueAsString(list);
		Mockito.when(service.sortEmployeeByDob(true)).thenReturn(list);
		MvcResult result = mockMvc
				.perform(get("/employee/sortByDob/true").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		assertEquals(response, result.getResponse().getContentAsString());
		assertEquals(200, result.getResponse().getStatus());

	}

	@Test
	public void sortVyDojTest() throws Exception {
		Employee emp = new Employee();
		emp.setEmployeeId(1);
		emp.setFirstName("shubh");
		emp.setSurName("jaiswal");
		emp.setEmployeeDob(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1994-05-05 00:00:00"));
		emp.setEmployeeDoj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-04-12 00:00:00"));
		emp.setMobileNumber(9479605143l);
		emp.setCountry("india");
		emp.setState("M.P");
		emp.setCity("khandwa");
		emp.setPincode(450001);
		emp.setActiveFlag(1);

		Employee emp1 = new Employee();
		emp1.setEmployeeId(2);
		emp1.setFirstName("vishal");
		emp1.setSurName("jaiswal");
		emp1.setEmployeeDob(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1986-06-19 00:00:00"));
		emp1.setEmployeeDoj(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2019-09-22 00:00:00"));
		emp1.setMobileNumber(9479605143l);
		emp1.setCountry("india");
		emp1.setState("M.P");
		emp1.setCity("harsud");
		emp1.setPincode(450116);
		emp1.setActiveFlag(1);

		List<Employee> list = new ArrayList<Employee>();
		list.add(emp);
		list.add(emp1);
		String response = objMap.writeValueAsString(list);
		Mockito.when(service.sortEmployeeByDoj(false)).thenReturn(list);
		MvcResult result = mockMvc
				.perform(get("/employee/sortByDoj/false").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		assertEquals(response, result.getResponse().getContentAsString());
		assertEquals(200, result.getResponse().getStatus());

	}

}
